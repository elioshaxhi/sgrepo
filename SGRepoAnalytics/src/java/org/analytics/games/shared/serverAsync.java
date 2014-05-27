/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package org.analytics.games.shared;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.analytics.games.client.models.Analytics;

/**
 *
 * @author Elion Haxhi
 */
public interface serverAsync {

    public void getSGSAnalytics(AsyncCallback<Analytics> callback);

}
