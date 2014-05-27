/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.customlib.seriousgame.client.grids;


import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.forms.GenericForm;
import com.customlib.seriousgame.client.forms.GenericSelectionForm;
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
public class GenericGrid extends ContentPanel implements GridBasicFuncionalities
{
    private MainEntryPointInterface entrypoint = null;

    private MyExtendedClasses.CostomGrid<GenericModel> grid;
    private CheckBoxSelectionModel<GenericModel> cbSelectionModel;
    private Button b_add;
    private ToolBar toolBar;
    private Html title;
    private  List<GenericModel> sourceData = null;

    public GenericGrid(final MainEntryPointInterface entrypoint, String header, List<GenericModel> source)
    {
        this.entrypoint = entrypoint;
        this.sourceData = source;
        setHeaderVisible(false);
        setLayout(new FitLayout());
        setHeight(150);
        

        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

        cbSelectionModel = new CheckBoxSelectionModel<GenericModel>();
        
        configs.add(cbSelectionModel.getColumn());

        ColumnConfig column = new ColumnConfig(ModelTag.name.toString(), Constants.Strings.name(), 250);
        configs.add(column);
        
        column = new ColumnConfig(ModelTag.description.toString(), Constants.Strings.description(), 150);        
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
                removeSelectedElements();
            }
        });
        
        title = new Html(header);
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
        toolBar.add(b_delete);
        
        add(grid);
        setTopComponent(toolBar);
        
        new QuickTip(grid); 

    }
    public void setHeaderToolTip(String toolTip)
    {
        title.setToolTip(toolTip);
    }
    public void SetAddButtonSelectionListner(SelectionListener<ButtonEvent> listner)
    {
        b_add.addSelectionListener(listner);
    }
    public void SetGridHeaders(String firstColumnHeader, String firstColumnId, String secondColumnHeader, String secondColumnId)
    {
        if(grid.getColumnModel().getColumnCount() == 3) {
            grid.getColumnModel().getColumns().get(1).setHeader(firstColumnHeader);
            grid.getColumnModel().getColumns().get(1).setId(firstColumnId);
            grid.getColumnModel().getColumns().get(2).setHeader(secondColumnHeader);
            grid.getColumnModel().getColumns().get(2).setId(secondColumnId);
        }
        
    }
    public void addToolBarButtonButton(Button button)
    {
        toolBar.insert(button, 3);
    }
    public void setSourceData(List<GenericModel> data)
    {
        this.sourceData = data;
    }
    public void setData(List<GenericModel> data)
    {
        grid.getStore().removeAll();
        grid.getStore().add(data);
    }
    
    public void addModel(GenericModel record)
    {
        grid.getStore().add(record);
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
    public void removeSelectedElements()
    {
        deleteElements();
    }
    public List<GenericModel> getData()
    {
        return grid.getStore().getModels();
    }
    private List<GenericModel> getDataSelectedFromSourceData()
    {
        List<GenericModel> retval = new ArrayList<GenericModel>();
        for (GenericModel sourceModel : sourceData) {
            for (GenericModel selectedModel : getData()) {
                if(sourceModel.getId().intValue() == selectedModel.getId().intValue())
                    retval.add(sourceModel);
            }
        }
        return retval;
    }

    @Override
    public void deleteElements() {
        List<GenericModel> records = cbSelectionModel.getSelectedItems();
        for (GenericModel model : records) {
            grid.getStore().remove(model);
        }
    }

    @Override
    public void editElement() {
        Button edit = (Button)toolBar.getItem(3);
        if(edit.getText().equals(Constants.Strings.b_edit()))
            edit.fireEvent(Events.Select);
    }

    @Override
    public void newElement() {
        GenericSelectionForm form = new GenericSelectionForm(entrypoint, Constants.Strings.genericSelections(), sourceData, getDataSelectedFromSourceData());
        form.addListener(Events.Hide, new Listener<WindowEvent>() {

            @Override
            public void handleEvent(WindowEvent be) {
                GenericSelectionForm gsf = (GenericSelectionForm) be.getSource();
                if (gsf.isOkClose()) {
                    final List<GenericModel> store = gsf.getSelectedData();
                    boolean others = false;
                    for (final GenericModel model : store) {
                        if ((model.getName()).equals(Utilities.otherString)) {
                            others = true;
                            GenericForm gf = new GenericForm(entrypoint, Constants.Strings.other(), null);
                            gf.addListener(Events.Hide, new Listener<WindowEvent>() {

                                @Override
                                public void handleEvent(WindowEvent be) {
                                    GenericForm form = (GenericForm) be.getSource();
                                    if (form.isOkClose()) {
                                        GenericModel other = new GenericModel(-1, form.getName(), form.getDescription());
                                        model.set(ModelTag.name.toString(), model.getName() + " (" + form.getName() + ")");
                                        model.set(ModelTag.other.toString(), other);
                                        setData(store);
                                    }
                                }
                            });
                            gf.show();
                            break;
                        }
                    }
                    if (!others) {
                        setData(store);
                    }
                }
            }
        });
        form.show();
    }
}