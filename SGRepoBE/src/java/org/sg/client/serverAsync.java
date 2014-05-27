/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sg.client;

import com.customlib.seriousgame.client.models.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import org.xml.sax.SAXException;

/**
 *
 * @author NERTIL
 */
public interface serverAsync {

    public void getData(User currentUser, AsyncCallback<DataBase> callback);
    public void exequteQuery(String query, AsyncCallback<Integer> callback);
    public void getPublishers(AsyncCallback<List<GenericModel>> callback);
    public void getGeneric(String tableName, AsyncCallback<List<GenericModel>> callback);
    public void getSeriousGames(int currentUserId, AsyncCallback<List<SeriousGame>> callback);
    public void insertNewSeriousGame(SeriousGame record, AsyncCallback<SeriousGame> callback);
    public void getUser(String username, String password, AsyncCallback<User> callback);
    public void getContributersMessages(int userId, AsyncCallback<List<ContributersMessages>> callback);
    public void getSeriousGame(int currentUser, int id, AsyncCallback<SeriousGame> callback);
    public void updateSeriousGame(SeriousGame record, AsyncCallback<SeriousGame> callback);
}
