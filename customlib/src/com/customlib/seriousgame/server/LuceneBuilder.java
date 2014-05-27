/*
 * Make sure to add /usr/share/java/mysql.jar to the list of jars in NetBeans properties
 * 
 */
package com.customlib.seriousgame.server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author Geert-Jan Giezeman
 */
public class LuceneBuilder implements IndexBuilder{


   @Override
	public boolean build(Path destdir) {
		Connection con;
		try {
			con = DatabaseConnection.getConnection();
		} catch (SQLException ex) {
			Logger.getLogger(LuceneBuilder.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
		try {

			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
			Directory directory;
			try {
				directory = FSDirectory.open(destdir.toFile());
			} catch (IOException ex) {
				Logger.getLogger(LuceneBuilder.class.getName()).log(Level.SEVERE, null, ex);
				return false;
			}
			IndexWriterConfig config = new IndexWriterConfig(org.apache.lucene.util.Version.LUCENE_CURRENT, analyzer);
			try (IndexWriter iwriter = new IndexWriter(directory, config)) {
				try {
					Statement stmt;
					ResultSet rs;
					stmt = con.createStatement();
					rs = stmt.executeQuery("SELECT id,Title,Keywords,FreeDescription FROM vre_serious_games");
					while (!rs.isLast()) {
						rs.next();
						String title = rs.getString(2);
						String keywords = rs.getString(3);
						String description = rs.getString(4);
						Document doc = new Document();
						doc.add(new Field("id", rs.getString(1), TextField.TYPE_STORED));
						doc.add(new Field("title", title, TextField.TYPE_STORED));
						doc.add(new Field("content", description + " " + title + " " + keywords + " ",
								TextField.TYPE_NOT_STORED));
						iwriter.addDocument(doc);
					}
				} catch (SQLException ex) {
					Logger.getLogger(LuceneBuilder.class.getName()).log(Level.SEVERE, null, ex);
				}
			} catch (IOException ex) {
				Logger.getLogger(LuceneBuilder.class.getName()).log(Level.SEVERE, null, ex);
				return false;
			}
		} finally {
			try {
				con.close();
			} catch (SQLException ex) {
				Logger.getLogger(LuceneBuilder.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return true;
	}

}
