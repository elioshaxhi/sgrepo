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

/**
 *
 * @author ElionHaxhi
 */
public class GenericComponetClass extends FieldSet {

	private MainEntryPointInterface mainEntryPointInterface;
	private FormData formData;
	private MyExtendedClasses.SeriouGameLabelField componentType;
	private MyExtendedClasses.SeriouGameLabelField componentDescription;
	private MyExtendedClasses.SeriouGameLabelField componentSpecificSG;
	private MyExtendedClasses.SeriouGameLabelField relatedComponent;

	public GenericComponetClass(final MainEntryPointInterface mainEntryPointInterface, final GenericModel gm, List<GenericModel> components) {
		this.mainEntryPointInterface = mainEntryPointInterface;
		formData = new FormData("-15");

		setLayout(new FormLayout());
		setBorders(false);

		componentType = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.name());

		String componentValueType = gm.getName();
		this.setFieldValue(componentType, componentValueType);
		this.add(componentType, new FormData("-15"));

		componentDescription = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.description());
		String componentValueDescription = gm.getDescription();
		setFieldValue(componentDescription, componentValueDescription);
		this.add(componentDescription, new FormData("-15"));

		componentSpecificSG = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.arch_specificSGCase());
		String componentValueSpecificSG = (String) gm.get(ModelTag.specificSGCase.toString());
		setFieldValue(componentSpecificSG, componentValueSpecificSG);
		this.add(componentSpecificSG, new FormData("-15"));

		relatedComponent = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.arch_relatedSGComponents());
		String related = null;
		String tmp = ":";
		boolean flag = false;
          //qui questo blocco qui serve per i related component 
		if (components != null) {
			for (GenericModel c : components) {
				for (Integer i : (List<Integer>) gm.get(ModelTag.relatedSGComponents.toString())) {
					if (c.getId().intValue() == i.intValue()) {

						if (related != null) {
							related += ", ";
						}
						if (!flag) {
							flag = true;
							related = "";
						}
						related += c.getName();

					}
				}

			}
		}


		setFieldValue(relatedComponent, related);

		this.add(relatedComponent, new FormData("-15"));

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
