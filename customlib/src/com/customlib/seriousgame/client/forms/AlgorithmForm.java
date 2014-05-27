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
public class AlgorithmForm extends Window implements ClosureFormInterface {

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

	public AlgorithmForm(final MainEntryPointInterface entrypoint, String header, List<GenericModel> components) {
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
		ListStore<GenericModel> storeProva = new ListStore<GenericModel>();
		storeProva.add(entrypoint.getDatabase().getAlgorithmTypesList());

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

		type = new ComboBox<GenericModel>();
		type.setForceSelection(true);
		type.setAllowBlank(false);
		type.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
		type.setFieldLabel(Constants.Strings.type());
		type.setDisplayField(ModelTag.name.toString());
		type.setTriggerAction(ComboBox.TriggerAction.ALL);
		type.setStore(storeProva);
		form.add(type, formData);

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
	public GenericModel getAlgorithm() {
//        int id=-1;
//        if(component != null)
//            id=component.getId();
		int id = -1;

		if (algorithm != null) {
			id = algorithm.getId();
		}
		List<Integer> ids = new ArrayList<Integer>();
		if (relatedComponents != null) {
			ids = Utilities.getIDList(relatedComponents.getToList().getStore().getModels());
		}

		algorithm = new GenericModel(id, name.getValue(), description.getValue());

		algorithm.set(ModelTag.specificSGCase.toString(), specificSGCase.getValue());
		algorithm.set(ModelTag.algorithmTypesID.toString(), type.getValue().getId());
		algorithm.set(ModelTag.algorithmType.toString(), type.getValue().getName());
		algorithm.set(ModelTag.relatedSGComponents.toString(), ids);
		return algorithm;
	}

	/**
	 * the data came from the respective query call
	 */
	public void setAlgorithm(GenericModel algorithm) {
		this.algorithm = algorithm;
		Integer algorithmTypesID = (Integer) algorithm.get(ModelTag.algorithmTypesID.toString());
		if ((algorithmTypesID == null || algorithmTypesID.intValue() == 0)
			&& entrypoint.getDatabase().getAlgorithmTypesList().size() > 0) {
			type.setValue(entrypoint.getDatabase().getAlgorithmTypesList().get(0));
		} else {
			for (GenericModel model : entrypoint.getDatabase().getAlgorithmTypesList()) {
				if (model.getId().intValue() == algorithmTypesID.intValue()) {
					type.setValue(model);
				}
			}
		}
		name.setValue(algorithm.getName());
		// type.setDisplayField(ModelTag.name.toString()); 
		description.setValue(algorithm.getDescription());
		specificSGCase.setValue((String) algorithm.get(ModelTag.specificSGCase.toString()));
		if (this.components != null) {
			for (GenericModel c : this.components) {
				for (Integer i : (List<Integer>) algorithm.get(ModelTag.relatedSGComponents.toString())) {
					if (c.getId().intValue() == i.intValue()) {
						relatedComponents.getFromList().getStore().remove(c);
						relatedComponents.getToList().getStore().add(c);
					}
				}
			}
		}

	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * here you create the model and succesively prepare it for the inection
	 * at the database ,this implementation is for algorithm form without
	 * type element in relation.
	 */
	/**
	 * @return the okClose
	 */
	@Override
	public boolean isOkClose() {
		return okClose;
	}
}
