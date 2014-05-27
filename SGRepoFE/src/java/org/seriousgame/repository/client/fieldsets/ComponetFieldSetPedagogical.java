/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client.fieldsets;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.GenericModel;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

/**
 *
 * @author nomecognome
 */
public class ComponetFieldSetPedagogical extends FieldSet {

	private MainEntryPointInterface mainEntryPointInterface;
	private FormData formData;

	public ComponetFieldSetPedagogical(final MainEntryPointInterface mainEntryPointInterface) {
		this.mainEntryPointInterface = mainEntryPointInterface;
		formData = new FormData("-15");


		setLayout(new FormLayout());
		setBorders(false);
	}

	/**
	 * ottengo il genericmodel (pedagogical) stampo tipi e descrizione
	 */
	public void setData(GenericModel chieldren) {


		MyExtendedClasses.SeriouGameLabelField type;
		MyExtendedClasses.SeriouGameLabelField description;

		type = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.arch_type());
		String valueT = (String) chieldren.getName();
		setFieldValue(type, valueT);

		this.add(type, new FormData("-15"));


		description = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.description());
		String valueD = (String) chieldren.getDescription();
		setFieldValue(description, valueD);


		this.add(description, new FormData("-15"));



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
