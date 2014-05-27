/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.forms;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.interfaces.ClosureFormInterface;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.ModelTag;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.tips.QuickTip;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nertil
 */
public class GenericSelectionForm extends Window implements ClosureFormInterface{

    private MainEntryPointInterface entrypoint = null;
    
    
    Grid<GenericModel> grid = null;
    CheckBoxSelectionModel<GenericModel> cbSelectionModel = null;
    private boolean okClose = false;
    

    public GenericSelectionForm(final MainEntryPointInterface entrypoint, String winTitle, List<GenericModel> data, List<GenericModel> selection) {
        this.entrypoint = entrypoint;
        setWidth(Utilities.smallformWidth);
        setHeading(winTitle);
        setMinWidth(Utilities.smallformWidth);
        setLayout(new FitLayout());
        setStyleName("form-font");
        setModal(true);
        setButtonAlign(Style.HorizontalAlignment.CENTER);
        setClosable(false);
        setHeight(Utilities.smallformWidth);
               
        Button ok_button = new Button(Constants.Strings.b_ok(), new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
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
        
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

        cbSelectionModel = new CheckBoxSelectionModel<GenericModel>();
        
        configs.add(cbSelectionModel.getColumn());

        ColumnConfig column = new ColumnConfig(ModelTag.name.toString(), Constants.Strings.name(), 250);
        configs.add(column);
        
        column = new ColumnConfig(ModelTag.description.toString(), Constants.Strings.description(), 150);        
        column.setRenderer(Utilities.getRenderCellWithTooltip());
        configs.add(column);

        ListStore<GenericModel> store = new ListStore<GenericModel>();
        store.add(data);
        
        ColumnModel cm = new ColumnModel(configs);
        grid = new Grid<GenericModel>(store, cm);
        grid.setSelectionModel(cbSelectionModel);
        grid.addPlugin(cbSelectionModel);
        grid.setAutoExpandColumn(ModelTag.name.toString());
        grid.setStyleAttribute("border", "none");
        grid.getView().setAdjustForHScroll(false);
        grid.getView().setAutoFill(true);
        grid.setBorders(false);
        grid.setStripeRows(true);
        
        add(grid, new FitData());
        cbSelectionModel.setSelection(selection);
        new QuickTip(grid); 
    }
    public List<GenericModel> getSelectedData()
    {
        return cbSelectionModel.getSelectedItems();
    }
    /**
     * @return the okClose
     */
    @Override
    public boolean isOkClose() {
        return okClose;
    }
}
