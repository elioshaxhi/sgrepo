/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.models;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NERTIL
 */
public class Architecture implements IsSerializable {

	private List<GenericModel> components;
	private List<GenericModel> algorithms;
	private List<GenericModel> gameEngines;
	private List<GenericModel> interoperabilityAndStandards;
	private List<GenericModel> psichologicalAspects;
	private List<GenericModel> neuroscientificAspects;
	private boolean changes = false;

	public Architecture() {
	}

	public Architecture(List<GenericModel> components, List<GenericModel> algorithms, List<GenericModel> gameEngines, List<GenericModel> interoperabilityAndStandards, List<GenericModel> psichologicalAspects, List<GenericModel> neuroscientificAspects, boolean changes) {
		this.components = components;
		this.algorithms = algorithms;
		this.gameEngines = gameEngines;
		this.interoperabilityAndStandards = interoperabilityAndStandards;
		this.psichologicalAspects = psichologicalAspects;
		this.neuroscientificAspects = neuroscientificAspects;

		this.changes = changes;
	}

	public List<GenericModel> getComponents() {
		return components;
	}

	public List<GenericModel> getAlgorithms() {
		return algorithms;
	}

	public List<GenericModel> getGameEngines() {
		return gameEngines;
	}

	public List<GenericModel> getInteroperabilityAndStandards() {
		return interoperabilityAndStandards;
	}

	public List<GenericModel> getPsichologicalAspects() {
		return psichologicalAspects;
	}

	public List<GenericModel> getNeuroscientificAspects() {
		return neuroscientificAspects;
	}

	public void setComponents(List<GenericModel> newComponents) {
		for (GenericModel newModel : newComponents) {
			for (GenericModel oldModel : components) {
				if (areComponentsEquals(newModel, oldModel)) {
					newModel.set(ModelTag.oldComponentId.toString(), oldModel.getId());
					break;
				}
			}
		}
		this.components = newComponents;
		updateComponentsIds(algorithms);
		updateComponentsIds(interoperabilityAndStandards);
		updateComponentsIds(psichologicalAspects);
		updateComponentsIds(neuroscientificAspects);
	}

	private boolean areComponentsEquals(GenericModel model1, GenericModel model2) {
		boolean type = false;
		boolean name = false;
		String name1 = model1.getName();
		Integer componentTypeId1 = model1.get(ModelTag.componentTypeID.toString());

		String name2 = model2.getName();
		Integer componentTypeId2 = model2.get(ModelTag.componentTypeID.toString());
		if (componentTypeId1 != null && componentTypeId2 != null) {
			type = componentTypeId1 == componentTypeId2;
		}
		if (name1 != null && name2 != null) {
			name = name1.equals(name2);
		}

		return type && name;
	}

	private void updateComponentsIds(List<GenericModel> list) {
		for (GenericModel genericModel : list) {
			List<Integer> newComponentsIds = new ArrayList<Integer>();
			List<Integer> componentIds = genericModel.get(ModelTag.relatedSGComponents.toString());
			if (componentIds != null && componentIds.size() > 0) {
				for (Integer oldId : componentIds) {
					for (GenericModel component : components) {
						Integer oldComponentId = component.get(ModelTag.oldComponentId.toString());
						if (oldId != null && oldComponentId != null && oldComponentId.intValue() == oldId.intValue()) {
							newComponentsIds.add(component.getId());
						}
					}
				}
			}
			genericModel.set(ModelTag.relatedSGComponents.toString(), newComponentsIds);
		}
	}

	public boolean hasChanges() {
		return changes;
	}
}
