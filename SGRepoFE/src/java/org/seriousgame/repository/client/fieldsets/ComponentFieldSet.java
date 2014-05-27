/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client.fieldsets;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.ModelTag;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author nomecognome
 */
public class ComponentFieldSet extends FieldSet {

	private MainEntryPointInterface mainEntryPointInterface;
	private FormData formData;
	private MyExtendedClasses.SeriouGameLabelField componentType;
	private MyExtendedClasses.SeriouGameLabelField componentDescription;
	private MyExtendedClasses.SeriouGameLabelField componentSpecificSG;
	private ComponetFieldSetGoals goalsFieldSet;
	private ComponetFieldSetPedagogical pedagogicalFieldSet;
	private FieldSet relationWithLearning;
	private FieldSet relationWithPedagigical;

	public ComponentFieldSet(final MainEntryPointInterface mainEntryPointInterface, String nomeComponent, final GenericModel gm) {
		this.mainEntryPointInterface = mainEntryPointInterface;
		formData = new FormData("-15");

		setLayout(new FormLayout());
		setHeading(nomeComponent);
		setCollapsible(true);
		setExpanded(false);

		componentType = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.arch_type());

		String componentValueType = (String) gm.get(ModelTag.componentType.toString());
		setFieldValue(componentType, componentValueType);
		this.add(componentType, new FormData("-15"));

		componentDescription = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.description());
		String componentValueDescription = (String) gm.get(ModelTag.description.toString());
		setFieldValue(componentDescription, componentValueDescription);
		this.add(componentDescription, new FormData("-15"));

		componentSpecificSG = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.arch_specificSGCase());
		String componentValueSpecificSG = (String) gm.get(ModelTag.specificSGCase.toString());
		setFieldValue(componentSpecificSG, componentValueSpecificSG);
		this.add(componentSpecificSG, new FormData("-15"));

	}

	/**
	 * la lista source contiene i componenti pedagogical lo itero e per
	 * ciascun iterazione creo un nuovo component fieldset
	 */
	public void setDataPedagogical(List<GenericModel> source) {

		ListIterator iter = source.listIterator();
		relationWithPedagigical = new FieldSet();
		relationWithPedagigical.setLayout(new FormLayout());
		relationWithPedagigical.setHeading(Constants.Strings.arch_component_relationsWithPedagogicalApproaches());
		relationWithPedagigical.setCollapsible(true);
		relationWithPedagigical.setExpanded(true);
		boolean flag = true;

		if (source.isEmpty()) {
			flag = false;
		}
		while (iter.hasNext()) {
			GenericModel gm = (GenericModel) iter.next();


			pedagogicalFieldSet = new ComponetFieldSetPedagogical(this.mainEntryPointInterface);



			pedagogicalFieldSet.setData(gm);
			relationWithPedagigical.add(pedagogicalFieldSet, new FormData("-2"));

		}
		if (flag) {
			this.add(relationWithPedagigical, new FormData("-2"));
		} else {
//			relationWithPedagigical.setVisible(false);
			relationWithPedagigical.setExpanded(false);
			relationWithPedagigical.setEnabled(false);
			this.add(relationWithPedagigical, new FormData("-2"));
		}


	}

	/**
	 * ottengo la litsa learning goals lo ittero e per ciascun componente mi
	 * creo una nuova istanza
	 */
	public void setDataGoals(List<GenericModel> source) {

		ListIterator iter = source.listIterator();
		relationWithLearning = new FieldSet();
		relationWithLearning.setLayout(new FormLayout());
		relationWithLearning.setHeading(Constants.Strings.arch_component_relationsWithLearningGoals());
		relationWithLearning.setCollapsible(true);
		relationWithLearning.setExpanded(true);
		boolean flag = true;
		if (source.isEmpty()) {
			flag = false;
		}
		while (iter.hasNext()) {
			GenericModel gm = (GenericModel) iter.next();


			goalsFieldSet = new ComponetFieldSetGoals(this.mainEntryPointInterface);
			goalsFieldSet.setGenericWithRelation();

			goalsFieldSet.setData(gm);
			relationWithLearning.add(goalsFieldSet, new FormData("-2"));

		}
		if (flag) {
			this.add(relationWithLearning, new FormData("-2"));
		} else {
//			relationWithLearning.setVisible(false);
			relationWithLearning.setExpanded(false);
			relationWithLearning.setEnabled(false);
			this.add(relationWithLearning, new FormData("-2"));
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
