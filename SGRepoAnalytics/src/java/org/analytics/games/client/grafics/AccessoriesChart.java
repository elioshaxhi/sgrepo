/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analytics.games.client.grafics;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.series.PieSeries;
import com.sencha.gxt.data.shared.ListStore;
import java.util.HashMap;
import java.util.Map;
import org.analytics.games.client.models.UnicModel;
import org.analytics.games.client.models.UnicModelProperties;

/**
 *
 * @author Elion Haxhi
 */
public class AccessoriesChart {

	public Map<String, ListStore<UnicModel>> tbStore = new HashMap<String, ListStore<UnicModel>>();
	public Map<String, Legend<UnicModel>> tbLegend = new HashMap<String, Legend<UnicModel>>();
	public Map<String, Chart<UnicModel>> tbChart = new HashMap<String, Chart<UnicModel>>();
	public Map<String, PieSeries<UnicModel>> tbPie = new HashMap<String, PieSeries<UnicModel>>();



	private static final UnicModelProperties props = GWT.create(UnicModelProperties.class);
	final ListStore<UnicModel> gl = new ListStore<UnicModel>(props.key());
	final ListStore<UnicModel> ml = new ListStore<UnicModel>(props.key());
	final ListStore<UnicModel> agel = new ListStore<UnicModel>(props.key());
	final ListStore<UnicModel> aval = new ListStore<UnicModel>(props.key());
	final ListStore<UnicModel> statl = new ListStore<UnicModel>(props.key());


	final Chart<UnicModel> gc = new Chart<UnicModel>();
	final Chart<UnicModel> mc = new Chart<UnicModel>();
	final Chart<UnicModel> agec = new Chart<UnicModel>();
	final Chart<UnicModel> avac = new Chart<UnicModel>();
	final Chart<UnicModel> statc = new Chart<UnicModel>();



	final Legend<UnicModel> gleg = new Legend<UnicModel>();
	final Legend<UnicModel> mleg = new Legend<UnicModel>();
	final Legend<UnicModel> ageleg = new Legend<UnicModel>();
	final Legend<UnicModel> avaleg = new Legend<UnicModel>();
	final Legend<UnicModel> statleg = new Legend<UnicModel>();

	PieSeries<UnicModel> gp=new PieSeries<UnicModel>();
	PieSeries<UnicModel> mp=new PieSeries<UnicModel>();
	PieSeries<UnicModel> agep=new PieSeries<UnicModel>();
	PieSeries<UnicModel> avap=new PieSeries<UnicModel>();
	PieSeries<UnicModel> statp=new PieSeries<UnicModel>();

	public AccessoriesChart() {
		tbStore.put("genres", gl);
		tbStore.put("markets", ml);
		tbStore.put("ages", agel);
		tbStore.put("availability", aval);
		tbStore.put("status", statl);

		tbLegend.put("genres", gleg);
		tbLegend.put("markets", mleg);
		tbLegend.put("ages", ageleg);
		tbLegend.put("availability", avaleg);
		tbLegend.put("status", statleg);

		tbChart.put("genres", gc);
		tbChart.put("markets", mc);
		tbChart.put("ages", agec);
		tbChart.put("availability", avac);
		tbChart.put("status", statc);

		tbPie.put("genres",gp);
		tbPie.put("markets",mp);
		tbPie.put("ages",agep);
		tbPie.put("availability",avap);
		tbPie.put("status",statp);
	}
}
