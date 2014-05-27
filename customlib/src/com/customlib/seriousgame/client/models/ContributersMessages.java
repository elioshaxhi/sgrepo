/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.customlib.seriousgame.client.models;

import com.customlib.seriousgame.client.DbTables;
import com.customlib.seriousgame.client.DbTablesSpecialColums;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.google.gwt.user.client.rpc.IsSerializable;


public class ContributersMessages extends BaseModel implements IsSerializable
{
    public static final String TableName = DbTables.contributors_messages.toString();

    public static final String TagId = "id";
    public static final String TagSenderId = "SenderID";
    public static final String TagSender = "Sender";
    public static final String TagSeriousGameId = DbTablesSpecialColums.seriousGameID.toString();
    public static final String TagSeriousGame = "SeriousGame";
    
    public ContributersMessages()
    {
    }
    
    public ContributersMessages(int id, int senderId,String sender, int seriousGameId, String seriousGame)
    {
        set(ContributersMessages.TagId, id);
        set(ContributersMessages.TagSenderId, senderId);
        set(ContributersMessages.TagSender, sender);
        set(ContributersMessages.TagSeriousGameId, seriousGameId);
        set(ContributersMessages.TagSeriousGame, seriousGame);
    }

    public Integer getId() {
        return (Integer) get(ContributersMessages.TagId);
    }
    
}
