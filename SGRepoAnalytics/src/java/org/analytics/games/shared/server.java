/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.analytics.games.shared;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.analytics.games.client.models.Analytics;

/**
 *
 * @author Elion Haxhi
 */
@RemoteServiceRelativePath("server")
public interface server extends RemoteService {

    public Analytics getSGSAnalytics();

}
