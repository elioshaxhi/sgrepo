/**
 * Sencha GXT 3.0.1 - Sencha for GWT Copyright(c) 2007-2012, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */

package org.analytics.games.client.GeneralDescription;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import org.analytics.games.client.AnalyticsStatic;
import org.analytics.games.client.model.Example.Detail;
import org.analytics.games.client.grafics.AccessoriesChart;
import org.analytics.games.client.grafics.Torta;
import org.analytics.games.client.models.Analytics;

@Detail(name = "Markets Chart", icon = "piechart", category = "GeneralDescription")
public class Markets implements IsWidget, EntryPoint {

	static AccessoriesChart acc = new AccessoriesChart();
	private Analytics analytics = null;

	public void onModuleLoad() {
		RootPanel.get().add(asWidget());
	}

	@Override
	public Widget asWidget() {
		analytics = AnalyticsStatic.analytics;
		Torta genres=new Torta("Markets Chart");
		genres.setMappa(acc, "markets");
		genres.setCoda(analytics.getMarketsList(),"Markets Chart");
		return genres.asWidget();
	}
}