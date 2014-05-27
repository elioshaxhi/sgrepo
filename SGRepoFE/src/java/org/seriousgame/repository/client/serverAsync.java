/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client;

import com.customlib.seriousgame.client.models.DataBase;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;

/**
 *
 * @author NERTIL
 */
public interface serverAsync {

    public void getData(AsyncCallback<DataBase> callback);
    public void getSeriousGame(int id, AsyncCallback<SeriousGame> callback);
    public void getSeriousGames(String searchText, AsyncCallback<List<SeriousGame>> callback);
    public void getSeriousGames(FilterPagingLoadConfig filters, String contentSearch, AsyncCallback<PagingLoadResult<SeriousGame>> callback);
}
