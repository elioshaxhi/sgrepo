/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client.form;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.Architecture;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import org.seriousgame.repository.client.fieldsets.GenericFieldSetParent;
import org.seriousgame.repository.client.fieldsets.ComponentFieldSetParent;

/**
 *
 * @author nertil
 */
public class ArchitectureFormFe extends FormPanel {

	private MainEntryPointInterface entrypoint = null;
	private ComponentFieldSetParent componentParent;
	private GenericFieldSetParent algorithmParent;
	private GenericFieldSetParent gameEngine;
	private GenericFieldSetParent interoperabilityAndStandarts;
	private GenericFieldSetParent psichologicalAspects;
	private GenericFieldSetParent neuroscientificAspects;
	private FormData formData;

	public ArchitectureFormFe(MainEntryPointInterface entrypoint) {
		this.entrypoint = entrypoint;

		formData = new FormData("-15");
          componentParent = new ComponentFieldSetParent(entrypoint, Constants.Strings.arch_components());
          algorithmParent = new GenericFieldSetParent(entrypoint, Constants.Strings.arch_algorithms());
          gameEngine = new GenericFieldSetParent(entrypoint, Constants.Strings.arch_gameEngine());
          interoperabilityAndStandarts = new GenericFieldSetParent(entrypoint, Constants.Strings.arch_InteroperabilityAndStandars());
          psichologicalAspects = new GenericFieldSetParent(entrypoint, Constants.Strings.arch_psichologicalAspects());
          neuroscientificAspects = new GenericFieldSetParent(entrypoint, Constants.Strings.arch_neuroscientificAspects());

		setStyleName("sg-form-infogroup");
		getHeader().setStyleName("sg-form-infogroup-header");
		setBodyStyleName("sg-form-infogroup-body");
		setBorders(true);
		setLabelWidth(Utilities.formLabelWidth);
		setHeaderVisible(false);

		setButtonAlign(Style.HorizontalAlignment.CENTER);
		setLayout(new FormLayout());
		setPadding(5);

		setBodyBorder(true);
		setHeaderVisible(false);
		setScrollMode(Style.Scroll.AUTO);

	}

//	@Override
	public void setRecord(SeriousGame record) {
		Architecture architecture = record.get(SeriousGame.TagArchitecture);
		if (architecture == null) {
			return;
		}
          componentParent.setData(architecture.getComponents());
          algorithmParent.setData(architecture.getAlgorithms(), architecture.getComponents());
          gameEngine.setData(architecture.getGameEngines(), architecture.getComponents());
          interoperabilityAndStandarts.setData(architecture.getInteroperabilityAndStandards(), architecture.getComponents());
          psichologicalAspects.setData(architecture.getPsichologicalAspects(), architecture.getComponents());
          neuroscientificAspects.setData(architecture.getNeuroscientificAspects(), architecture.getComponents());

          add(componentParent, new FormData("-2"));
          add(algorithmParent, new FormData("-2"));
          add(gameEngine, new FormData("-2"));
          add(interoperabilityAndStandarts, new FormData("-2"));
          add(psichologicalAspects, new FormData("-2"));
          add(neuroscientificAspects, new FormData("-2"));
	}
}
