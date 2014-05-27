/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client.fieldsets;

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
public class ComponentFieldSetParent extends FieldSet {

	private MainEntryPointInterface mainEntryPointInterface = null;
	private ComponentFieldSet fieldSetComponent;
	private FormData formData;

	public ComponentFieldSetParent(final MainEntryPointInterface mainEntryPointInterface, String nameComponent) {
		this.mainEntryPointInterface = mainEntryPointInterface;
		formData = new FormData("-15");

		setHeading(nameComponent);
		setCollapsible(true);
		setExpanded(false);
		setLayout(new FormLayout());




	}

	public void setData(List<GenericModel> componentQueue) {

		ListIterator<GenericModel> iter = componentQueue.listIterator();
		if (!componentQueue.isEmpty()) {
			while (iter.hasNext()) {
				GenericModel gm = (GenericModel) iter.next();

				fieldSetComponent = new ComponentFieldSet(mainEntryPointInterface, gm.getName(), gm);


				fieldSetComponent.setDataPedagogical((List<GenericModel>) gm.get(ModelTag.pedagogicalParadigm.toString()));
//			fieldSetComponent.setDataGoals((List<GenericModel>) gm.get(ModelTag.learningGoals.toString()));
				fieldSetComponent.setDataGoals((List<GenericModel>) gm.get(ModelTag.learningGoals.toString()));


				this.add(fieldSetComponent, new FormData("-2"));

			}
		} else {
//			MyExtendedClasses.SeriouGameLabelField noItems = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.arch_noItems());
//			setFieldValue(noItems, "");
//			this.add(noItems, new FormData("-2"));

			setEnabled(false);
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
