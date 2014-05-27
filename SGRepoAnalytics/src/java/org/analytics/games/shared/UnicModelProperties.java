/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package org.analytics.games.shared;
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


import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface UnicModelProperties extends PropertyAccess<UnicModel> {
  @Path("id")
  ModelKeyProvider<UnicModel> key();

  @Path("name")
  LabelProvider<UnicModel> nameLabel();

  ValueProvider<UnicModel, Integer> numGames();

  ValueProvider<UnicModel, String> numQuantita();

  ValueProvider<UnicModel, Integer> idItem();

  ValueProvider<UnicModel, String> nomeItem();

}