/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.analytics.games.client.models;

//import org.analytics.games.client.models.AnalyticModelTag;
//import com.extjs.gxt.ui.client.data.BaseModel;
//import com.google.gwt.user.client.rpc.IsSerializable;
//import java.util.List;
//
//
//public class UnicModel extends BaseModel implements IsSerializable {
//
//    public UnicModel()
//    {
//    }
//
//    public UnicModel(int numGames, int idItem, String nameItem)
//    {
//        set(AnalyticModelTag.numGames.toString(), numGames);
//        set(AnalyticModelTag.idItem.toString(), idItem);
//        set(AnalyticModelTag.nameItem.toString(), nameItem);
//    }
//
//    public Integer getNumGames() {
//        return (Integer) get(AnalyticModelTag.numGames.toString());
//    }
//
//    public Integer getIdItem()
//    {
//        return (Integer)get(AnalyticModelTag.idItem.toString());
//    }
//
//    public String getNameItem()
//    {
//        return (String)get(AnalyticModelTag.nameItem.toString());
//    }
//
//}

/**
 * Sencha GXT 3.1.0-beta - Sencha for GWT
 * Copyright(c) 2007-2014, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */


/**
 * Sencha GXT 3.1.0-beta - Sencha for GWT
 * Copyright(c) 2007-2014, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */

import java.io.Serializable;
import java.util.Date;

public class UnicModel implements Serializable {

  private Integer numGames;
  private Integer idItem;
  private String nomeItem;
  private String numQuantita;

  private static int COUNTER = 0;

  public UnicModel() {
  }

  public UnicModel(Integer numGames, Integer idItem, String nomeItem, String numQuantita) {
    this();
    this.numGames = numGames;
    this.idItem = idItem;
    this.nomeItem = nomeItem;
    this.numQuantita = numQuantita;
  }


  public Integer getNumGames() {
    return numGames;
  }

  public Integer getIdItem() {
    return idItem;
  }

  public String getNomeItem() {
    return nomeItem;
  }
  public String getNumQuantita() {
    return numQuantita;
  }


}