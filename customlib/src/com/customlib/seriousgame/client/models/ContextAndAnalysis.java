/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.models;

import com.customlib.seriousgame.client.Utilities;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NERTIL
 */
public class ContextAndAnalysis implements IsSerializable{

    private List<GenericModel> contexts;
    private Analysis analysis;
    
    private boolean changes = false;

    public ContextAndAnalysis() {
    }

    public ContextAndAnalysis(List<GenericModel> contexts, Analysis analysis, boolean changes) {
        this.contexts = contexts;
        this.analysis = analysis;
        this.changes = changes;
    }

    public List<GenericModel> getContexts() {
        return contexts;
    }
    public boolean hasChanges() {
        return changes;
    }

    public Analysis getAnalysis() {
        return analysis;
    }
 
    public void setContexts(List<GenericModel> newContexts)
    {
        for (GenericModel newModel : newContexts) {
            for (GenericModel oldModel : contexts) {
                if(areComponentsEquals(newModel, oldModel)){
                    newModel.set(ModelTag.oldComponentId.toString(), oldModel.getId());
                    break;
                }
            }
        }
        this.contexts = newContexts;
        updateContextsIds();
    }
    private boolean areComponentsEquals(GenericModel model1, GenericModel model2)
    {
        boolean name = false;
        String name1 = model1.getName();
        String name2 = model2.getName();
        if(name1 != null && name2 != null)
            name = name1.equals(name2);
        return name;
    }
    private void updateContextsIds()
    {
        List<Integer> referenceContextIds = Utilities.getListFromString(analysis.getReferenceContext());
        
        List<Integer> newComponentsIds = new ArrayList<Integer>();
        if (referenceContextIds != null && referenceContextIds.size() > 0) {
            for (Integer oldId : referenceContextIds) {
                for (GenericModel component : contexts) {
                    Integer oldComponentId = component.get(ModelTag.oldComponentId.toString());
                    if (oldId != null && oldComponentId != null && oldComponentId.intValue() == oldId.intValue()) {
                        newComponentsIds.add(component.getId());
                    }
                }
            }
        }
        analysis.setReferenceContext(Utilities.getStringFromList(newComponentsIds));
    }
    
}
