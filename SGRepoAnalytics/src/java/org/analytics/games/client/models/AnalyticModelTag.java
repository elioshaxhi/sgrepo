/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analytics.games.client.models;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author NERTIL
 */
public enum AnalyticModelTag implements IsSerializable {
    idItem("idItem"),
    nameItem("nameItem"),
    numGames("NumeroGiochi");
    private String tag;

    AnalyticModelTag()
    {
    }
    AnalyticModelTag(String column)
    {
        this.tag = column;
    }
    @Override
    public String toString()
    {
        return tag;
    }
}
