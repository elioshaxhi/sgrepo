/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client.fieldsetsContext;

import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.Context;
import com.customlib.seriousgame.client.models.ContextAndAnalysis;
import com.customlib.seriousgame.client.models.GenericModel;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author nomecognome
 */
public class ContextFieldSetParent extends FieldSet {

	private MainEntryPointInterface mainEntryPointInterface = null;
	private ContextFieldSet fieldSetContext;
	private FormData formData;
	private boolean flag = true;

	public ContextFieldSetParent(final MainEntryPointInterface mainEntryPointInterface, String nameComponent) {
		this.mainEntryPointInterface = mainEntryPointInterface;
		formData = new FormData("-15");

		setHeading(nameComponent);
		setCollapsible(true);

		FormLayout layout = new FormLayout(LabelAlign.LEFT);
		layout.setLabelWidth(Utilities.formLabelWidth);
		setLayout(layout);




	}

	public void setData(ContextAndAnalysis c) {
		List<GenericModel> componentQueue = c.getContexts();
		ListIterator<GenericModel> iter = componentQueue.listIterator();
		if (!componentQueue.isEmpty()) {
			setExpanded(true);
		}
		if (!componentQueue.isEmpty()) {
			while (iter.hasNext()) {
				fieldSetContext = new ContextFieldSet(this.mainEntryPointInterface);
				GenericModel gm = (GenericModel) iter.next();
				Context context = gm.get("Context");


				fieldSetContext.setData(context.getName(), context);


				this.add(fieldSetContext, new FormData("-2"));

			}
		} else {
//			MyExtendedClasses.SeriouGameLabelField noItems = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.arch_noItems());
//			setFieldValue(noItems, "");
//			this.add(noItems, new FormData("-2"));
			setEnabled(false);
			setExpanded(false);
		}


	}
}
