/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.customlib.seriousgame.client.grids;


import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.forms.ArchitectureForm;
import com.customlib.seriousgame.client.forms.ComponentForm;
import com.customlib.seriousgame.client.interfaces.GridBasicFuncionalities;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.ModelTag;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.tips.QuickTip;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nertil
 */
public class ComponentGrid extends ContentPanel implements GridBasicFuncionalities
{
    private MainEntryPointInterface entrypoint = null;

    private MyExtendedClasses.CostomGrid<GenericModel> grid;
    private CheckBoxSelectionModel<GenericModel> cbSelectionModel;
    private Button b_add;
    private ToolBar toolBar;
    private int componentTempId = 0;
    private Listener<MessageBoxEvent> focusListner = null;

    public ComponentGrid(final MainEntryPointInterface entrypoint, String header, final ArrayList<GenericModel> sourceData)
    {
        this.entrypoint = entrypoint;
        setHeaderVisible(false);
        setLayout(new FitLayout());
        setHeight(150);
        

        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

        cbSelectionModel = new CheckBoxSelectionModel<GenericModel>();
        
        configs.add(cbSelectionModel.getColumn());

        ColumnConfig column = new ColumnConfig(ModelTag.name.toString(), Constants.Strings.name(), 250);
        configs.add(column);
        
        column = new ColumnConfig(ModelTag.componentType.toString(), Constants.Strings.arch_type(), 150); 
        configs.add(column);
        
        column = new ColumnConfig(ModelTag.description.toString(), Constants.Strings.description(), 150);        
        column.setRenderer(Utilities.getRenderCellWithTooltip());
        configs.add(column);
        
        column = new ColumnConfig(ModelTag.specificSGCase.toString(), Constants.Strings.arch_specificSGCase(), 150);        
        column.setRenderer(Utilities.getRenderCellWithTooltip());
        configs.add(column);

        ListStore<GenericModel> store = new ListStore<GenericModel>();
        
        ColumnModel cm = new ColumnModel(configs);
        grid = new MyExtendedClasses.CostomGrid<GenericModel>(store, cm, cbSelectionModel, ModelTag.name.toString(), this);
        
        
        b_add = new Button(Constants.Strings.b_add(), IconHelper.create("images/new.gif"));
        if (sourceData != null) {
            b_add.addSelectionListener(new SelectionListener<ButtonEvent>() {

                @Override
                public void componentSelected(ButtonEvent ce) {
                    newElement();
                }
            });
        }
        Button b_delete = new Button(Constants.Strings.b_delete(), IconHelper.create("images/delete.gif"), new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                deleteElements();
            }
        });
        
        Button b_edit = new Button(Constants.Strings.b_edit(), IconHelper.create("images/edit.png"),  new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                editElement();
            }
        });
        
        Html title = new Html(header);
        title.addStyleName("relations-title");
        title.setStyleAttribute("font", "bold 11px tahoma,arial,verdana,sans-serif");
        title.setStyleAttribute("marginLeft", "5px");

        toolBar = new ToolBar();
        if(header.length() > 0)
        {
            toolBar.add(title);
        }
        toolBar.add(new FillToolItem()); 
        toolBar.add(b_add);
        toolBar.add(b_edit);
        toolBar.add(b_delete);
        
        add(grid);
        setTopComponent(toolBar);
        
        new QuickTip(grid); 
        
        focusListner = new Listener<MessageBoxEvent>() {

            @Override
            public void handleEvent(MessageBoxEvent be) {
                grid.focus();
            }
        };

    }
    public void SetAddButtonSelectionListner(SelectionListener<ButtonEvent> listner)
    {
        b_add.addSelectionListener(listner);
    }
    public void SetGridHeaders(String firstColumn, String secondColumn)
    {
        if(grid.getColumnModel().getColumnCount() == 3) {
            grid.getColumnModel().getColumns().get(1).setHeader(firstColumn);
            grid.getColumnModel().getColumns().get(2).setHeader(secondColumn);
        }
        
    }
    public void addToolBarButtonButton(Button button)
    {
        toolBar.insert(button, 1);
    }
    
    public void setData(List<GenericModel> data)
    {
        grid.getStore().removeAll();
        grid.getStore().add(data);
        ((ArchitectureForm)grid.getParent().getParent()).componentListChange();
    }
    public List<GenericModel> getData()
    {
        return grid.getStore().getModels();
    }
    public void addModel(GenericModel record)
    {
        grid.getStore().add(record);
        ((ArchitectureForm)grid.getParent().getParent()).componentListChange();
    }

    @Override
    public void deleteElements() {
        List<GenericModel> records = cbSelectionModel.getSelectedItems();
        for (GenericModel model : records) {
            grid.getStore().remove(model);
        }
        ((ArchitectureForm)grid.getParent().getParent()).componentListChange();
    }

    @Override
    public void editElement() {
        List<GenericModel> records = cbSelectionModel.getSelectedItems();
        if(records.isEmpty())
        {
            MessageBox.alert(Constants.Strings.alert(), Constants.Strings.zeroSelection(), focusListner);
            return;
        }
        if(records.size()>1)
        {
            MessageBox.alert(Constants.Strings.alert(), Constants.Strings.moreSelection(), focusListner);
            return;
        }
        
        ComponentForm form = new ComponentForm(entrypoint);
        form.setComponent(records.get(0));
        form.addListener(Events.Hide, new Listener<WindowEvent>() {

            @Override
            public void handleEvent(WindowEvent be) {
                ComponentForm gsf = (ComponentForm) be.getSource();
                if (gsf.isOkClose()) {
                    deleteElements();
                    addModel(gsf.getComponent());
                    grid.focus();
                }
            }
        });
        form.show();
    }

    @Override
    public void newElement() {
        ComponentForm form = new ComponentForm(entrypoint);
        form.setComponent(new GenericModel(--componentTempId, null, null));
        form.addListener(Events.Hide, new Listener<WindowEvent>() {

            @Override
            public void handleEvent(WindowEvent be) {
                ComponentForm gsf = (ComponentForm) be.getSource();
                if (gsf.isOkClose()) {
                    addModel(gsf.getComponent());
                    grid.focus();
                }
            }
        });
        form.show();
    }
    
}