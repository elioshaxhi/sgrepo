/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.forms;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.DbTables;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.grids.GenericGrid;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.interfaces.SeriousGameDataInteractionForm;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.ModelTag;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.customlib.seriousgame.client.models.Technology;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.*;
import com.extjs.gxt.ui.client.widget.layout.AnchorData;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import java.util.List;

/**
 *
 * @author NERTIL
 */
public class TechnologyForm extends FormPanel implements SeriousGameDataInteractionForm{

    private MainEntryPointInterface entrypoint;
        
    private ComboBox<GenericModel> gamePlatform;
    private TextField<String> processor;
    private NumberField ram;    
    private NumberField disk;
    private ComboBox<GenericModel> bandwidth;
    private ComboBox<GenericModel> inputDeviceRequired;
    
    private GenericGrid deploymentStyle;
    private GenericGrid targetRange;
    
    private boolean changes = false;
    
    public TechnologyForm(final MainEntryPointInterface entrypoint) {
        this.entrypoint = entrypoint;
        deploymentStyle = new GenericGrid(entrypoint, Constants.Strings.tec_deploymentStyle(), null);
        targetRange = new GenericGrid(entrypoint, Constants.Strings.tec_targetRange(), null);
    
        setBodyBorder(false);
        setHeaderVisible(false);
        setButtonAlign(Style.HorizontalAlignment.CENTER);
        setLayout(new FormLayout());
        setScrollMode(Style.Scroll.AUTO);
        setPadding(5);
        
        FormData formData = new FormData("-15");
        AnchorData anchoreData = new AnchorData("100%", new Margins(0, 15, 10, 0));
        
        ListStore<GenericModel> gamePlatformStore = new ListStore<GenericModel>();  
        gamePlatformStore.add(entrypoint.getDatabase().getGamePlatformList());
        
        gamePlatform = new ComboBox<GenericModel>(); 
        gamePlatform.setForceSelection(true);
        gamePlatform.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
        gamePlatform.setFieldLabel(Constants.Strings.tec_gamePlatform());  
        gamePlatform.setDisplayField(ModelTag.name.toString());  
        gamePlatform.setTriggerAction(ComboBox.TriggerAction.ALL);  
        gamePlatform.setStore(gamePlatformStore);
        gamePlatform.addSelectionChangedListener(new SelectionChangedListener<GenericModel>() {

            @Override
            public void selectionChanged(SelectionChangedEvent<GenericModel> se) {
                Utilities.otherSelectedOnComboBox(entrypoint, se);
            }
        });
        add(gamePlatform, formData);

        FieldSet fieldSet = new FieldSet();
        fieldSet.setHeading(Constants.Strings.tec_minimalTecnicalRequirements());
        fieldSet.setCollapsible(true); 
        FormLayout layout = new FormLayout();
        layout.setLabelWidth(90);
        fieldSet.setLayout(layout); 

        processor = new TextField<String>();  
        processor.setFieldLabel(Constants.Strings.tec_processor());
        processor.setMaxLength(Utilities.varCharLimitShort);
        fieldSet.add(processor, new FormData("-5")); 
        
        ram = new NumberField();  
        ram.setFieldLabel(Constants.Strings.tec_RAM());
        fieldSet.add(ram, new FormData("-5"));
        
        disk = new NumberField();  
        disk.setFieldLabel(Constants.Strings.tec_Disc());
        fieldSet.add(disk, new FormData("-5"));
        
        ListStore<GenericModel> bandwidthStore = new ListStore<GenericModel>();  
        bandwidthStore.add(entrypoint.getDatabase().getBandwidthList());
        
        bandwidth = new ComboBox<GenericModel>(); 
        bandwidth.setForceSelection(true);
        bandwidth.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
        bandwidth.setFieldLabel(Constants.Strings.tec_bandwidth());  
        bandwidth.setDisplayField(ModelTag.name.toString());  
        bandwidth.setTriggerAction(ComboBox.TriggerAction.ALL);  
        bandwidth.setStore(bandwidthStore);
        fieldSet.add(bandwidth, new FormData("-5"));
        add(fieldSet, anchoreData);
        
        ListStore<GenericModel> inputDeviceRequiredStore = new ListStore<GenericModel>();  
        inputDeviceRequiredStore.add(entrypoint.getDatabase().getInputDeviceRequiredList());
        
        inputDeviceRequired = new ComboBox<GenericModel>(); 
        inputDeviceRequired.setForceSelection(true);
        inputDeviceRequired.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
        inputDeviceRequired.setFieldLabel(Constants.Strings.tec_inputDeviceRequired());  
        inputDeviceRequired.setDisplayField(ModelTag.name.toString());  
        inputDeviceRequired.setTriggerAction(ComboBox.TriggerAction.ALL);  
        inputDeviceRequired.setStore(inputDeviceRequiredStore);
        inputDeviceRequired.addSelectionChangedListener(new SelectionChangedListener<GenericModel>() {

            @Override
            public void selectionChanged(SelectionChangedEvent<GenericModel> se) {
                Utilities.otherSelectedOnComboBox(entrypoint, se);
            }
        });
        add(inputDeviceRequired, formData); 
        
        deploymentStyle.SetGridHeaders(Constants.Strings.type(), ModelTag.name.toString(), Constants.Strings.notes(), ModelTag.description.toString());
        deploymentStyle.SetAddButtonSelectionListner(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                final GenericGrid source = (GenericGrid)((Button)ce.getSource()).getParent().getParent();
                GenericModelSelectionForm form = new GenericModelSelectionForm(entrypoint, entrypoint.getDatabase().getDeploymentStyleList());
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
        deploymentStyle.addToolBarButtonButton(new Button(Constants.Strings.b_edit(), IconHelper.create("images/edit.png"),  new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                final GenericGrid source = (GenericGrid)((Button)ce.getSource()).getParent().getParent();
                GenericModel model = source.getSelection();
                if(model == null)
                    return;
                GenericModelSelectionForm form = new GenericModelSelectionForm(entrypoint, entrypoint.getDatabase().getDeploymentStyleList());
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
        add(deploymentStyle, anchoreData);
        
        targetRange.SetGridHeaders(Constants.Strings.type(), ModelTag.name.toString(), Constants.Strings.notes(), ModelTag.description.toString());
        targetRange.SetAddButtonSelectionListner(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                final GenericGrid source = (GenericGrid)((Button)ce.getSource()).getParent().getParent();
                GenericModelSelectionForm form = new GenericModelSelectionForm(entrypoint, entrypoint.getDatabase().getTargetRangeList());
                form.setSubType(entrypoint.getDatabase().getTargetRangeTypesList()); 
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
                form.setSubType(entrypoint.getDatabase().getTargetRangeTypesList());                
            }
        });
        targetRange.addToolBarButtonButton(new Button(Constants.Strings.b_edit(), IconHelper.create("images/edit.png"),  new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                final GenericGrid source = (GenericGrid)((Button)ce.getSource()).getParent().getParent();
                GenericModel model = source.getSelection();
                if(model == null)
                    return;
                GenericModelSelectionForm form = new GenericModelSelectionForm(entrypoint, entrypoint.getDatabase().getTargetRangeList());
                form.setSubType(entrypoint.getDatabase().getTargetRangeTypesList()); 
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
                form.setSubType(entrypoint.getDatabase().getTargetRangeTypesList());
            }
        }));
        add(targetRange, anchoreData);
    }
    @Override
    public void updateRecord(SeriousGame record)
    {
        Integer gamePlatformId = null;
        if(gamePlatform.getValue() != null)
        {
            gamePlatformId = gamePlatform.getValue().getId();
            Utilities.updateOtherList(record, DbTables.game_platforms.toString(), gamePlatform);
        }
        Integer inputDeviceRequiredId = null;
        if(inputDeviceRequired.getValue() != null)
        {
            inputDeviceRequiredId = inputDeviceRequired.getValue().getId();
            Utilities.updateOtherList(record, DbTables.input_device_required.toString(), inputDeviceRequired);
        }
        Integer bandwidthId = null;
        if(bandwidth.getValue() != null)
            bandwidthId = bandwidth.getValue().getId();
        Integer ram = null;
        if(this.ram.getValue() != null)
            ram = this.ram.getValue().intValue();
        Integer disk = null;
        if(this.disk.getValue() != null)
            disk = this.disk.getValue().intValue();
        
        Technology technology = new Technology(-1, record.getId(), gamePlatformId, inputDeviceRequiredId, 
                processor.getValue(), ram, disk, bandwidthId,deploymentStyle.getData(), targetRange.getData(), hasChanges());
        
        record.set(SeriousGame.TagTechnology, technology);
    }
    @Override
    public void setRecord(SeriousGame record)
    {
        Technology technology = record.get(SeriousGame.TagTechnology);
        if(technology == null)
            return;
        Integer gamePlatformId = technology.getGamePlatformID();
        if(gamePlatformId != null && gamePlatformId.intValue() > 0)
            Utilities.setComboBoxSelectionWhithOtherList(new GenericModel(gamePlatformId, "", ""), ModelTag.id.toString(), gamePlatform, (List<GenericModel>)record.get(SeriousGame.TagOthers), DbTables.game_platforms.toString());
        
        Integer inputDeviceRequiredId = technology.getInputDeviceReruiredID();
        if(inputDeviceRequiredId != null && inputDeviceRequiredId.intValue() > 0)
            Utilities.setComboBoxSelectionWhithOtherList(new GenericModel(inputDeviceRequiredId, "", ""), ModelTag.id.toString(), inputDeviceRequired, (List<GenericModel>)record.get(SeriousGame.TagOthers), DbTables.input_device_required.toString());
        
        Integer bandwidthId = technology.getBandwidthID();
        if(bandwidthId != null && bandwidthId.intValue() > 0)
            Utilities.setComboBoxSelection(new GenericModel(bandwidthId, "", ""), ModelTag.id.toString(), bandwidth);
        
        processor.setValue(technology.getProcessor());
        if(technology.getRAM() != null && technology.getRAM() > 0)
            ram.setValue(technology.getRAM());
        else
            ram.setValue(null);
        if(technology.getDisk() != null && technology.getDisk() > 0)
            disk.setValue(technology.getDisk()); 
        else
            disk.setValue(null);
        
        deploymentStyle.setData(technology.getDeploymentStyle());
        targetRange.setData(technology.getTargetRange());
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
