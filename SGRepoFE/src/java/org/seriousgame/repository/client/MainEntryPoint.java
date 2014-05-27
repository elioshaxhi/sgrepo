/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client;

import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.DataBase;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.MarginData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import java.util.List;

/**
 * Main entry point.
 *
 * @author NERTIL Qatipi
 */
public class MainEntryPoint implements EntryPoint, MainEntryPointInterface {


    private serverAsync server;
    private DataBase database = null;
    private Page page = null;
    /**
     * Creates a new instance of MainEntryPoint
     */
    public MainEntryPoint() {
    }

    /**
     * The entry point method, called automatically by loading a module that
     * declares an implementing class as an entry-point
     */
    @Override
    public void onModuleLoad() {
        server = (serverAsync)GWT.create(server.class);
        server.getData(asyncDatabase);
        page = new Page(this);

        Viewport port = new Viewport();
        port.setStyleName("port");
        port.setLayout(new FillLayout());
        port.add(page, new MarginData(2));
        RootPanel.get().add(port);


    }
    public static void debug()
    {
        int pippo = 3;
        pippo = 4;
        pippo++;
    }
      public final AsyncCallback<DataBase> asyncDatabase = new AsyncCallback<DataBase>() {

        @Override
        public void onSuccess(DataBase result) {
            database = result;
            GXT.hideLoadingPanel("loading");
            page.layout(true);
        }
        @Override
        public void onFailure(Throwable caught) {
            MessageBox.info("Exceprion", "Failed to get data from database.", null);
            GXT.hideLoadingPanel("loading");
        }
    };

    @Override
    public DataBase getDatabase() {
        return database;
    }

    @Override
    public void getPublishers(AsyncCallback<List<GenericModel>> callback) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void getGeneric(String tableName, AsyncCallback<List<GenericModel>> callback) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void exequteQuery(String query, AsyncCallback<Integer> callback) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateSeriousGame(SeriousGame record, AsyncCallback<SeriousGame> callback) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void getSeriousGame(int currentUser, int id, AsyncCallback<SeriousGame> callback) {
        server.getSeriousGame(id, callback);
    }

    @Override
    public void insertNewSeriousGame(SeriousGame record, AsyncCallback<SeriousGame> callback) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void getSeriousGames(int currentUserId, AsyncCallback<List<SeriousGame>> callback) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void getSeriousGame(String searchText, AsyncCallback<List<SeriousGame>> callback) {
        server.getSeriousGames(searchText, callback);
    }



    public void mask(boolean mask)
    {
        if(mask)
            page.mask("Loading...");
        else
            page.unmask();
    }
    public serverAsync getServices() {
        return server;
    }
}
