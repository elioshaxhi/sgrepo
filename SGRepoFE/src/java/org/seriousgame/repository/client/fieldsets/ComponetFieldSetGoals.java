/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client.fieldsets;

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
 * @author ElionHaxhi
 */
public class ComponetFieldSetGoals extends FieldSet {

	private MainEntryPointInterface mainEntryPointInterface;
	private FormData formData;
	private List<GenericModelWithRelation> storeSkills = null;
	private List<GenericModel> storeLearningGoalsType = null;
	private List<GenericModelWithRelation> storeLearningGoalsSubType = null;

	public ComponetFieldSetGoals(final MainEntryPointInterface mainEntryPointInterface) {
		this.mainEntryPointInterface = mainEntryPointInterface;
		formData = new FormData("-15");


		setLayout(new FormLayout());
		setBorders(false);
	}

	/**
	 * prima dei set data vado a eseguire serGenericwithRelationship, otteno
	 * dua mappe e una lista, la lista contiene tutti i tipi possibi di
	 * goals, prendo come argomento il model corrente ottengo il
	 * corrispindente model ma con le relazioni(lo ottengo dala lista
	 * learninggoaltypes) usando il model con le relazioni tramita il
	 * metotdo getRelation ottengo la lista dei subType, da questa lista
	 * utilizando l'id del modello base verifico se lo sybtype contine
	 * subsubtye. Se si allora stamdpo i dati subsubtype loop
	 *
	 */
	public void setData(GenericModel model) {

		MyExtendedClasses.SeriouGameLabelField type;
		MyExtendedClasses.SeriouGameLabelField subType;
		MyExtendedClasses.SeriouGameLabelField subSubType;
		MyExtendedClasses.SeriouGameLabelField description;

		GenericModel gmType = null;

		for (GenericModel currentModel : storeLearningGoalsType) {
			if (currentModel.getId().intValue() == model.getId().intValue()) {
				gmType = currentModel;
				break;
			}
		}


		type = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.arch_type());
		String valueT = (String) gmType.getName();
		setFieldValue(type, valueT);

		this.add(type, new FormData("-15"));

		GenericModelWithRelation gmSubType = null;

		int subtypeId = ((Integer) model.get(ModelTag.relationID.toString())).intValue();

		List<GenericModelWithRelation> listasubTypes = mainEntryPointInterface.getDatabase().getRelationSubList(this.storeLearningGoalsSubType, gmType.getId());
		for (GenericModelWithRelation currentModel : listasubTypes) {
			if (currentModel.getId().intValue() == subtypeId) {
				gmSubType = currentModel;
				break;
			}
		}
		subType = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.subtype());
		String valuesT = null;
		if (gmSubType != null) {
			valuesT = gmSubType.getName();
			setFieldValue(subType, valuesT);
		} else {
			setFieldValue(subType, valuesT);
		}


		this.add(subType, new FormData("-15"));

		GenericModelWithRelation gmsubSub = null;

		List<GenericModelWithRelation> listasubSubTypes = mainEntryPointInterface.getDatabase().getRelationSubList(this.storeSkills, gmSubType.getId());


		String valuesST = null;
		if (model.get(ModelTag.subSubTypeID.toString()) != null) {
			int subSubTypeId = ((Integer) model.get(ModelTag.subSubTypeID.toString())).intValue();
			for (GenericModelWithRelation currentModel : listasubSubTypes) {
				if (currentModel.getId().intValue() == subSubTypeId) {
                         //gmsubSub = currentModel;
			          valuesST = currentModel.getName();
					break;
				}
			}

		}

		subSubType = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.subSubtype());
		setFieldValue(subSubType, valuesST);

		this.add(subSubType, new FormData("-15"));

		description = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.notes());

		String valueD = (String) model.getDescription();
		setFieldValue(description, valueD);

		this.add(description, new FormData("-15"));
	}

	public void setGenericWithRelation() {
		this.storeLearningGoalsSubType = mainEntryPointInterface.getDatabase().getContextLearningGoalsList();
		this.storeSkills = mainEntryPointInterface.getDatabase().getContextSoftSkillList();
		this.storeLearningGoalsType = mainEntryPointInterface.getDatabase().getContextLearningGoalsTypesList();
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
