/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analytics.games.client.grafics;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.chart.client.chart.Chart;
import com.sencha.gxt.chart.client.chart.Chart.Position;
import com.sencha.gxt.chart.client.chart.Legend;
import com.sencha.gxt.chart.client.chart.series.PieSeries;
import com.sencha.gxt.chart.client.chart.series.Series.LabelPosition;
import com.sencha.gxt.chart.client.chart.series.SeriesLabelConfig;
import com.sencha.gxt.chart.client.draw.Gradient;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite.TextAnchor;
import com.sencha.gxt.chart.client.draw.sprite.TextSprite.TextBaseline;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.widget.core.client.FramedPanel;
import java.util.List;
import java.util.Random;
import org.analytics.games.client.form.Colori;
import org.analytics.games.client.models.UnicModel;
import org.analytics.games.client.models.UnicModelProperties;

/**
 *
 * @author Elion Haxhi
 */
public class Torta implements IsWidget {
	private static final UnicModelProperties props = GWT.create(UnicModelProperties.class);
	Chart<UnicModel> chart =null;
	ListStore<UnicModel> store =null;
	Legend<UnicModel> legend =null;
	PieSeries<UnicModel> series=null;
	FramedPanel fp=new FramedPanel();

	AccessoriesChart acc=null;

	static Random rn=new Random(System.currentTimeMillis());

	public interface UnicModelPropertyAccess extends PropertyAccess<UnicModel> {

		ValueProvider<UnicModel, Integer> numGames();

		ValueProvider<UnicModel, String> nomeItem();

		ValueProvider<UnicModel, String> numQuantita();

		@Path("id")
		ModelKeyProvider<UnicModel> key();
	}

	@Override
	public Widget asWidget(){
	return fp;
	}

	public Torta(String nameContent) {
		fp.setHeadingText(nameContent);
		fp.setHeaderVisible(false);
		fp.setBodyBorder(false);
		fp.addStyleName("page");
		fp.setPixelSize(600,600);

	//	setPixelSize(400, 400);
	}

	public void setMappa(AccessoriesChart acc,String tipoTorta)
	{
		this.acc=acc;
		this.chart = acc.tbChart.get(tipoTorta);
		this.store=acc.tbStore.get(tipoTorta);
		this.legend=acc.tbLegend.get(tipoTorta);
		this.series=acc.tbPie.get(tipoTorta);
	}

	/**
	 *
	 * @param coda
	 * @param title
	 */
	public void setCoda(List<UnicModel> coda,String title) {
		store.addAll(coda);
		chart.setDefaultInsets(50);
		chart.setStore(store);
		chart.setShadowChart(true);
		chart.setAnimated(true);
		chart.setBorders(false);
		setGradient(Colori.getColor(), coda.size());
		sgPieSeries(title);
		setPieColor(Colori.getColor(), coda.size());
		sgLegend();
		chart.addSeries(series);
		chart.setLegend(legend);

		fp.add(chart);
	}

	/**
	 *
	 */
	public void sgLegend() {
		legend.setPosition(Position.RIGHT);
		legend.setItemHighlighting(true);
		legend.setItemHiding(true);
		legend.getBorderConfig().setStrokeWidth(0);
	}

	/**
	 *
	 * @param arr
	 * @param dim
	 */
	public void setPieColor(Gradient[] arr, int dim) {
		for (int i = 0; i < dim; i++) {
			if(i<arr.length)
			series.addColor(arr[i]);
			else
				chart.addGradient(arr[rn.nextInt(arr.length)]);
		}
	}

	/**
	 *
	 * @param arr
	 * @param dim
	 */
	public void setGradient(Gradient[] arr, int dim) {
		for (int i = 0; i < dim; i++) {
			if(i<arr.length)
			chart.addGradient(arr[i]);
			else
				chart.addGradient(arr[rn.nextInt(arr.length)]);
		}
	}

	/**
	 *
	 * @param title
	 */
	public void sgPieSeries(String title) {
		series = new PieSeries<UnicModel>();
		series.setAngleField(props.numGames());

		TextSprite textConfig = new TextSprite();
		textConfig.setFont("Arial");
		textConfig.setFontSize(18);
		textConfig.setTextBaseline(TextBaseline.MIDDLE);
		textConfig.setTextAnchor(TextAnchor.MIDDLE);
		textConfig.setZIndex(15);
		SeriesLabelConfig<UnicModel> labelConfig = new SeriesLabelConfig<UnicModel>();
		labelConfig.setSpriteConfig(textConfig);
		labelConfig.setLabelPosition(LabelPosition.START);
		labelConfig.setValueProvider(props.numQuantita(), new StringLabelProvider<String>());
		series.setLabelConfig(labelConfig);
		series.setHighlighting(true);
		series.setLegendValueProvider(props.nomeItem(), new LabelProvider<String>() {

			@Override
			public String getLabel(String item) {
				return item;
			}
		});
	}

}
