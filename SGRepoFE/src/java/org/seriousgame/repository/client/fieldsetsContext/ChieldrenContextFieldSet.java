/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client.fieldsetsContext;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.GenericModelWithRelation;
import com.customlib.seriousgame.client.models.ModelTag;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import java.util.List;

/**
 *
 * @author nomecognome
 */
public class ChieldrenContextFieldSet extends FieldSet {

	private MainEntryPointInterface mainEntryPointInterface;
	private FormData formData;
	private MyExtendedClasses.SeriouGameLabelField note;
	private MyExtendedClasses.SeriouGameLabelField type;
	private MyExtendedClasses.SeriouGameLabelField subType;
	private MyExtendedClasses.SeriouGameLabelField subSubType;
	private List<GenericModel> coda = null;
	private List<GenericModelWithRelation> subCoda = null;
	private List<GenericModelWithRelation> subSubCoda = null;
	private List<GenericModelWithRelation> theSubList = null;
	private List<GenericModelWithRelation> theSubSubList = null;

	public ChieldrenContextFieldSet(final MainEntryPointInterface mainEntryPointInterface, List<GenericModel> coda, List<GenericModelWithRelation> subCoda, List<GenericModelWithRelation> subSubCoda) {

		this.mainEntryPointInterface = mainEntryPointInterface;
		this.formData = new FormData("-30");
		this.coda = coda;
		this.subCoda = subCoda;
		this.subSubCoda = subSubCoda;
		setLayout(new FormLayout());
		setBorders(false);

	}

//		private void typeSelectionChange() {
//		if (subType == null || !hasSubtype || subTypeList == null) {
//			return;
//		}
//		if (subSubType != null && hasSubSubtype) {
//			subSubType.setVisible(type.getValue().getName().equals("Soft skills"));
//		}
//		List<GenericModelWithRelation> store = entrypoint.getDatabase().getRelationSubList(this.subTypeList, type.getValue().getId());
//		subType.getStore().removeAll();
//		subType.getStore().add(store);
//		if (store.size() > 0 && subType.getValue() == null) {
//			subType.setValue(store.get(0));
//		}
//	}
//	private void subTypeSelectionChange() {
//		if (subSubType == null || !hasSubSubtype || subSubTypeList == null) {
//			return;
//		}
//		if (subSubType.isVisible()) {
//			List<GenericModelWithRelation> store = entrypoint.getDatabase().getRelationSubList(this.subSubTypeList, subType.getValue().getId());
//			subSubType.getStore().removeAll();
//			subSubType.getStore().add(store);
//			if (store.size() > 0 && subSubType.getValue() == null) {
//				subSubType.setValue(store.get(0));
//			}
//		}
//	}
	public void setTopicData(GenericModel model) {


		note = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.notes());
		type = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.type());
		subType = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.subtype());
		subSubType = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.subSubtype());

		String valNote = null;
		String valType = null;
		String valSubType = null;
		String valSubSubType = null;
		GenericModel tmp = null;

		valNote = model.getDescription();
		for (GenericModel currentModel : coda) {
			if (currentModel.getId().intValue() == model.getId().intValue()) {
				valType = currentModel.getName();
				break;
			}
		}
		if (model.get(ModelTag.relationID.toString()) != null) {
			theSubList = this.mainEntryPointInterface.getDatabase().getRelationSubList(this.subCoda, model.getId().intValue());
			int subtypeId = ((Integer) model.get(ModelTag.relationID.toString())).intValue();
			for (GenericModelWithRelation currentModel : theSubList) {
				if (currentModel.getId().intValue() == subtypeId) {
					valSubType = currentModel.getName();
					tmp = currentModel;
					break;
				}
			}
		}
		if (valType.equals("Soft skills")) {
			int subSubTypeId = ((Integer) model.get(ModelTag.subSubTypeID.toString())).intValue();

			theSubSubList = this.mainEntryPointInterface.getDatabase().getRelationSubList(this.subSubCoda, tmp.getId().intValue());
			int i = 0;
			String tmps;
			for (GenericModelWithRelation currentModel : theSubSubList) {
				if (currentModel.getId().intValue() == subSubTypeId) {
					valSubSubType = currentModel.getName();
					tmps = valSubSubType;
					break;
				}
				i++;
			}
		}
		setFieldValue(type, valType);
		setFieldValue(subType, valSubType);
		setFieldValue(subSubType, valSubSubType);
		setFieldValue(note, valNote);

		this.add(type, new FormData("-3"));
		this.add(subType, new FormData("-3"));
		this.add(subSubType, new FormData("-3"));
		this.add(note, new FormData("-3"));

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
