/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.interfaces.ClosureFormInterface;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.widget.*;
import com.extjs.gxt.ui.client.widget.layout.FillData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;

/**
 *
 * @author nertil
 */
public class SeriousGameForm extends Window implements ClosureFormInterface {

	private MainEntryPoint entrypoint = null;
	private boolean okClose = false;
	private SeriousGameTabPanel sgTabPanel;

	public SeriousGameForm(MainEntryPoint entrypoint) {
		this.entrypoint = entrypoint;

		setSize((int) (com.google.gwt.user.client.Window.getClientWidth() * Constants.scaleFormFactor), (int) (com.google.gwt.user.client.Window.getClientHeight() * Constants.scaleFormFactor));
		setMinWidth(Constants.smallFormMinWidth);
		setMinHeight(Constants.smallFormMinWidth);
		setMaximizable(true);
		setCollapsible(false);
//		setScrollMode(Style.Scroll.AUTOY);
		setLayout(new FillLayout(Style.Orientation.HORIZONTAL));





//		FormLayout layout = new FormLayout();
//		layout.setLabelWidth(Utilities.formLabelWidth);
//		setLayout(layout);







		setModal(true);
		setBodyBorder(false);
		setStyleName("sg-form");
		setBodyStyleName("sg-form-body");
		setBorders(false);
		setHeading(Constants.Strings.description());

		sgTabPanel = new SeriousGameTabPanel(entrypoint);


		add(sgTabPanel, new FillData());
	}

	public void show(SeriousGame sg) {

		sgTabPanel.SetData(sg);

		super.show();

		setWidth((int) (com.google.gwt.user.client.Window.getClientWidth() * Constants.scaleFormFactor));
		setHeight((int) (com.google.gwt.user.client.Window.getClientHeight() * Constants.scaleFormFactor));
	}

	@Override
	public boolean isOkClose() {
		return okClose;
	}
}
