/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client.form;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.*;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.widget.form.*;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

/**
 *
 * @author NERTIL
 */
public class LearningEnvironmentFormFe extends FormPanel {

	private MainEntryPointInterface entrypoint;
	private MyExtendedClasses.SeriouGameLabelField feedback;
	private MyExtendedClasses.SeriouGameLabelField motivation;
	private MyExtendedClasses.SeriouGameLabelField sociality;
	private MyExtendedClasses.SeriouGameLabelField granualityOfLearning;
	private MyExtendedClasses.SeriouGameLabelField transfer;
	private MyExtendedClasses.SeriouGameLabelField assessment;
	private MyExtendedClasses.SeriouGameLabelField supportToExploitationOtherMedia;
	private MyExtendedClasses.SeriouGameLabelField supportToActiveLearning;
	private MyExtendedClasses.SeriouGameLabelField personalizationAndAdaptation;
	private MyExtendedClasses.SeriouGameLabelField other;
	private boolean flag = false;
	private LearningEnvironment learningEnvironment;
	private FieldSet fieldSet;

	public LearningEnvironmentFormFe(final MainEntryPointInterface entrypoint) {
		this.entrypoint = entrypoint;

		fieldSet = new FieldSet();

		setStyleName("sg-form-infogroup");
		getHeader().setStyleName("sg-form-infogroup-header");
		setBodyStyleName("sg-form-infogroup-body");
		setBorders(false);
		setBodyBorder(false);
		setHeaderVisible(false);
		setScrollMode(Style.Scroll.AUTO);

		setButtonAlign(Style.HorizontalAlignment.CENTER);
		FormLayout layout = new FormLayout(LabelAlign.LEFT);
		layout.setLabelWidth(Utilities.formLabelWidth);
		setLayout(layout);
		FormLayout layoutFieldSet = new FormLayout(LabelAlign.LEFT);
		layoutFieldSet.setLabelWidth(Utilities.formLabelWidth);
//		setLayout(layout);
		fieldSet.setLayout(layoutFieldSet);
		fieldSet.setBorders(false);



		FormData formData = new FormData("-40");


		feedback = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.le_feedback());
		fieldSet.add(feedback, new FormData("-3"));

		motivation = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.le_motivation());
		fieldSet.add(motivation, new FormData("-3"));

		sociality = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.le_sociality());
		fieldSet.add(sociality, new FormData("-3"));

		granualityOfLearning = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.le_granualityOfLearning());
		fieldSet.add(granualityOfLearning, new FormData("-3"));

		transfer = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.le_transfer());
		fieldSet.add(transfer, new FormData("-3"));

		assessment = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.le_assessment());
		fieldSet.add(assessment, new FormData("-3"));

		supportToExploitationOtherMedia = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.le_supportToExploitationOtherMedia());
		fieldSet.add(supportToExploitationOtherMedia, new FormData("-3"));

		supportToActiveLearning = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.tt_le_supportToActiveLearning());
		fieldSet.add(supportToActiveLearning, new FormData("-3"));

		personalizationAndAdaptation = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.le_personalizationAndAdaptation());
		fieldSet.add(personalizationAndAdaptation, new FormData("-3"));

		other = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.le_other());
		fieldSet.add(other, new FormData("-3"));



	}

	public void setData(SeriousGame sg) {


		learningEnvironment = sg.get(SeriousGame.TagLearningEnvironment);


		setFieldValue(feedback, learningEnvironment.getFeedback());
		setFieldValue(motivation, learningEnvironment.getMotivation());
		setFieldValue(sociality, learningEnvironment.getSociality());
		setFieldValue(granualityOfLearning, learningEnvironment.getGradualityOfLearning());
		setFieldValue(transfer, learningEnvironment.getTransfer());
		setFieldValue(assessment, learningEnvironment.getAssessment());
		setFieldValue(supportToExploitationOtherMedia, learningEnvironment.getSupportToExploitation());
		setFieldValue(supportToActiveLearning, learningEnvironment.getSupportToLearnByDoing());
		setFieldValue(personalizationAndAdaptation, learningEnvironment.getPersonalizationAndAdaptation());
		setFieldValue(other, learningEnvironment.getOther());
		if (learningEnvironment.getFeedback() != null || learningEnvironment.getMotivation() != null || learningEnvironment.getSociality() != null || learningEnvironment.getGradualityOfLearning() != null || learningEnvironment.getTransfer() != null || learningEnvironment.getAssessment() != null || learningEnvironment.getSupportToExploitation() != null || learningEnvironment.getSupportToLearnByDoing() != null || learningEnvironment.getPersonalizationAndAdaptation() != null || learningEnvironment.getOther() != null) {
			flag = true;
		}

		if (!flag) {
			fieldSet.setExpanded(false);
			fieldSet.setEnabled(false);
			fieldSet.setHeading(Constants.Strings.arch_noItems());
			fieldSet.setCollapsible(true);
		}
		add(fieldSet, new FormData("-15"));



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
