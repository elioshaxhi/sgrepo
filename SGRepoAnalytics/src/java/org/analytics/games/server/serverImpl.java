/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.analytics.games.server;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.analytics.games.shared.server;
import org.analytics.games.client.models.Analytics;

/**
 *
 * @author Elion Haxhi
 */
public class serverImpl extends RemoteServiceServlet implements server {

	@Override
	public void init() {

		String servletContext = getServletContext().getRealPath("");
		Logic.setServletContext(servletContext);
	}

	@Override
	public Analytics getSGSAnalytics() {
		return Logic.getSGSAnalytics();
	}

}
