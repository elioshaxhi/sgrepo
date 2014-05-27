/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sg.client;

//import com.customlib.seriousgame.client.analytics.GenresModel;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.ContributersMessages;
import com.customlib.seriousgame.client.models.DataBase;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.sg.client.forms.LoginForm;

/**
 * Main_entry_point
 *
 * @author NERTIL 
 */
public class MainEntryPoint implements EntryPoint, MainEntryPointInterface {


    private serverAsync server;
    private Page page = null;
    private DataBase database = null;

    private ArrayList<ContributersMessages> tempmessages = null;

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
        Utilities.formWidth = ((int)(Window.getClientWidth()*0.75));

        server = (serverAsync)GWT.create(server.class);
        page = new Page(this);
        getPage().init();

        LoginForm login = new LoginForm(this);
        login.checkUser();

    }
    public void run()
    {
        RootPanel.get("corpus").add(getPage());
    }
    /**
    * the call of this  method come from loginform class
    * succesivly i fill the grid create before with data usin filldata metod
    */
    public final AsyncCallback<DataBase> asyncDatabase = new AsyncCallback<DataBase>() {

        @Override
        public void onSuccess(DataBase result) {
            database = result;
            fillData();
            run();
            getPage().getTopPanel().setUser();
        }
        @Override
        public void onFailure(Throwable caught)
	{
            MessageBox.info("Exceprion", "Failed to get data from database.", null);
        }
    };
    private void fillData()
    {
        getPage().getCentralPanel().getseriousGameGrid().reload();
    }

    /**
     * @return the server
     */
    public serverAsync getServer() {
        return server;
    }
    /**
     * @return the database
     */
    public DataBase getDatabase() {
        return database;
    }

    /**
     * @return the page
     */
    public Page getPage() {
        return page;
    }
    public void debug()
    {
        int pippo = 3;
        pippo = 4;
    }

    @Override
    public void getPublishers(AsyncCallback<List<GenericModel>> callback) {
        this.getServer().getPublishers(callback);
    }

    @Override
    public void getGeneric(String tableName, AsyncCallback<List<GenericModel>> callback) {
        this.getServer().getGeneric(tableName, callback);
    }

    @Override
    public void exequteQuery(String query, AsyncCallback<Integer> callback) {
        this.getServer().exequteQuery(query, callback);
    }

    @Override
    public void updateSeriousGame(SeriousGame record, AsyncCallback<SeriousGame> callback) {
        this.getServer().updateSeriousGame(record, callback);
    }

    @Override
    public void getSeriousGame(int currentUser, int id, AsyncCallback<SeriousGame> callback) {
        this.getServer().getSeriousGame(currentUser, id, callback);
    }

    @Override
    public void insertNewSeriousGame(SeriousGame record, AsyncCallback<SeriousGame> callback) {
        this.getServer().insertNewSeriousGame(record, callback);
    }

    @Override
    public void getSeriousGames(int currentUserId, AsyncCallback<List<SeriousGame>> callback) {
        this.getServer().getSeriousGames(currentUserId, callback);
    }
    //robba inutile but necesery for no error
  //@Override
  //  public void getSeriousGameAnalytics(AsyncCallback<List<GenresModel>> callback) {
//	this.getServer().getSeriousGameAnalytics(callback);
  //  }
  //  @Override
  //  public List<GenresModel> getAllAnalytics(){
//	    return new LinkedList<GenresModel>();
  //  }
}

