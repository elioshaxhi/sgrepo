/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.customlib.seriousgame.client.models;

public class GenericModelWithRelation extends GenericModel
{

    public GenericModelWithRelation()
    {
    }
    
    public GenericModelWithRelation(int id, String name, String description, int relationID)
    {
        super(id, name, description);
        set(ModelTag.relationID.toString(), relationID);
    }
    public Integer getRelationId() {
        return (Integer) get(ModelTag.relationID.toString());
    }
}
