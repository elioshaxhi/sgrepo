/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client;

import com.customlib.seriousgame.client.models.DataBase;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;

/**
 *
 * @author NERTIL
 */
@RemoteServiceRelativePath("server")
public interface server extends RemoteService {

    public DataBase getData();
    public SeriousGame getSeriousGame(int id);
    public List<SeriousGame> getSeriousGames(String searchText);
    public PagingLoadResult<SeriousGame> getSeriousGames(FilterPagingLoadConfig filters, String contentSearch);
}
