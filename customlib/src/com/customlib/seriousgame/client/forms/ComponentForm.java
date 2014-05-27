/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.forms;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.grids.GenericGrid;
import com.customlib.seriousgame.client.interfaces.ClosureFormInterface;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.ModelTag;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.AnchorData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import java.util.List;

/**
 *
 * @author nertil
 */
public class ComponentForm extends Window implements ClosureFormInterface{

    private MainEntryPointInterface entrypoint = null;
    
    private FormPanel form;    
    private ComboBox<GenericModel> type;
    private TextField<String> name;
    private TextArea description;
    private TextArea specificSGCase;
    private GenericGrid pedagogicalParadigm;
    private GenericGrid learningGoals;
    private boolean okClose = false;
    private GenericModel component;

    public ComponentForm(final MainEntryPointInterface entrypoint) {
        this.entrypoint = entrypoint;
        setWidth((int)((double)com.google.gwt.user.client.Window.getClientWidth()*Utilities.formFactor));
        setHeading(Constants.Strings.b_new() + " " + Constants.Strings.arch_component());
        setMinWidth(Utilities.smallformWidth);
        setLayout(new FitLayout());
        setStyleName("form-font");
        setModal(true);
        setClosable(false);
        setButtonAlign(Style.HorizontalAlignment.CENTER);
        
        pedagogicalParadigm = new GenericGrid(entrypoint, Constants.Strings.arch_component_relationsWithPedagogicalApproaches(), null);
        learningGoals = new GenericGrid(entrypoint, Constants.Strings.arch_component_relationsWithLearningGoals(), null);
               
        Button ok_button = new Button(Constants.Strings.b_ok(), new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                if(!form.isValid())
                    return;
                ((Button)ce.getSource()).setEnabled(false);
                okClose = true;
                hide();
            }
        });
        Button cancel_button = new Button(Constants.Strings.b_cancel(), new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                hide();
            }
        });
        addButton(ok_button);
        addButton(cancel_button);
        
        form = new FormPanel();
        form.setEncoding(FormPanel.Encoding.MULTIPART);
        form.setMethod(FormPanel.Method.POST);
        form.setAction("ServletUpload");
        form.setHeaderVisible(false);
        FormLayout layout = new FormLayout(FormPanel.LabelAlign.LEFT);
        layout.setLabelWidth(Utilities.formLabelWidth);
        form.setLayout(layout);
        form.setWidth(Utilities.smallformWidth);
        form.setBodyBorder(false);
        
        FormData formData = new FormData("-20"); 
        
        name = new TextField<String>();  
        name.setFieldLabel(Constants.Strings.name());  
        name.setAllowBlank(false);  
        name.getFocusSupport().setPreviousId(getButtonBar().getId());  
        name.setMaxLength(Utilities.varCharLimitShort);
        form.add(name, formData); 
        
        ListStore<GenericModel> store = new ListStore<GenericModel>();  
        store.add(entrypoint.getDatabase().getComponentTypesList());
        
        type = new ComboBox<GenericModel>(); 
        type.setForceSelection(true);
        type.setAllowBlank(false);
        type.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
        type.setFieldLabel(Constants.Strings.type());  
        type.setDisplayField(ModelTag.name.toString()); 
        type.setTriggerAction(ComboBox.TriggerAction.ALL);  
        type.setStore(store);
        if(entrypoint.getDatabase().getComponentTypesList().size()>0)
            type.setValue(entrypoint.getDatabase().getComponentTypesList().get(0));
        form.add(type, formData); 
        
        description = new TextArea();  
        description.setPreventScrollbars(true);
        description.setFieldLabel(Constants.Strings.description());  
        description.setMaxLength(Utilities.textLimitNormal);
        form.add(description, formData); 
        
        specificSGCase = new TextArea();  
        specificSGCase.setPreventScrollbars(true);
        specificSGCase.setFieldLabel(Constants.Strings.arch_specificSGCase());   
        specificSGCase.setMaxLength(Utilities.textLimitNormal);
        specificSGCase.setToolTip(Constants.Strings.tt_arch_component_apecificSGCase());
        form.add(specificSGCase, formData); 
        
        pedagogicalParadigm.SetGridHeaders(Constants.Strings.type(), ModelTag.name.toString(), Constants.Strings.notes(), ModelTag.description.toString());
        pedagogicalParadigm.SetAddButtonSelectionListner(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                final GenericGrid source = (GenericGrid)((Button)ce.getSource()).getParent().getParent();
                GenericModelSelectionForm form = new GenericModelSelectionForm(entrypoint, entrypoint.getDatabase().getContextPedagogicalParadigmListForComponent());
                form.addListener(Events.Hide, new Listener<WindowEvent>() {

                    @Override
                    public void handleEvent(WindowEvent be) {
                        GenericModelSelectionForm gsf = (GenericModelSelectionForm) be.getSource();
                        if (gsf.isOkClose()) {
                            source.addModel(gsf.getGenericModel());
                        }
                    }
                });
                form.show();           
            }
        });
        pedagogicalParadigm.addToolBarButtonButton(new Button(Constants.Strings.b_edit(), IconHelper.create("images/edit.png"),  new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                final GenericGrid source = (GenericGrid)((Button)ce.getSource()).getParent().getParent();
                GenericModel model = source.getSelection();
                if(model == null)
                    return;
                GenericModelSelectionForm form = new GenericModelSelectionForm(entrypoint, entrypoint.getDatabase().getContextPedagogicalParadigmList());
                form.setModel(model);
                form.addListener(Events.Hide, new Listener<WindowEvent>() {

                    @Override
                    public void handleEvent(WindowEvent be) {
                        GenericModelSelectionForm gsf = (GenericModelSelectionForm) be.getSource();
                        if (gsf.isOkClose()) {
                            source.removeSelectedElements();
                            source.addModel(gsf.getGenericModel());
                        }
                    }
                });
                form.show();
            }
        }));
        
        AnchorData anchoreData = new AnchorData("100%", new Margins(0, 15, 10, 0));
        form.add(pedagogicalParadigm, anchoreData);
        
        learningGoals.SetGridHeaders(Constants.Strings.type(), ModelTag.name.toString(), Constants.Strings.subtype(), ModelTag.subType.toString());
        learningGoals.setHeaderToolTip(Constants.Strings.tt_context_learningGoals());
        learningGoals.SetAddButtonSelectionListner(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                final GenericGrid source = (GenericGrid)((Button)ce.getSource()).getParent().getParent();
                GenericModelSelectionForm form = new GenericModelSelectionForm(entrypoint, entrypoint.getDatabase().getContextLearningGoalsTypesList());
                form.setSubSubType(entrypoint.getDatabase().getContextSoftSkillList()); 
                form.setSubType(entrypoint.getDatabase().getContextLearningGoalsList());
                form.addListener(Events.Hide, new Listener<WindowEvent>() {

                    @Override
                    public void handleEvent(WindowEvent be) {
                        GenericModelSelectionForm gsf = (GenericModelSelectionForm) be.getSource();
                        if (gsf.isOkClose()) {
                            source.addModel(gsf.getGenericModel());
                        }
                    }
                });
                form.show(); 
                form.setSubSubType(entrypoint.getDatabase().getContextSoftSkillList()); 
                form.setSubType(entrypoint.getDatabase().getContextLearningGoalsList()); 
            }
        });
        learningGoals.addToolBarButtonButton(new Button(Constants.Strings.b_edit(), IconHelper.create("images/edit.png"),  new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                final GenericGrid source = (GenericGrid)((Button)ce.getSource()).getParent().getParent();
                GenericModel model = source.getSelection();
                if(model == null)
                    return;
                GenericModelSelectionForm form = new GenericModelSelectionForm(entrypoint, entrypoint.getDatabase().getContextLearningGoalsTypesList());
                form.setSubSubType(entrypoint.getDatabase().getContextSoftSkillList()); 
                form.setSubType(entrypoint.getDatabase().getContextLearningGoalsList()); 
                form.addListener(Events.Hide, new Listener<WindowEvent>() {

                    @Override
                    public void handleEvent(WindowEvent be) {
                        GenericModelSelectionForm gsf = (GenericModelSelectionForm) be.getSource();
                        if (gsf.isOkClose()) {
                            source.removeSelectedElements();
                            source.addModel(gsf.getGenericModel());
                        }
                    }
                });
                form.show();
                form.setSubSubType(entrypoint.getDatabase().getContextSoftSkillList()); 
                form.setSubType(entrypoint.getDatabase().getContextLearningGoalsList()); 
                form.setModel(model);
            }
        }));
        form.add(learningGoals, anchoreData);
        
        add(form);
        
    }
    public GenericModel getComponent()
    {
//        int id=-1;
//        if(component != null)
//            id=component.getId();
        component = new GenericModel(component.getId(), name.getValue(), description.getValue());        
        component.set(ModelTag.componentTypeID.toString(), type.getValue().getId());
        component.set(ModelTag.componentType.toString(), type.getValue().getName());
        component.set(ModelTag.specificSGCase.toString(), specificSGCase.getValue());
        component.set(ModelTag.pedagogicalParadigm.toString(), pedagogicalParadigm.getData());
        component.set(ModelTag.learningGoals.toString(), learningGoals.getData());
        return component;
    }
    /**
    * the data came from the respective query call
    */
    public void setComponent(GenericModel component)
    {
        this.component = component;
        Integer componentTypeId = (Integer)component.get(ModelTag.componentTypeID.toString());
        if((componentTypeId == null || componentTypeId.intValue() == 0) &&
                entrypoint.getDatabase().getComponentTypesList().size()>0)
        {
            type.setValue(entrypoint.getDatabase().getComponentTypesList().get(0));
        }
        else
        {
            for (GenericModel model : entrypoint.getDatabase().getComponentTypesList()) {
                if (model.getId().intValue() == componentTypeId.intValue()) {
                    type.setValue(model);
                }
            }
        }
        name.setValue(component.getName());
        description.setValue(component.getDescription());
        specificSGCase.setValue((String)component.get(ModelTag.specificSGCase.toString()));
        pedagogicalParadigm.setData((List<GenericModel>)component.get(ModelTag.pedagogicalParadigm.toString()));
        learningGoals.setData((List<GenericModel>)component.get(ModelTag.learningGoals.toString()));
    }
    /**
     * @return the okClose
     */
    @Override
    public boolean isOkClose() {
        return okClose;
    }
}
