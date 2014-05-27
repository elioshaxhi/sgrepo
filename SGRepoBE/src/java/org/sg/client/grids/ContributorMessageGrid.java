/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sg.client.grids;


import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.interfaces.ReloadDataInterface;
import com.customlib.seriousgame.client.models.ContributersMessages;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.List;
import org.sg.client.MainEntryPoint;


/**
 *
 * @author nertil
 */
public class ContributorMessageGrid extends ContentPanel implements ReloadDataInterface
{
    private MainEntryPoint entrypoint = null;

    Grid<ContributersMessages> grid = null;
    CheckBoxSelectionModel<ContributersMessages> cbSelectionModel = null;

    public ContributorMessageGrid(MainEntryPoint entrypoint)
    {
        this.entrypoint = entrypoint;
    }
    public void init()
    {
        setBodyBorder(false);
        setHeaderVisible(false);
        setButtonAlign(Style.HorizontalAlignment.CENTER);
        setLayout(new FitLayout());
        //setSize("100%", "590px");

        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

        cbSelectionModel = new CheckBoxSelectionModel<ContributersMessages>();
        
        configs.add(cbSelectionModel.getColumn());

        ColumnConfig column = new ColumnConfig(ContributersMessages.TagSender, Constants.Strings.username(), 150);
        configs.add(column);

        column = new ColumnConfig(ContributersMessages.TagSeriousGame, Constants.Strings.sg(), 250);
        configs.add(column);

        ListStore<ContributersMessages> store = new ListStore<ContributersMessages>();
        store.add(entrypoint.getDatabase().getContributersMessagesList());
        
        ColumnModel cm = new ColumnModel(configs);
        grid = new Grid<ContributersMessages>(store, cm);
        grid.setSelectionModel(cbSelectionModel);
        grid.addPlugin(cbSelectionModel);
        grid.setAutoExpandColumn(ContributersMessages.TagSeriousGame);
        grid.setStyleAttribute("border", "none");
        grid.getView().setAdjustForHScroll(false);
        grid.getView().setAutoFill(true);
        grid.setBorders(false);
        grid.setStripeRows(true);
        

        Button deny = new Button(Constants.Strings.b_deny(), IconHelper.create("images/delete.gif"), new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
//                List<ContributersMessages> records = cbSelectionModel.getSelectedItems();
//                if(records.isEmpty())
//                {
//                    MessageBox.alert(Constants.Strings.alert(), Constants.Strings.zeroSelection(), null);
//                    return;
//                }
//                for (Iterator<ContributersMessages> it = records.iterator(); it.hasNext();) {
//                    ContributersMessages ContributersMessages = it.next();
//                    if (entrypoint.getUser().getId().intValue() != ((Integer) ContributersMessages.get(ContributersMessages.TagOwnerId)).intValue()) {
//                        MessageBox.alert(Constants.Strings.alert(), Constants.Strings.enableToDelete(), null);
//                        return;
//                    }
//                }
//                MessageBox.confirm(Constants.Strings.alert(), Constants.Strings.deleteMessage(), new Listener<MessageBoxEvent>() {
//
//                    @Override
//                    public void handleEvent(MessageBoxEvent be) {
//                        if(be.getButtonClicked().getItemId().equals(Dialog.YES))
//                            delete();
//                    }
//                });              
            }
        });
        Button allow = new Button(Constants.Strings.b_allow(), IconHelper.create("images/allow.gif"), new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
//                ContributersMessagesForm form = new ContributersMessagesForm(entrypoint);
//                form.setHeading(Constants.Strings.b_new() + " " + Constants.Strings.sg().toLowerCase());
//                form.show();
//                form.addListener(Events.Hide, new Listener<WindowEvent>() {
//
//                        @Override
//                        public void handleEvent(WindowEvent be) {
//                            ClosureFormInterface form = (ClosureFormInterface) be.getSource();
//                            if (form.isOkClose()) {  
//                                getDataFromServer();
//                            }
//                        }
//                    });
            }
        });

        ToolBar toolBar = new ToolBar();
        toolBar.add(allow);
        toolBar.add(deny);        
        add(grid);
        setBottomComponent(toolBar);

    }
    public void reload()
    {
        grid.getStore().removeAll();
        grid.getStore().add(entrypoint.getDatabase().getContributersMessagesList());
        entrypoint.getPage().getTopPanel().setUser();
        unmask();
    }
    @Override
    public void getDataFromServer()
    {
        mask(Constants.Strings.loading());
        entrypoint.getServer().getContributersMessages(entrypoint.getDatabase().getCurrentUser().getId().intValue(), asyncGetContributersMessages);
    }
    final AsyncCallback<List<ContributersMessages>> asyncGetContributersMessages = new AsyncCallback<List<ContributersMessages>>() {

        @Override
        public void onSuccess(List<ContributersMessages> result) {
            if(result != null)
            {
                entrypoint.getDatabase().setContributersMessagesList(result);
                reload();
            }
        }
        @Override
        public void onFailure(Throwable caught) {
            MessageBox.info("Exceprion", "Failed to get ContributersMessages.", null);
        }
    };
}