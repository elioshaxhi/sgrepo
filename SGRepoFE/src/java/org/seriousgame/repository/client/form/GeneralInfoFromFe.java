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
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.form.*;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Image;
import java.util.List;

/**
 *
 * @author NERTIL
 */
public class GeneralInfoFromFe extends FormPanel {

	private MainEntryPointInterface entrypoint;
	private Html title;
	private ContentPanel attachments;
	private MyExtendedClasses.SeriouGameLabelField publisher;
	private MyExtendedClasses.SeriouGameLabelField webSite;
	private MyExtendedClasses.SeriouGameLabelField status;
	private MyExtendedClasses.SeriouGameLabelField yearOfFirstRelease;
	private MyExtendedClasses.SeriouGameLabelField yearOfLastRelease;
	private MyExtendedClasses.SeriouGameLabelField availability;
	private MyExtendedClasses.SeriouGameLabelField description;
	private MyExtendedClasses.SeriouGameLabelField keywords;
	private MyExtendedClasses.SeriouGameLabelField ages;
	private MyExtendedClasses.SeriouGameLabelField genres;
	private MyExtendedClasses.SeriouGameLabelField markets;
	private MyExtendedClasses.SeriouGameLabelField learningCurve;
	private MyExtendedClasses.SeriouGameLabelField learningTime;
	private MyExtendedClasses.SeriouGameLabelField gamePlatform;
	private MyExtendedClasses.SeriouGameLabelField processor;
	private MyExtendedClasses.SeriouGameLabelField ram;
	private MyExtendedClasses.SeriouGameLabelField disk;
	private MyExtendedClasses.SeriouGameLabelField bandwidth;
	private MyExtendedClasses.SeriouGameLabelField inputDeviceRequired;
	private MyExtendedClasses.SeriouGameLabelField deploymentStyle;
	private MyExtendedClasses.SeriouGameLabelField targetRange;
	private Technology technology;
	private boolean changes = false;

	public GeneralInfoFromFe(final MainEntryPointInterface entrypoint) {
		this.entrypoint = entrypoint;



		setStyleName("sg-form-infogroup");
		getHeader().setStyleName("sg-form-infogroup-header");
		setBodyStyleName("sg-form-infogroup-body");
		setBorders(false);
		setHeaderVisible(false);

//		setWidth(Utilities.formWidth - 20);

		FormLayout layout = new FormLayout(LabelAlign.LEFT);
		layout.setLabelWidth(Utilities.formLabelWidth);
		setLayout(layout);
		setBodyBorder(false);
		setScrollMode(Style.Scroll.AUTO);


		FormData formData = new FormData("-40");

		title = new Html();
		title.setStyleName("sg-form-title");
		add(title, formData);

		attachments = new ContentPanel();
		attachments.setHeaderVisible(false);
		attachments.setBodyBorder(false);
		attachments.setBorders(false);
		attachments.setScrollMode(Style.Scroll.AUTOX);
		attachments.setBodyStyleName("sg-form-attach");
		attachments.setLayoutOnChange(true);

		add(attachments, formData);

		publisher = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.sg_publisher());
		add(publisher, formData);

