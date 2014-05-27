/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.forms;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.interfaces.ClosureFormInterface;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.GenericModelWithRelation;
import com.customlib.seriousgame.client.models.ModelTag;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import java.util.List;

/**
 *
 * @author nertil
 */
public class GenericModelSelectionForm extends Window implements ClosureFormInterface {

	private MainEntryPointInterface entrypoint = null;
	private FormPanel form;
	private ComboBox<GenericModel> type;
	private ComboBox<GenericModelWithRelation> subType;
	private ComboBox<GenericModelWithRelation> subSubType;
	private MyExtendedClasses.NotesTextArea notes;
	private boolean okClose = false;
	private boolean hasSubtype = false;
	private boolean hasSubSubtype = false;
	private List<GenericModelWithRelation> subTypeList;
	private List<GenericModelWithRelation> subSubTypeList;

	public GenericModelSelectionForm(final MainEntryPointInterface entrypoint, List<GenericModel> source) {
		this.entrypoint = entrypoint;
		setWidth(Utilities.smallformWidth);
		setMinWidth(Utilities.formMinWidth);
		setLayout(new FitLayout());
		setStyleName("form-font");
		setModal(true);
		setButtonAlign(Style.HorizontalAlignment.CENTER);
		setHeading(Constants.Strings.genericSelection());

		Button ok_button = new Button(Constants.Strings.b_ok(), new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (!form.isValid()) {
					return;
				}
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

		FormData formData = new FormData("-20");

		ListStore<GenericModel> store = new ListStore<GenericModel>();
		store.add(source);

		type = new ComboBox<GenericModel>();
		type.setForceSelection(true);
		type.setAllowBlank(false);
		type.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
		type.setFieldLabel(Constants.Strings.type());
		type.setDisplayField(ModelTag.name.toString());
		type.setTriggerAction(ComboBox.TriggerAction.ALL);
		type.setStore(store);
		type.addSelectionChangedListener(new SelectionChangedListener<GenericModel>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<GenericModel> se) {
				typeSelectionChange();
				Utilities.otherSelectedOnComboBox(entrypoint, se);
			}
		});
		if (source.size() > 0) {
			type.setValue(source.get(0));
		}
		form.add(type, formData);

		subType = new ComboBox<GenericModelWithRelation>();
		subType.setForceSelection(true);
		subType.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
		subType.setFieldLabel(Constants.Strings.subtype());
		subType.setDisplayField(ModelTag.name.toString());
		subType.setTriggerAction(ComboBox.TriggerAction.ALL);
		subType.setStore(new ListStore<GenericModelWithRelation>());
		subType.setVisible(false);
		subType.addSelectionChangedListener(new SelectionChangedListener<GenericModelWithRelation>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<GenericModelWithRelation> se) {
				subTypeSelectionChange();
				Utilities.otherSelectedOnComboBoxII(entrypoint, se);
			}
		});
		form.add(subType, formData);

		subSubType = new ComboBox<GenericModelWithRelation>();
		subSubType.setForceSelection(true);
		subSubType.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
		subSubType.setFieldLabel(Constants.Strings.subSubtype());
		subSubType.setDisplayField(ModelTag.name.toString());
		subSubType.setTriggerAction(ComboBox.TriggerAction.ALL);
		subSubType.setStore(new ListStore<GenericModelWithRelation>());
		subSubType.setVisible(false);
		subSubType.addSelectionChangedListener(new SelectionChangedListener<GenericModelWithRelation>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<GenericModelWithRelation> se) {
				Utilities.otherSelectedOnComboBoxII(entrypoint, se);
			}
		});
		form.add(subSubType, formData);

		notes = new MyExtendedClasses.NotesTextArea();
		form.add(notes, formData);

		add(form);

	}

	/**
	 * @return the okClose
	 */
	@Override
	public boolean isOkClose() {
		return okClose;
	}

	public GenericModel getGenericModel() {
		GenericModel current = type.getValue();
		if (current == null) {
			return null;
		}
		GenericModel retval = new GenericModel(current.getId(), current.getName(), notes.getValue());
		if (hasSubtype) {
			retval.set(ModelTag.subType.toString(), subType.getValue().getName());
			retval.set(ModelTag.relationID.toString(), subType.getValue().getId());
		}
		if (hasSubSubtype && subSubType != null && subSubType.getValue() != null) {
			retval.set(ModelTag.subSubTypeID.toString(), subSubType.getValue().getId());
		}
		return retval;
	}

	public void setModel(GenericModel model) {
		notes.setValue(model.getDescription());
		for (GenericModel currentModel : type.getStore().getModels()) {
			if (currentModel.getId().intValue() == model.getId().intValue()) {
				type.setValue(currentModel);
				break;
			}
		}
		if (hasSubtype) {
			int subtypeId = ((Integer) model.get(ModelTag.relationID.toString())).intValue();
			for (GenericModelWithRelation currentModel : subType.getStore().getModels()) {
				if (currentModel.getId().intValue() == subtypeId) {
					subType.setValue(currentModel);
					break;
				}
			}
		}
		if (hasSubSubtype && type.getValue().getName().equals("Soft skills")) {
			int subSubTypeId = ((Integer) model.get(ModelTag.subSubTypeID.toString())).intValue();
			for (GenericModelWithRelation currentModel : subSubType.getStore().getModels()) {
				if (currentModel.getId().intValue() == subSubTypeId) {
					subSubType.setValue(currentModel);
					break;
				}
			}
		}
	}

	public void setSubType(List<GenericModelWithRelation> subtypeList) {
		this.subTypeList = subtypeList;
		hasSubtype = true;
		subType.setVisible(true);
		typeSelectionChange();
	}

	public void setSubSubType(List<GenericModelWithRelation> subSubtypeList) {
		this.subSubTypeList = subSubtypeList;
		hasSubSubtype = true;
		subSubType.setVisible(true);
		subTypeSelectionChange();
	}

	private void typeSelectionChange() {
		if (subType == null || !hasSubtype || subTypeList == null) {
			return;
		}
		if (subSubType != null && hasSubSubtype) {
			subSubType.setVisible(type.getValue().getName().equals("Soft skills"));
		}
		List<GenericModelWithRelation> store = entrypoint.getDatabase().getRelationSubList(this.subTypeList, type.getValue().getId());
		subType.getStore().removeAll();
		subType.getStore().add(store);
		if (store.size() > 0 && subType.getValue() == null) {
			subType.setValue(store.get(0));
		}

	}

	private void subTypeSelectionChange() {
		if (subSubType == null || !hasSubSubtype || subSubTypeList == null) {
			return;
		}
		if (subSubType.isVisible()) {
			List<GenericModelWithRelation> store = entrypoint.getDatabase().getRelationSubList(this.subSubTypeList, subType.getValue().getId());
			subSubType.getStore().removeAll();
			subSubType.getStore().add(store);
			if (store.size() > 0 && subSubType.getValue() == null) {
				subSubType.setValue(store.get(0));
			}
		}
	}
}
