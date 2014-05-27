/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client.fieldsets;

import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.GenericModel;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import java.util.List;
import java.util.ListIterator;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.Constants;

/**
 *
 * @author nomecognome
 */
public class GenericFieldSetParent extends FieldSet {

	private MainEntryPointInterface mainEntryPointInterface = null;
	private GenericComponetClass fieldSetGeneric;
	private FormData formData;

	public GenericFieldSetParent(final MainEntryPointInterface mainEntryPointInterface, String nameComponent) {
		this.mainEntryPointInterface = mainEntryPointInterface;
		formData = new FormData("-15");
		setHeading(nameComponent);
		setCollapsible(true);
		setExpanded(false);
		setLayout(new FormLayout());




	}

	public void setData(List<GenericModel> componentGeneric, List<GenericModel> components) {

		ListIterator<GenericModel> iter = componentGeneric.listIterator();
		if (!componentGeneric.isEmpty()) {
			while (iter.hasNext()) {
				GenericModel gm = (GenericModel) iter.next();

				fieldSetGeneric = new GenericComponetClass(this.mainEntryPointInterface, gm, components);





				this.add(fieldSetGeneric, new FormData("-2"));

			}
		} else {
               //MyExtendedClasses.SeriouGameLabelField noItems = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.arch_noItems());
               //setFieldValue(noItems, "");
		//setExpanded(true);
			setEnabled(false);
               //this.add(noItems, new FormData("-2"));

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
