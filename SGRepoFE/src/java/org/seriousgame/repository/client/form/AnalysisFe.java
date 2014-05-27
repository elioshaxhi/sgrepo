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
import org.seriousgame.repository.client.fieldsetsAnalysis.AnalysisFieldSetParent;

/**
 *
 * @author nertil
 */
public class AnalysisFe extends FormPanel {

	private MainEntryPointInterface entrypoint = null;
	private AnalysisFieldSetParent analysisParent;
	private FormData formData;

	public AnalysisFe(MainEntryPointInterface entrypoint) {
		this.entrypoint = entrypoint;

		formData = new FormData("-15");
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
		analysisParent = new AnalysisFieldSetParent(this.entrypoint, Constants.Strings.analysis());
		if (contextAndAnalysis != null) {
			analysisParent.setData(contextAndAnalysis);
		} else {
			analysisParent.setExpanded(false);
			analysisParent.setEnabled(false);
		}
		add(analysisParent, new FormData("-2"));


		/**
		 * ottengo la lista dei generic model di context
		 */
	}
}
