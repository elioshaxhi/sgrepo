/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sg.client;

import com.customlib.seriousgame.client.models.*;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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
@RemoteServiceRelativePath("server")
public interface server extends RemoteService {

    public DataBase getData(User currentUser);
    public int exequteQuery(String query);
    public List<GenericModel> getPublishers();
    public List<GenericModel> getGeneric(String tableName);
    public List<SeriousGame> getSeriousGames(int currentUserId);
    public SeriousGame insertNewSeriousGame(SeriousGame record);
    public User getUser(String username, String password);
    public List<ContributersMessages> getContributersMessages(int owner);
    public SeriousGame getSeriousGame(int currentUser, int id);
    public SeriousGame updateSeriousGame(SeriousGame record);
}
