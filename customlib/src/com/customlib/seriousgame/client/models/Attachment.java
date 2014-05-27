/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.customlib.seriousgame.client.models;

import com.customlib.seriousgame.client.DbTablesSpecialColums;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.google.gwt.user.client.rpc.IsSerializable;



public class Attachment extends BaseModel implements IsSerializable
{

    public Attachment()
    {
    }
    
    public Attachment(int id, int seriousGameId, String name, String description)
    {
        set(ModelTag.id.toString(), id);
        set(ModelTag.name.toString(), name);
        set(ModelTag.originalName.toString(), description);
        set(DbTablesSpecialColums.seriousGameID.toString(), seriousGameId);
    }

    public Integer getId() {
        return (Integer) get(ModelTag.id.toString());
    }
    public Attachment(String info)
    {
        String words[] = info.split("_\\$:_");
        if(words.length == 4)
        {
            set(ModelTag.id.toString(), Integer.parseInt(words[0]));
            set(DbTablesSpecialColums.seriousGameID.toString(), Integer.parseInt(words[1]));
            set(ModelTag.name.toString(), words[2]);
            set(ModelTag.originalName.toString(), words[3]);
        }

    }
    @Override
    public String toString()
    {
        return  get(ModelTag.id.toString()) + "_$:_" +
                get(DbTablesSpecialColums.seriousGameID.toString()) + "_$:_" +
                get(ModelTag.name.toString()) + "_$:_" +
                get(ModelTag.originalName.toString());
    }
}
