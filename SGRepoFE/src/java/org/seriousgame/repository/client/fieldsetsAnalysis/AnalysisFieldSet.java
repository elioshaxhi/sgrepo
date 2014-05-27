/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client.fieldsetsAnalysis;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.Analysis;
import com.customlib.seriousgame.client.models.GenericModel;
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
public class AnalysisFieldSet extends FieldSet {

	private MainEntryPointInterface mainEntryPointInterface;
	private FormData formData;
	private boolean flag = true;
	private MyExtendedClasses.SeriouGameLabelField modelFieldsetName;
	private MyExtendedClasses.SeriouGameLabelField modelFieldsetDescription;
	private FieldSet filedSet;

	public AnalysisFieldSet(final MainEntryPointInterface mainEntryPointInterface, String nome) {
		this.mainEntryPointInterface = mainEntryPointInterface;
		formData = new FormData("-30");
		setHeading(nome);
		setLayout(new FormLayout());
		setCollapsible(true);
	}

	public void setData(List<GenericModel> iContext, Analysis analysis) {




		if (iContext.isEmpty()) {
			flag = false;
		}
		ListIterator iter = iContext.listIterator();
		while (iter.hasNext()) {
			GenericModel gm = (GenericModel) iter.next();
			if (analysis != null) {
				List<Integer> ids = Utilities.getListFromString(analysis.getReferenceContext());
				if (ids.isEmpty()) {
					flag = false;
				}
				for (Integer i : ids) {
					if (gm.getId().intValue() == i.intValue()) {
						filedSet = new FieldSet();
						filedSet.setLayout(new FormLayout());
						filedSet.setHeading(gm.getName());
						filedSet.setCollapsible(true);

						modelFieldsetDescription = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.description());
						String valueDescription = gm.getDescription();
						setFieldValue(modelFieldsetDescription, valueDescription);
						filedSet.add(modelFieldsetDescription, new FormData("-3"));
						this.add(filedSet);

					}

				}
			} else {
				flag = false;
			}

		}
		if (!flag) {
			setEnabled(false);
			setExpanded(false);
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
