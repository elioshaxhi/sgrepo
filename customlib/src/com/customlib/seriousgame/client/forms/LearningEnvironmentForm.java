/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.forms;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.interfaces.SeriousGameDataInteractionForm;
import com.customlib.seriousgame.client.models.LearningEnvironment;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

/**
 *
 * @author NERTIL
 */
public class LearningEnvironmentForm extends FormPanel implements SeriousGameDataInteractionForm {

	private MainEntryPointInterface entrypoint;
	private MyExtendedClasses.CostomTextArea feedback;
	private MyExtendedClasses.CostomTextArea motivation;
	private MyExtendedClasses.CostomTextArea sociality;
	private MyExtendedClasses.CostomTextArea granualityOfLearning;
	private MyExtendedClasses.CostomTextArea transfer;
	private MyExtendedClasses.CostomTextArea assessment;
	private MyExtendedClasses.CostomTextArea supportToExploitationOtherMedia;
	private MyExtendedClasses.CostomTextArea supportToActiveLearning;
	private MyExtendedClasses.CostomTextArea personalizationAndAdaptation;
	private MyExtendedClasses.CostomTextArea other;
	private boolean changes = false;
	private LearningEnvironment learningEnvironment;

	public LearningEnvironmentForm(final MainEntryPointInterface entrypoint) {
		this.entrypoint = entrypoint;

		setStyleName("sg-form-infogroup");
		getHeader().setStyleName("sg-form-infogroup-header");
		setBodyStyleName("sg-form-infogroup-body");
		setBorders(false);
		setBodyBorder(false);
		setLabelWidth(Utilities.formLabelWidth);
		setHeaderVisible(false);
		setScrollMode(Style.Scroll.AUTO);

		setBodyBorder(false);
		setHeaderVisible(false);
		setButtonAlign(Style.HorizontalAlignment.CENTER);
		FormLayout layout = new FormLayout();
		layout.setLabelWidth(110);
		setLayout(layout);
		setScrollMode(Style.Scroll.AUTO);
		setPadding(5);

		FormData formData = new FormData("-15");

		feedback = new MyExtendedClasses.CostomTextArea(Constants.Strings.le_feedback(), Constants.Strings.tt_le_feedback());
		add(feedback, formData);

		motivation = new MyExtendedClasses.CostomTextArea(Constants.Strings.le_motivation(), Constants.Strings.tt_le_motivation());
		add(motivation, formData);

		sociality = new MyExtendedClasses.CostomTextArea(Constants.Strings.le_sociality(), Constants.Strings.tt_le_sociality());
		add(sociality, formData);

		granualityOfLearning = new MyExtendedClasses.CostomTextArea(Constants.Strings.le_granualityOfLearning(), Constants.Strings.tt_le_granualityOfLearning());
		add(granualityOfLearning, formData);

		transfer = new MyExtendedClasses.CostomTextArea(Constants.Strings.le_transfer(), Constants.Strings.tt_le_transfer());
		add(transfer, formData);

		assessment = new MyExtendedClasses.CostomTextArea(Constants.Strings.le_assessment(), Constants.Strings.tt_le_assessment());
		add(assessment, formData);

		supportToExploitationOtherMedia = new MyExtendedClasses.CostomTextArea(Constants.Strings.le_supportToExploitationOtherMedia(), null);
		add(supportToExploitationOtherMedia, formData);

		supportToActiveLearning = new MyExtendedClasses.CostomTextArea(Constants.Strings.le_supportToActiveLearning(), Constants.Strings.tt_le_supportToActiveLearning());
		add(supportToActiveLearning, formData);

		personalizationAndAdaptation = new MyExtendedClasses.CostomTextArea(Constants.Strings.le_personalizationAndAdaptation(), null);
		add(personalizationAndAdaptation, formData);

		other = new MyExtendedClasses.CostomTextArea(Constants.Strings.le_other(), null);
		add(other, formData);
	}

	@Override
	public void updateRecord(SeriousGame record) {
		LearningEnvironment learningEnvironment = new LearningEnvironment(-1,
			record.getId(),
			feedback.getValue(),
			motivation.getValue(),
			sociality.getValue(),
			granualityOfLearning.getValue(),
			transfer.getValue(),
			assessment.getValue(),
			supportToExploitationOtherMedia.getValue(),
			supportToActiveLearning.getValue(),
			personalizationAndAdaptation.getValue(),
			other.getValue(),
			hasChanges());
		record.set(SeriousGame.TagLearningEnvironment, learningEnvironment);
	}

	@Override
	public void setRecord(SeriousGame record) {
		LearningEnvironment learningEnvironment = record.get(SeriousGame.TagLearningEnvironment);

		feedback.setValue(learningEnvironment.getFeedback());
		motivation.setValue(learningEnvironment.getMotivation());
		sociality.setValue(learningEnvironment.getSociality());
		granualityOfLearning.setValue(learningEnvironment.getGradualityOfLearning());
		transfer.setValue(learningEnvironment.getTransfer());
		assessment.setValue(learningEnvironment.getAssessment());
		supportToExploitationOtherMedia.setValue(learningEnvironment.getSupportToExploitation());
		supportToActiveLearning.setValue(learningEnvironment.getSupportToLearnByDoing());
		personalizationAndAdaptation.setValue(learningEnvironment.getPersonalizationAndAdaptation());
		other.setValue(learningEnvironment.getOther());
	}

	@Override
	public boolean hasChanges() {
		return this.changes;
	}

	@Override
	public void setChanges(boolean changes) {
		this.changes = changes;
	}
}