		webSite = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.sg_webSite());
		add(webSite, formData);

		status = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.sg_status());
		add(status, formData);

		yearOfFirstRelease = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.sg_yearFirstRelease());
		add(yearOfFirstRelease, formData);

		yearOfLastRelease = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.sg_yearLastRelease());
		add(yearOfLastRelease, formData);

		availability = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.sg_availability());
		add(availability, formData);

		description = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.sg_freeDescription());
		add(description, formData);

		keywords = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.sg_keywords());
		add(keywords, formData);

		ages = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.sg_ages());
		add(ages, formData);

		genres = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.sg_genres());
		add(genres, formData);

		markets = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.sg_markets());
		add(markets, formData);

		learningCurve = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.sg_learningCurve());
		add(learningCurve, formData);

		learningTime = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.sg_EffectiveLearningTime());
		add(learningTime, formData);

		gamePlatform = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.tec_gamePlatform());
		add(gamePlatform, formData);

		processor = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.tec_gamePlatform());
		add(processor, formData);

		ram = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.tec_RAM());
		add(ram, formData);

		disk = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.tec_Disc());
		add(disk, formData);

		bandwidth = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.tec_bandwidth());
		add(bandwidth, formData);

		inputDeviceRequired = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.tec_inputDeviceRequired());
		add(inputDeviceRequired, formData);

		deploymentStyle = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.tec_deploymentStyle());
		add(deploymentStyle, formData);

		targetRange = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.tec_targetRange());
		add(targetRange, formData);
	}

	public void setData(SeriousGame sg) {

		title.setHtml((String) sg.get(SeriousGame.TagTitle.toString()));

		attachments.removeAll();

		technology = sg.get(SeriousGame.TagTechnology);

		List<Attachment> attachmentsList = (List<Attachment>) sg.get(SeriousGame.TagAttachments.toString());
		if (attachmentsList != null && !attachmentsList.isEmpty()) {
			String filename = "";
			for (Attachment attachment : attachmentsList) {
				String tempfName = attachment.get(ModelTag.name.toString());
				if (Utilities.isImage(tempfName)) {
					filename = tempfName;
					Image img = new Image(GWT.getHostPageBaseURL() + Utilities.uploadedFolder + "/" + filename);
					img.setStyleName("sg-form-image");
					attachments.add(img);
				}
			}
		}
		if (attachments.getItemCount() > 0) {
			attachments.setVisible(true);
		} else {
			attachments.setVisible(false);
		}

		setFieldValue(publisher, Utilities.getModelValue(entrypoint.getDatabase().getPublisherList(), (Integer) sg.get(SeriousGame.TagPublisherID.toString())));
		setFieldValue(webSite, (String) sg.get(SeriousGame.TagWebSite.toString()));
		setFieldValue(status, Utilities.getModelValue(entrypoint.getDatabase().getStatusList(), (Integer) sg.get(SeriousGame.TagStatusID.toString())));
		setFieldValue(yearOfFirstRelease, String.valueOf((Integer) sg.get(SeriousGame.TagYearFirstRelease.toString())));
		setFieldValue(yearOfLastRelease, String.valueOf((Integer) sg.get(SeriousGame.TagYearLastRelease.toString())));
		setFieldValue(availability, Utilities.getModelValue(entrypoint.getDatabase().getAvailabilityList(), (Integer) sg.get(SeriousGame.TagAvailabilityID.toString())));
		setFieldValue(description, (String) sg.get(SeriousGame.TagFreeDescription.toString()));
		setFieldValue(keywords, (String) sg.get(SeriousGame.TagKeywords.toString()));
		setFieldValue(ages, Utilities.getListValue((List<GenericModel>) sg.get(SeriousGame.TagAges)));
		setFieldValue(genres, Utilities.getListValue((List<GenericModel>) sg.get(SeriousGame.TagGenres)));
		setFieldValue(markets, Utilities.getListValue((List<GenericModel>) sg.get(SeriousGame.TagMarkets)));
		setFieldValue(deploymentStyle, Utilities.getListValue((List<GenericModel>) technology.getDeploymentStyle()));
		setFieldValue(targetRange, Utilities.getListValue((List<GenericModel>) technology.getTargetRange()));

		setFieldValue(learningCurve, Utilities.getModelValue(entrypoint.getDatabase().getLearningCurveList(), (Integer) sg.get(SeriousGame.TagLearningCurveID)));
		setFieldValue(learningTime, Utilities.getModelValue(entrypoint.getDatabase().getEffectiveLearningTimeList(), (Integer) sg.get(SeriousGame.TagEffectiveLearningTimeID)));

		setFieldValue(gamePlatform, Utilities.getModelValue(entrypoint.getDatabase().getGamePlatformList(), (Integer) technology.getGamePlatformID()));
		setFieldValue(bandwidth, Utilities.getModelValue(entrypoint.getDatabase().getBandwidthList(), (Integer) technology.getBandwidthID()));
		setFieldValue(inputDeviceRequired, Utilities.getModelValue(entrypoint.getDatabase().getInputDeviceRequiredList(), (Integer) technology.getInputDeviceReruiredID()));

		setFieldValue(processor, technology.getProcessor());
		setFieldValue(ram, technology.getRAM().toString());
		setFieldValue(disk, technology.getDisk().toString());


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
