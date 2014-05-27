/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.customlib.seriousgame.client.grids;


import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.forms.ContextAndAnalysisForm;
import com.customlib.seriousgame.client.forms.ContextForm;
import com.customlib.seriousgame.client.interfaces.ClosureFormInterface;
import com.customlib.seriousgame.client.interfaces.GridBasicFuncionalities;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.Context;
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
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author nertil
 */
public class ContextGrid extends ContentPanel implements GridBasicFuncionalities
{
    private MainEntryPointInterface entrypoint = null;

    private MyExtendedClasses.CostomGrid<GenericModel> grid;
    private CheckBoxSelectionModel<GenericModel> cbSelectionModel;
    private Button b_add;
    private ToolBar toolBar;

    public ContextGrid(final MainEntryPointInterface entrypoint, final ArrayList<GenericModel> sourceData)
    {
        this.entrypoint = entrypoint;
        setHeaderVisible(false);
        setLayout(new FitLayout());
        setHeight(250);
        

        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

        cbSelectionModel = new CheckBoxSelectionModel<GenericModel>();
        
        configs.add(cbSelectionModel.getColumn());
        
        ColumnConfig column = new ColumnConfig(ModelTag.name.toString(), Constants.Strings.name(), 150);
        configs.add(column);

        column = new ColumnConfig(Context.tagType, Constants.Strings.context_type(), 150);
        configs.add(column);
                
        column = new ColumnConfig(ModelTag.description.toString(), Constants.Strings.description(), 250);
        configs.add(column);

        ListStore<GenericModel> store = new ListStore<GenericModel>();
        
        
        ColumnModel cm = new ColumnModel(configs);
        grid = new MyExtendedClasses.CostomGrid<GenericModel>(store, cm, cbSelectionModel, ModelTag.description.toString(), this);
        
        b_add = new Button(Constants.Strings.b_add(), IconHelper.create("images/new.gif"));
        if (sourceData != null) {
            b_add.addSelectionListener(new SelectionListener<ButtonEvent>() {

                @Override
                public void componentSelected(ButtonEvent ce) {
                    newElement();
                }
            });
        }
        Button b_edit = new Button(Constants.Strings.b_edit(), IconHelper.create("images/edit.png"));
        if (sourceData != null) {
            b_edit.addSelectionListener(new SelectionListener<ButtonEvent>() {

                @Override
                public void componentSelected(ButtonEvent ce) {
                    editElement();
                }
            });
        }
        Button b_delete = new Button(Constants.Strings.b_delete(), IconHelper.create("images/delete.gif"), new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                deleteElements();
            }
        });
        
        Html title = new Html(Constants.Strings.context());
        title.addStyleName("relations-title");
        title.setStyleAttribute("font", "bold 11px tahoma,arial,verdana,sans-serif");
        title.setStyleAttribute("marginLeft", "5px");

        toolBar = new ToolBar();
        toolBar.add(title);
        toolBar.add(new FillToolItem()); 
        toolBar.add(b_add);
        toolBar.add(b_edit);
        toolBar.add(b_delete);
        
        add(grid);
        setTopComponent(toolBar);
        
    }
    public void setData(List<GenericModel> data)
    {
        grid.getStore().removeAll();
        grid.getStore().add(data);
        ((ContextAndAnalysisForm)grid.getParent().getParent()).contextListChange();
    }
    public void addModel(GenericModel record)
    {
        grid.getStore().add(record);
        ((ContextAndAnalysisForm)grid.getParent().getParent()).contextListChange();
    }
    public List<GenericModel> getData()
    {
        return grid.getStore().getModels();
    }
    public GenericModel getSelection()
    {
        List<GenericModel> records = cbSelectionModel.getSelectedItems();
        if(records.isEmpty())
        {
            MessageBox.alert(Constants.Strings.alert(), Constants.Strings.zeroSelection(), null);
            return null;
        }
        if(records.size()>1)
        {
            MessageBox.alert(Constants.Strings.alert(), Constants.Strings.moreSelection(), null);
            return null;
        }
        return records.get(0);
    }

    @Override
    public void deleteElements() {
        List<GenericModel> records = cbSelectionModel.getSelectedItems();
        for (GenericModel model : records) {
            grid.getStore().remove(model);
        }
        ((ContextAndAnalysisForm)grid.getParent().getParent()).contextListChange();
    }

    @Override
    public void editElement() {
        final ContextForm form = new ContextForm(entrypoint, getData());
        form.setHeading(Constants.Strings.b_edit() + " " + Constants.Strings.context().toLowerCase());
        form.setContextModel(getSelection());
        form.addListener(Events.Hide, new Listener<WindowEvent>() {

            @Override
            public void handleEvent(WindowEvent be) {
                ClosureFormInterface gsf = (ClosureFormInterface) be.getSource();
                if (gsf.isOkClose()) {
                    deleteElements();
                    addModel(form.getContextModel());
                }
            }
        });
        form.show();
    }

    @Override
    public void newElement() {
        final ContextForm form = new ContextForm(entrypoint, getData());
        form.setHeading(Constants.Strings.b_new() + " " + Constants.Strings.context().toLowerCase());
        form.addListener(Events.Hide, new Listener<WindowEvent>() {

            @Override
            public void handleEvent(WindowEvent be) {
                ClosureFormInterface gsf = (ClosureFormInterface) be.getSource();
                if (gsf.isOkClose()) {
                    addModel(form.getContextModel());
                }
            }
        });
        form.show();
    }
}
