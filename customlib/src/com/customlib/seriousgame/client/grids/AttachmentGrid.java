/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.customlib.seriousgame.client.grids;


import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.forms.AttachmentForm;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.Attachment;
import com.customlib.seriousgame.client.models.ModelTag;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.*;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Window;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author nertil
 */
public class AttachmentGrid extends ContentPanel
{
    private MainEntryPointInterface entrypoint = null;

    Grid<Attachment> grid = null;
    CheckBoxSelectionModel<Attachment> cbSelectionModel = null;
    private int seriousgameId = -1;

    public AttachmentGrid(MainEntryPointInterface entrypoint)
    {
        this.entrypoint = entrypoint;
    }
    public void init()
    {
        setHeaderVisible(false);
        setLayout(new FitLayout());
        setHeight(150);

        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

        cbSelectionModel = new CheckBoxSelectionModel<Attachment>();
        
        configs.add(cbSelectionModel.getColumn());

        ColumnConfig column = new ColumnConfig(ModelTag.name.toString(), Constants.Strings.name(), 250);
        configs.add(column);
        column.setRenderer( new GridCellRenderer() {
            @Override
            public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore store, Grid grid) {
                String url = model.get(ModelTag.name.toString());
                if(url == null || url.length() == 0)
                    return "";
                
                return "<a href=\"" + Window.Location.getPath() + Utilities.uploadedFolder + "/" + url + "\" target=\"_blank\">" + url + "</a>";
            }
        });

        ListStore<Attachment> store = new ListStore<Attachment>();
        
        ColumnModel cm = new ColumnModel(configs);
        grid = new Grid<Attachment>(store, cm);
        grid.setSelectionModel(cbSelectionModel);
        grid.addPlugin(cbSelectionModel);
        grid.setAutoExpandColumn(ModelTag.name.toString());
        grid.setStyleAttribute("border", "none");
        grid.getView().setAdjustForHScroll(false);
        grid.getView().setAutoFill(true);
        grid.setBorders(false);
        grid.setStripeRows(true);
        
        Button b_add = new Button(Constants.Strings.b_new(), IconHelper.create("images/new.gif"), new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                AttachmentForm form = new AttachmentForm(entrypoint, seriousgameId);
                form.setHeading(Constants.Strings.loadFile());
                form.show();
                form.addListener(Events.Hide, new Listener<WindowEvent>() {

                        @Override
                        public void handleEvent(WindowEvent be) {
                            AttachmentForm form = (AttachmentForm) be.getSource();
                            if (form.getCurrent() != null) {  
                                grid.getStore().add(form.getCurrent());
                            }
                        }
                    });
            }
        });
        Button b_delete = new Button(Constants.Strings.b_delete(), IconHelper.create("images/delete.gif"), new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                removeSelectedElements();
            }
        });
        
        Html title = new Html(Constants.Strings.attachments());
        title.addStyleName("relations-title");
        title.setStyleAttribute("font", "bold 11px tahoma,arial,verdana,sans-serif");
        title.setStyleAttribute("marginLeft", "5px");

        ToolBar toolBar = new ToolBar();
        toolBar.add(title);
        toolBar.add(new FillToolItem()); 
        toolBar.add(b_add);
        toolBar.add(b_delete);
        
        add(grid);
        setTopComponent(toolBar);

    }
    public void reload(ArrayList<Attachment> attachments)
    {
        grid.getStore().removeAll();
        grid.getStore().add(attachments);
        unmask();
    }

    public void setSeriousgameId(int seriousgameId) {
        this.seriousgameId = seriousgameId;
    }
    public void setData(List<Attachment> data)
    {
        grid.getStore().removeAll();
        grid.getStore().add(data);
    }
    
    public void addModel(Attachment record)
    {
        grid.getStore().add(record);
    }
    public void removeSelectedElements()
    {
        List<Attachment> records = cbSelectionModel.getSelectedItems();
        for (Attachment model : records) {
            grid.getStore().remove(model);
        }
    }
    public List<Attachment> getData()
    {
        return grid.getStore().getModels();
    }
}