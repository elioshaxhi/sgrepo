/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.forms;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.grids.AlgorithmGrid;
import com.customlib.seriousgame.client.grids.ComponentGrid;
import com.customlib.seriousgame.client.grids.GenericArchitectureGrid;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.interfaces.SeriousGameDataInteractionForm;
import com.customlib.seriousgame.client.models.Architecture;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.AnchorData;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import java.util.ArrayList;

/**
 *
 * @author nertil
 */
public class ArchitectureForm extends FormPanel implements SeriousGameDataInteractionForm {

	private MainEntryPointInterface entrypoint = null;
	private ComponentGrid components;
	private AlgorithmGrid algorithm;
	private GenericArchitectureGrid gameEngine;
	private GenericArchitectureGrid interoperabilityAndStandarts;
	private GenericArchitectureGrid psichologicalAspects;
	private GenericArchitectureGrid neuroscientificAspects;

	private boolean changes = false;

	public ArchitectureForm(MainEntryPointInterface entrypoint) {
		this.entrypoint = entrypoint;

		components = new ComponentGrid(entrypoint, Constants.Strings.arch_components(), new ArrayList<GenericModel>());
		algorithm = new AlgorithmGrid(entrypoint, Constants.Strings.arch_algorithm(), new ArrayList<GenericModel>());
		gameEngine = new GenericArchitectureGrid(entrypoint, Constants.Strings.arch_gameEngine(), new ArrayList<GenericModel>());
		interoperabilityAndStandarts = new GenericArchitectureGrid(entrypoint, Constants.Strings.arch_InteroperabilityAndStandars(), new ArrayList<GenericModel>());
		psichologicalAspects = new GenericArchitectureGrid(entrypoint, Constants.Strings.arch_psichologicalAspects(), new ArrayList<GenericModel>());
		neuroscientificAspects = new GenericArchitectureGrid(entrypoint, Constants.Strings.arch_neuroscientificAspects(), new ArrayList<GenericModel>());

		setBodyBorder(false);
		setHeaderVisible(false);
		setButtonAlign(HorizontalAlignment.CENTER);
		setLayout(new FormLayout());
		setScrollMode(Style.Scroll.AUTO);
		setPadding(5);

		FormData formData = new FormData("-15");
		AnchorData anchoreData = new AnchorData("100%", new Margins(0, 15, 5, 0));

		add(components, anchoreData);
		add(algorithm, anchoreData);
		add(gameEngine, anchoreData);
		add(interoperabilityAndStandarts, anchoreData);
		add(psichologicalAspects, anchoreData);
		add(neuroscientificAspects, anchoreData);
	}

	public void componentListChange() {
		algorithm.setComponents(components.getData());
		interoperabilityAndStandarts.setComponents(components.getData());
		psichologicalAspects.setComponents(components.getData());
		neuroscientificAspects.setComponents(components.getData());
	}

	@Override
	public void updateRecord(SeriousGame record) {
		Architecture architecture = new Architecture(components.getData(),
			algorithm.getData(),
			gameEngine.getData(),
			interoperabilityAndStandarts.getData(),
			psichologicalAspects.getData(),
			neuroscientificAspects.getData(),
			hasChanges());
		record.set(SeriousGame.TagArchitecture, architecture);
	}

	@Override
	public void setRecord(SeriousGame record) {
		Architecture architecture = record.get(SeriousGame.TagArchitecture);
		if (architecture == null) {
			return;
		}
		components.setData(architecture.getComponents());
		algorithm.setData(architecture.getAlgorithms());
		gameEngine.setData(architecture.getGameEngines());
		interoperabilityAndStandarts.setData(architecture.getInteroperabilityAndStandards());
		psichologicalAspects.setData(architecture.getPsichologicalAspects());
		neuroscientificAspects.setData(architecture.getNeuroscientificAspects());
	}

	@Override
	public boolean hasChanges() {
		return this.changes;
	}

	@Override
	public void setChanges(boolean changes) {
		this.changes = changes;
	}
}
