/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.customlib.seriousgame.client.models;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.List;
import java.lang.Comparable;


public class GenericModel extends BaseModel implements IsSerializable {
    private GenericModel other = null;
    private Context context = null;
    private List<GenericModel> list = null;
    
    public GenericModel()
    {
    }
    
    public GenericModel(int id, String name, String description)
    {
        set(ModelTag.id.toString(), id);
        set(ModelTag.name.toString(), name);
        set(ModelTag.description.toString(), description);
    }

    public Integer getId() {
        return (Integer) get(ModelTag.id.toString());
    }
    public String getName()
    {
        return (String)get(ModelTag.name.toString());
    }
    public String getDescription()
    {
        return (String)get(ModelTag.description.toString());
	}

}
