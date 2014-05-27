/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Padding;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.Layout;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import java.util.List;

/**
 *
 * @author NERTIL
 */
public class FooterPanel extends HorizontalPanel {

	Image euLogo = new Image("images/logo.EU.png");
	Image fp7Logo = new Image("images/logo.FP7.png");
	Image galanoeLogo = new Image("images/gala-1med.png");
	Html contact = new Html(Constants.Strings.sg_repo_fe_contact());
	Html contactLink = new Html(Constants.Strings.sg_repo_fe_contactLink());
	VerticalPanel editPanel = new VerticalPanel();
	HorizontalPanel textPanel = new HorizontalPanel();
	Layout l = new FitLayout();
	Html text = new Html(Constants.Strings.sg_repo_fe_text());
//	LayoutContainer containerEdit = new LayoutContainer();
//	LayoutContainer containerContact = new LayoutContainer();
	boolean primo = true;
	MainEntryPoint entrypoint=null;

	public FooterPanel(MainEntryPoint entrypoint) {
		this.entrypoint=entrypoint;
		euLogo.setStyleName("footer-logo");
		euLogo.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open("http://cordis.europa.eu/fp7/home_en.html", "_blank", null);
			}
		});


		editPanel.setHorizontalAlign(Style.HorizontalAlignment.CENTER);
		editPanel.setWidth(260);
		editPanel.setStyleName("footer-edit-panel");






		contact.setStyleName("footer-edit-panel-contact-label");
		contactLink.setStyleName("footer-edit-panel-contact-label");


		fp7Logo.setStyleName("footer-logo");
		fp7Logo.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open("https://ec.europa.eu/digital-agenda/", "_blank", null);
			}
		});

		galanoeLogo.setStyleName("header-galanoe");
		galanoeLogo.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.open("http://www.galanoe.eu/", "_blank", null);
			}
		});

		setStyleName("footer");
		setHeight("100px");

	}




	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);

		HBoxLayout layout = new HBoxLayout();
		layout.setPadding(new Padding(2));
		layout.setHBoxLayoutAlign(HBoxLayout.HBoxLayoutAlign.TOP);
		setLayout(layout);

		HBoxLayoutData noflex = new HBoxLayoutData(new Margins(0, 5, 0, 0));
		noflex.setFlex(0);
		HBoxLayoutData flex = new HBoxLayoutData(new Margins(0, 5, 0, 0));
		flex.setFlex(1);
		Button edit = new Button("Edit", new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Window.open("http://localhost:8084/SGRepoBE/", "_blank", "");
			}
		});
		edit.setWidth(70);
		text.setWidth(350);
		text.setStyleName("footer-edit-panel-contact-texti");

		text.setWidth(350);
		text.setStyleName("footer-edit-panel-contact-texti");



//		HBoxLayout hLayout = new HBoxLayout();
//		hLayout.setPadding(new Padding(2));
//		hLayout.setHBoxLayoutAlign(HBoxLayout.HBoxLayoutAlign.MIDDLE);

//		containerEdit.setLayout(hLayout);
//		containerEdit.add(edit);

//		HBoxLayout hLayoutContact = new HBoxLayout();
//		hLayoutContact.setPadding(new Padding(2));
//		hLayoutContact.setHBoxLayoutAlign(HBoxLayout.HBoxLayoutAlign.MIDDLE);
//
//		containerContact.setLayout(hLayoutContact);
//		containerContact.add(contact);



//		VBoxLayout vLayout = new VBoxLayout();
//		vLayout.setPadding(new Padding(2));
//		vLayout.setVBoxLayoutAlign(VBoxLayout.VBoxLayoutAlign.CENTER);
//		editPanel.setLayout(vLayout);

		editPanel.add(edit);
//		textPanel.setHorizontalAlign(Style.HorizontalAlignment.LEFT);
//		textPanel.setStyleName("footer-texti-panel");

//		text.setWidth(465);

//		textPanel.setLayout(l);
//		textPanel.add(text);

		editPanel.add(contact);
		editPanel.add(contactLink);
//		text.setWidth(350);


		add(euLogo, noflex);
		add(fp7Logo, noflex);
		add(galanoeLogo, noflex);
		add(text, flex);
//		add(new Html(), flex);
		add(editPanel, noflex);

		if (primo) {
			primo = false;
			setHeight("auto");
		}
	}
	public void nisu(AsyncCallback<SeriousGame> callback){
	}


}
