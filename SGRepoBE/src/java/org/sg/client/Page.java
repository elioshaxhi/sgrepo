/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sg.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockPanel;

/**
 *
 * @author nertil
 */
public class Page extends DockPanel implements HistoryListener {

	private MainEntryPoint entrypoint = null;

	private TopPanel topPanel = null;
	private CentralPanel centralPanel = null;

	public Page(MainEntryPoint entrypoint) {
		this.entrypoint = entrypoint;

		topPanel = new TopPanel(entrypoint);

		centralPanel = new CentralPanel(entrypoint);
		centralPanel.setHeightToRetrieve(228);
	}

	public void init() {
		initHistorySupport();
		run();
	}

	public void run() {
		getCentralPanel().init();

		add(getTopPanel(), DockPanel.NORTH);
		add(getCentralPanel(), DockPanel.CENTER);
	}

	/**
	 * @return the centralPanel
	 */
	public CentralPanel getCentralPanel() {
		return centralPanel;
	}

	public void onHistoryChanged(String historyToken) {
		updateState(historyToken);
	}

	public void initHistorySupport() {
		History.addHistoryListener(this);

		String token = History.getToken();
		//MessageBox.alert(strings.alert(), , null);(token);
		if (token.length() == 0) {
			onHistoryChanged("null");
		} else {
			onHistoryChanged(token);
		}
	}

	public void updateState(String token) {

	}
	final AsyncCallback<Void> asyncDebugMode = new AsyncCallback<Void>() {

		public void onSuccess(Void result) {
		}

		public void onFailure(Throwable caught) {
		}
	};

	/**
	 * @return the topPanel
	 */
	public TopPanel getTopPanel() {
		return topPanel;
	}
}
