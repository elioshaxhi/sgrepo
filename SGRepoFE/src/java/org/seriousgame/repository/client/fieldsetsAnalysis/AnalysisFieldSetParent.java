/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client.fieldsetsAnalysis;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.Analysis;
import com.customlib.seriousgame.client.models.ContextAndAnalysis;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

/**
 *
 * @author nomecognome
 */
public class AnalysisFieldSetParent extends FieldSet {

	private MainEntryPointInterface mainEntryPointInterface = null;
	private AnalysisFieldSet analysisFieldSet;
	private FormData formData;
	private boolean flag = true;
	private ScoreLabelSet effectiveness;
	private ScoreLabelSet efficiency;
	private ScoreLabelSet usability;
	private ScoreLabelSet diffusion;
	private ScoreLabelSet feedback;
	private ScoreLabelSet exploitability;
	private ScoreLabelSet reusability;
	private ScoreLabelSet capabilityOfMotivating;
	private ScoreLabelSet capabilityOfEngaging;
	private MyExtendedClasses.SeriouGameLabelField methodology;
	private Analysis analysis;

	public AnalysisFieldSetParent(final MainEntryPointInterface mainEntryPointInterface, String nameComponent) {

		this.mainEntryPointInterface = mainEntryPointInterface;
		formData = new FormData("-15");

		setHeading(nameComponent);
		setCollapsible(true);

		FormLayout layout = new FormLayout(LabelAlign.LEFT);
		layout.setLabelWidth(Utilities.formLabelWidth);
		setLayout(layout);

	}

	public void setData(final ContextAndAnalysis contextAndAnalysis) {
		analysisFieldSet = new AnalysisFieldSet(this.mainEntryPointInterface, Constants.Strings.analysis_referenceOfContext());
		if (contextAndAnalysis.getAnalysis() != null) {
			analysisFieldSet.setData(contextAndAnalysis.getContexts(), contextAndAnalysis.getAnalysis());

			add(analysisFieldSet, new FormData("-15"));
			analysis = contextAndAnalysis.getAnalysis();



			String value = analysis.getMethodology();

			methodology = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.analysis_methodology());
			setFieldValue(methodology, value);
			add(methodology, new FormData("-3"));



			effectiveness = new ScoreLabelSet(Constants.Strings.analysis_effectiveness(), 1, "");

			effectiveness.setScore(analysis.getEffectiveness());
			effectiveness.setMotivation(effectiveness.getMotivation(), analysis.getEffectivenessDescription());
			effectiveness.setToolTip(Constants.Strings.tt_analysis_effectiveness());
			add(effectiveness, new FormData("-3"));

			efficiency = new ScoreLabelSet(Constants.Strings.analysis_efficiency(), 1, "");
			efficiency.setScore(analysis.getEfficiency());
			efficiency.setMotivation(efficiency.getMotivation(), analysis.getEfficiencyDescription());
			efficiency.setToolTip(Constants.Strings.tt_analysis_efficiency());
			add(efficiency, new FormData("-3"));

			usability = new ScoreLabelSet(Constants.Strings.analysis_usability(), 1, "");
			usability.setScore(analysis.getUsability());
			usability.setMotivation(usability.getMotivation(), analysis.getUsabilityDescription());
			usability.setToolTip(Constants.Strings.tt_analysis_usability());
			add(usability, new FormData("-3"));

			diffusion = new ScoreLabelSet(Constants.Strings.analysis_diffusion(), 1, "");
			diffusion.setScore(analysis.getDiffusion());
			diffusion.setMotivation(diffusion.getMotivation(), analysis.getDiffusionDescription());
			diffusion.setToolTip(Constants.Strings.tt_analysis_diffusion());
			add(diffusion, new FormData("-3"));

			feedback = new ScoreLabelSet(Constants.Strings.analysis_feedback(), 1, "");
			feedback.setScore(analysis.getFeedbackAssessmentSupport());
			feedback.setMotivation(feedback.getMotivation(), analysis.getFeedbackAssessmentSupportDescription());
			add(feedback, new FormData("-3"));

			exploitability = new ScoreLabelSet(Constants.Strings.analysis_exploitability(), 1, "");
			exploitability.setScore(analysis.getExploitabilityLearningContext());
			exploitability.setMotivation(exploitability.getMotivation(), analysis.getExploitabilityLearningContextDescription());
			exploitability.setToolTip(Constants.Strings.tt_analysis_exploitability());
			add(exploitability, new FormData("-3"));

			reusability = new ScoreLabelSet(Constants.Strings.analysis_reusability(), 1, "");
			reusability.setScore(analysis.getReusabilityDifferentLearningContext());
			reusability.setMotivation(reusability.getMotivation(), analysis.getReusabilityDifferentLearningContextDescription());
			add(reusability, new FormData("-3"));

			capabilityOfMotivating = new ScoreLabelSet(Constants.Strings.analysis_capabilityOfMotivation(), 1, "");
			capabilityOfMotivating.setScore(analysis.getCapabilityMotivatingUser());
			capabilityOfMotivating.setMotivation(capabilityOfMotivating.getMotivation(), analysis.getCapabilityMotivatingUserDescription());
			add(capabilityOfMotivating, new FormData("-3"));

			capabilityOfEngaging = new ScoreLabelSet(Constants.Strings.analysis_capabilityOfEngaging(), 1, "");
			capabilityOfEngaging.setScore(analysis.getCapabilityEngagingUser());
			capabilityOfEngaging.setMotivation(capabilityOfEngaging.getMotivation(), analysis.getCapabilityEngagingUserDescription());
			add(capabilityOfEngaging, new FormData("-3"));


		} else {
			analysisFieldSet.setExpanded(false);
			analysisFieldSet.setEnabled(false);
		}


	}

	private void setFieldValue(Field field, String value) {
		if (value != null) {
			value = value.replaceAll("\n", "<br>");
			field.setVisible(true);
			field.setValue(value);
		} else {
			field.setVisible(false);
		}
	}
}
