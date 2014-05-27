/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sg.server;

import com.customlib.seriousgame.server.IndexBuilder;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * A thread that collects requests to rebuild an index and performs the rebuilds
 * from time to time.
 * 
 * There is some delay between the request of a rebuild and the start. 
 * As a result, if several rebuild requests are issued quickly after each other,
 * only one rebuild will be performed.
 * @author Geert-Jan Giezeman
 */
public class IndexerThread extends Thread {
	private final long maxWait;
	private final long delay;
	private final Path storedir;
	private final Path trashdir;
	private final Path builddir;
	private final IndexBuilder indexBuilder;
	// The following members need to be synchronized between this thread and the
	// callers of rebuildNeeded.
	private volatile boolean stopRequested = false;
	private final Lock index_build = new ReentrantLock();
	private final Condition notClean  = index_build.newCondition();
	// Condition on which the index builder waits.
	private long lastStartTime = 0; 
// Time (seconds since epoch) to start indexer at the latest. 
// This will make sure that if there is a constant flow of rebuild requests,
// those will not delay the rebuild indefinetely.
	private long startTime = 0; 
// time (seconds since last epoch) to start indexer 
// (unless delayed more by a new call to rebuildNeeded).
// A startTime of 0 indicates that no rebuild is requested.
	
	
	
	private void moveContent(Path sourcedir, Path destdir) throws IOException
	{
		List<Path> paths = new ArrayList<Path>();
		DirectoryStream<Path> stream = Files.newDirectoryStream(sourcedir);
		try  {
			for (Path entry: stream) {
				if (Files.isRegularFile(entry))
					paths.add(entry);
			}
		} finally {
			stream.close();
		}
		for (Path source: paths) {
			Files.move(source, destdir.resolve(source.getFileName()), REPLACE_EXISTING);
		} 
	}
	
	private void emptyContent(Path sourcedir) {
		List<Path> paths = new ArrayList<Path>();
		try {
			DirectoryStream<Path> stream = Files.newDirectoryStream(sourcedir);
			try {
				for (Path entry : stream) {
					if (Files.isRegularFile(entry)) {
						paths.add(entry);
					}
				}
			} finally {
				stream.close();
			}

		} catch (IOException ex) {
			Logger.getLogger(IndexerThread.class.getName()).log(Level.SEVERE, null, ex);
		}
		for (Path source : paths) {
			try {
				Files.delete(source);
			} catch (IOException ex) {
				Logger.getLogger(IndexerThread.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	/**
	 * Create a thread that handles rebuilding an index.
	 * @param builder The IndexBuilder that performs the rebuilding.
	 * @param storedir The directory where the index will be stored. 
	 * The index should be stored in regular files in this directory.
	 * Two directories, build and trash, will be created inside this directory.
	 * Those directories are used for internal processing.
	 * @param delay The time (in seconds) to wait at least between the last rebuild request and the rebuild start 
	 * @param maxWait The time (in seconds) to wait at most between the first rebuild request and the rebuild start.
	 * @throws IOException, IllegalArgumentException
	 */
	public IndexerThread(IndexBuilder builder, Path storedir, int delay, int maxWait) throws IOException
	{
		if (delay<0)
			throw new IllegalArgumentException("Negative delay");
		this.delay = delay;
		this.maxWait= maxWait<delay? delay : maxWait;		
		if (!Files.isDirectory(storedir)) {
			throw new IllegalArgumentException("storedir is not a directory: "+storedir.toString());
		}
		indexBuilder = builder;
		this.storedir=storedir;
		this.builddir=storedir.resolve("build");
		this.trashdir=storedir.resolve("trash");
		if (!Files.isDirectory(builddir)) {		
			Files.createDirectory(builddir);
		}
		if (!Files.isDirectory(trashdir)) {	
			Files.createDirectory(trashdir);
		}		
	}
	
	/**
	 * Calls IndexerThread constructor with standard values for delay and maxWait.
	 * @param builder
	 * @param storedir
	 * @throws IOException
	 */
	public IndexerThread(IndexBuilder builder, Path storedir) throws IOException
	{
		this(builder,storedir, 180, 1800);
	}
	
	/**
	 * Call rebuildNeeded from any thread to request a rebuild of the index.
	 */
	public void rebuildNeeded()
	{
		index_build.lock();
		try {
			long now = System.currentTimeMillis()/1000; 
			if (startTime==0) {
				lastStartTime=now+maxWait;
				startTime=now+delay;
				notClean.signal();
				
			} else {
				long time = now+delay;
				if (time>lastStartTime) time = lastStartTime;
				if (time>startTime) {
					startTime = time;
				}				
			}		
		} finally {
			index_build.unlock();
		}
	}
	
	/**
	 * Stops this Thread in a secure way. No new rebuild will be performed, 
	 * but a running computation will not be interrupted.
	 */
	public void stopThread()
	{
		stopRequested = true;
		interrupt();
	}
	
	/**
	 * Call start() to run this thread.
	 */
	@Override
	public void run() {
		while (true) {

			// indexer_state is idle
			index_build.lock();
			try {
				// wait for signal
				while (startTime == 0 && !stopRequested) {
					//System.err.println("Indexer waiting for notClean");
					try {
						notClean.await();
					} catch (InterruptedException ex) {
					}
				}
				while (!stopRequested) {
					long now = System.currentTimeMillis() / 1000;
					if (now >= startTime) {
						break;
					}
					try {
						notClean.await(startTime - now, TimeUnit.SECONDS);
					} catch (InterruptedException ex) {
					}
				}
				
				startTime = 0;
			} finally {
				index_build.unlock();
			}
			if (stopRequested) {
					break;
			}
			indexBuilder.build(builddir);
			if (stopRequested) {
				emptyContent(builddir);
				break;
			}
			try {
				// move index to trash
				moveContent(storedir, trashdir);
			} catch (IOException ex) {
				Logger.getLogger(IndexerThread.class.getName()).log(Level.SEVERE, null, ex);
			}
			try {
				// move temp to index
				moveContent(builddir, storedir);
			} catch (IOException ex) {
				Logger.getLogger(IndexerThread.class.getName()).log(Level.SEVERE, null, ex);
				emptyContent(builddir);
			}
			// empty trash
			emptyContent(trashdir);

		}
	}


}
