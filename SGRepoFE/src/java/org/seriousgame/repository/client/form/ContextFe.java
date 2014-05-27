/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client.form;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.ContextAndAnalysis;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import org.seriousgame.repository.client.fieldsetsContext.ContextFieldSetParent;

/**
 *
 * @author nertil
 */
public class ContextFe extends FormPanel {

	private MainEntryPointInterface entrypoint = null;
	private ContextFieldSetParent contextParent;
	private FormData formData;

	public ContextFe(MainEntryPointInterface entrypoint) {
		this.entrypoint = entrypoint;

		formData = new FormData("-15");
		contextParent = new ContextFieldSetParent(entrypoint, Constants.Strings.contexts());
		setStyleName("sg-form-infogroup");
		getHeader().setStyleName("sg-form-infogroup-header");
		setBodyStyleName("sg-form-infogroup-body");
		setBorders(true);
		setLabelWidth(Utilities.formLabelWidth);
		setHeaderVisible(false);

		setButtonAlign(Style.HorizontalAlignment.CENTER);
		setLayout(new FormLayout());
		setPadding(5);

		setBodyBorder(true);
		setHeaderVisible(false);
		setScrollMode(Style.Scroll.AUTO);

	}

//	@Override
	public void setRecord(SeriousGame record) {

		ContextAndAnalysis contextAndAnalysis = record.get(SeriousGame.TagContextAndAnalysi);
		if (contextAndAnalysis != null) {
			contextParent.setData(contextAndAnalysis);
		} else {
			contextParent.setExpanded(false);
			contextParent.setEnabled(false);

		}
		add(contextParent, new FormData("-2"));


		/**
		 * ottengo la lista dei generic model di context
		 */
	}
}
