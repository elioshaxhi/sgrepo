/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.forms;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.interfaces.ClosureFormInterface;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.ModelTag;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.*;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nertil
 */
public class GenericArchitectureForm extends Window implements ClosureFormInterface {

	private MainEntryPointInterface entrypoint = null;

	private FormPanel form;
	private TextField<String> name;
	private TextArea description;
	private TextArea specificSGCase;
	private ComboBox<GenericModel> type;
	private DualListField<GenericModel> relatedComponents = null;
	private boolean okClose = false;
	private GenericModel model;
	private GenericModel algorithm;
	private List<GenericModel> components;

	public GenericArchitectureForm(final MainEntryPointInterface entrypoint, String header, List<GenericModel> components) {
		this.entrypoint = entrypoint;
		this.components = components;

		setWidth(Utilities.smallformWidth);
		setHeading(header);
		setMinWidth(Utilities.smallformWidth);
		setLayout(new FitLayout());
		setStyleName("form-font");
		setModal(true);
		setClosable(false);
		setButtonAlign(Style.HorizontalAlignment.CENTER);

		Button ok_button = new Button(Constants.Strings.b_ok(), new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if (!form.isValid()) {
					return;
				}
				((Button) ce.getSource()).setEnabled(false);
				okClose = true;
				hide();
			}
		});
		Button cancel_button = new Button(Constants.Strings.b_cancel(), new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				hide();
			}
		});
		addButton(ok_button);
		addButton(cancel_button);

		FormData formData = new FormData("-20");

		/**
		 * i use thia controll for addint type field at algorithm form
		 */


		form = new FormPanel();
		form.setEncoding(FormPanel.Encoding.MULTIPART);
		form.setMethod(FormPanel.Method.POST);
		form.setAction("ServletUpload");
		form.setHeaderVisible(false);
		FormLayout layout = new FormLayout(FormPanel.LabelAlign.LEFT);
		layout.setLabelWidth(Utilities.formLabelWidth);
		form.setLayout(layout);
		form.setWidth(Utilities.smallformWidth);
		form.setBodyBorder(false);

		name = new TextField<String>();
		name.setFieldLabel(Constants.Strings.name());
		name.setAllowBlank(false);
		name.getFocusSupport().setPreviousId(getButtonBar().getId());
		name.setMaxLength(Utilities.varCharLimitShort);
		form.add(name, formData);

		description = new TextArea();
		description.setPreventScrollbars(true);
		description.setFieldLabel(Constants.Strings.description());
		description.setMaxLength(Utilities.textLimitNormal);
		form.add(description, formData);

		specificSGCase = new TextArea();
		specificSGCase.setPreventScrollbars(true);
		specificSGCase.setFieldLabel(Constants.Strings.arch_specificSGCase());
		specificSGCase.setMaxLength(Utilities.textLimitNormal);
		specificSGCase.setToolTip(Constants.Strings.tt_arch_specificSGCase());
		form.add(specificSGCase, formData);

		if (components != null) {
			relatedComponents = new DualListField<GenericModel>();
			relatedComponents.setMode(DualListField.Mode.INSERT);
			relatedComponents.setFieldLabel(Constants.Strings.arch_relatedSGComponents());

			ListField<GenericModel> from = relatedComponents.getFromList();
			from.setDisplayField(ModelTag.name.toString());
			ListStore<GenericModel> store = new ListStore<GenericModel>();
			store.add(components);
			from.setStore(store);
			ListField<GenericModel> to = relatedComponents.getToList();
			to.setDisplayField(ModelTag.name.toString());
			store = new ListStore<GenericModel>();
			to.setStore(store);

			form.add(relatedComponents, formData);
		}

		add(form);

	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * here you create the model and succesively prepare it for the inection
	 * at the database ,this implementation is for algorithm form without
	 * type element in relation.
	 */
	public GenericModel getArchitectureModel() {
		int id = -1;
		if (model != null) {
			id = model.getId();
		}
		List<Integer> ids = new ArrayList<Integer>();
		if (relatedComponents != null) {
			ids = Utilities.getIDList(relatedComponents.getToList().getStore().getModels());
		}
		model = new GenericModel(id, name.getValue(), description.getValue());
		model.set(ModelTag.specificSGCase.toString(), specificSGCase.getValue());
		model.set(ModelTag.relatedSGComponents.toString(), ids);

		return model;
	}

	/**
	 * here i update the value for the exsisting algorithm form
	 */
	public void setArchitectureModel(GenericModel model) {
		this.model = model;
		name.setValue(model.getName());
		description.setValue(model.getDescription());
		specificSGCase.setValue((String) model.get(ModelTag.specificSGCase.toString()));
		if (this.components != null) {
			for (GenericModel c : this.components) {
				for (Integer i : (List<Integer>) model.get(ModelTag.relatedSGComponents.toString())) {
					if (c.getId().intValue() == i.intValue()) {
						relatedComponents.getFromList().getStore().remove(c);
						relatedComponents.getToList().getStore().add(c);
					}
				}
			}
		}
	}

	/**
	 * @return the okClose
	 */
	@Override
	public boolean isOkClose() {
		return okClose;
	}
}
