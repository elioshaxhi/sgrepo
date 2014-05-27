/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.interfaces;

import com.customlib.seriousgame.client.models.DataBase;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;

/**
 *
 * @author NERTIL
 */
public interface MainEntryPointInterface {
    public DataBase getDatabase();
    public void getPublishers(AsyncCallback<List<GenericModel>> callback);
    public void getGeneric(String tableName, AsyncCallback<List<GenericModel>> callback);
    public void exequteQuery(String query, AsyncCallback<Integer> callback);
    public void updateSeriousGame(SeriousGame record, AsyncCallback<SeriousGame> callback);
    public void getSeriousGame(int currentUser, int id, AsyncCallback<SeriousGame> callback);
    public void insertNewSeriousGame(SeriousGame record, AsyncCallback<SeriousGame> callback);
    public void getSeriousGames(int currentUserId, AsyncCallback<List<SeriousGame>> callback);
}
