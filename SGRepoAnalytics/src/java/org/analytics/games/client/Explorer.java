/**
 * Sencha GXT 3.0.1 - Sencha for GWT
 * Copyright(c) 2007-2012, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */

package org.analytics.games.client;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.core.client.GXT;
import org.analytics.games.client.app.ioc.ExplorerGinjector;
import org.analytics.games.shared.server;
import org.analytics.games.shared.serverAsync;
import com.sencha.gxt.state.client.CookieProvider;
import com.sencha.gxt.state.client.StateManager;
import org.analytics.games.client.models.Analytics;

public class Explorer implements EntryPoint {

  private final ExplorerGinjector injector = GWT.create(ExplorerGinjector.class);
	private serverAsync server;

  @Override
  public void onModuleLoad() {

		server = (serverAsync) GWT.create(server.class);
		inizia(asyncGetAnalytics);
  }

  public void lancia(Analytics analytics){
	  AnalyticsStatic.analytics=analytics;
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {

      @Override
      public void execute() {
        StateManager.get().setProvider(new CookieProvider("/", null, null, GXT.isSecure()));

        ExplorerApp app = injector.getApp();
        app.run();

        onReady();
      }

    });
  }


	public void inizia(AsyncCallback<Analytics> callback){
		server.getSGSAnalytics(asyncGetAnalytics);
	}

	public serverAsync getServices() {
		return server;
	}

  private native void onReady() /*-{
		if (typeof $wnd.GxtReady != 'undefined') {
			$wnd.GxtReady();
		}
  }-*/;
public final AsyncCallback<Analytics> asyncGetAnalytics = new AsyncCallback<Analytics>() {
		@Override
		public void onSuccess(Analytics result) {
			lancia(result);
		}

		@Override
		public void onFailure(Throwable caught) {

						Window.alert("Error getting analytics");
		}
	};

}
