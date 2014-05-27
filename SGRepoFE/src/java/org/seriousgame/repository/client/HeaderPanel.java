/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client;

import com.customlib.seriousgame.client.Constants;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Padding;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;

/**
 *
 * @author NERTIL
 */
public class HeaderPanel extends LayoutContainer {

	Image galanoe = new Image("images/gala-1med.png");

	public HeaderPanel(MainEntryPoint entrypoint) {
		galanoe.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open("http://www.galanoe.eu/", "_blank", null);
			}
		});
		galanoe.setStyleName("header-galanoe");

		setStyleName("header");



	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);

		HBoxLayout layout = new HBoxLayout();
		layout.setPadding(new Padding(2));
		layout.setHBoxLayoutAlign(HBoxLayout.HBoxLayoutAlign.MIDDLE);
		setLayout(layout);

		Html text = new Html(Constants.Strings.sg_repo_fe_header());
		text.setWidth(600);
		text.setStyleName("header-text");


		HBoxLayoutData noflex = new HBoxLayoutData(new Margins(0, 5, 0, 0));
		noflex.setFlex(0);
		HBoxLayoutData flex = new HBoxLayoutData(new Margins(0, 5, 0, 0));
		flex.setFlex(1);

		add(galanoe, noflex);
		add(text, flex);
       // add(new Html(), flex);
Button analytics = new Button("Analytics", new SelectionListener<ButtonEvent>() {
			@Override

			public void componentSelected(ButtonEvent ce) {
				Window.open("http://localhost:8084/SGRepoAnalytics/", "_blank", "");

			}
		});
		analytics.setWidth(70);

		add(analytics, noflex);

	}
}
