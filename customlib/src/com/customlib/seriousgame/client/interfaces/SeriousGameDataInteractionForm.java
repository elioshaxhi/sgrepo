/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.interfaces;

import com.customlib.seriousgame.client.models.SeriousGame;


/**
 *
 * @author NERTIL
 */
public interface SeriousGameDataInteractionForm {
    public void updateRecord(SeriousGame record);
    public void setRecord(SeriousGame record);
    public boolean hasChanges();
    public void setChanges(boolean changes);
}
