/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.models;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author NERTIL
 */
public enum ModelTag implements IsSerializable {
    id("id"),
    name("name"),
    description("description"),
    link("link"),
    originalName("originalName"),
    subType("subType"),
    relationID("relationID"),
    subSubTypeID("suSubTypeID"),
    algorithmID("algorithmID"),
    algorithmTypesID("algorithmTypesID"),
    algorithmType("algorithmType"),
    componentID("componentID"),
    componentTypeID("componentTypeID"),
    componentType("componentType"),
    specificSGCase("specificSGCase"),
    relatedSGComponents("relatedSGComponents"),
    oldComponentId("oldComponentId"),
    gamePlatformID("GamePlatformID"),
    inputDeviceReruiredID("InputDeviceReruiredID"),
    processor("Processor"),
    ram("RAM"),
    disk("Disk"),     
    bandwidthID("BandwidthID"),
    other("Other"),
    context("Context"),
    pedagogicalParadigm("PedagogicalParadigm"),
    learningGoals("LearningGoals");

    private String tag;

    ModelTag()
    {
    }
    ModelTag(String column)
    {
        this.tag = column;
    }
    @Override
    public String toString()
    {
        return tag;
    }
}
