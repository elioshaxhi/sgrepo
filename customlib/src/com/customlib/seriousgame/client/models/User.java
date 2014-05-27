/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.customlib.seriousgame.client.models;

import com.customlib.seriousgame.client.DbTables;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.google.gwt.user.client.rpc.IsSerializable;


public class User extends BaseModel implements IsSerializable
{
    public static final String TableName = DbTables.users.toString();

    public static final String TagId = "id";
    public static final String TagName = "name";
    public static final String TagUserName = "username";
    public static final String TagPassword = "password";    
    public static final String TagEmail = "email";

    public User()
    {
    }
    
    public User(int id, String name, String username, String password, String email)
    {
        set(User.TagId, id);
        set(User.TagName, name);
        set(User.TagUserName, username);
        set(User.TagPassword, password);
        set(User.TagEmail, email);
    }
    public User(String info)
    {
        String words[] = info.split("_\\$_");
        if(words.length == 5)
        {
            set(User.TagId, Integer.parseInt(words[0]));
            set(User.TagName, words[1]);
            set(User.TagUserName, words[2]);
            set(User.TagPassword, words[3]);
            set(User.TagEmail, words[4]);
        }

    }

    public Integer getId() {
        return (Integer) get(User.TagId);
    } 
    
    @Override
    public String toString()
    {
        return  get(User.TagId) + "_$_" +
                get(User.TagName) + "_$_" +
                get(User.TagUserName) + "_$_" +
                get(User.TagPassword) + "_$_" +
                get(User.TagEmail);
    }
}
