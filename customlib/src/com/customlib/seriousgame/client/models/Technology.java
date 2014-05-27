/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.models;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.List;

/**
 *
 * @author NERTIL
 */
public class Technology implements IsSerializable{

    private Integer id;
    private Integer seriousGameID;
    private Integer gamePlatformID;
    private Integer inputDeviceReruiredID;
    private String processor;
    private Integer ram;
    private Integer disk;
    private List<GenericModel> deploymentStyle;
    private List<GenericModel> targetRange;
    private Integer bandwidthID;
    
    private boolean changes = false;

    public Technology() {
    }

    public Technology(Integer id, Integer seriousGameID, Integer gamePlatformID, 
            Integer inputDeviceReruiredID, String processor, Integer ram, Integer disk, 
            Integer bandwidthID, List<GenericModel> deploymentStyle, List<GenericModel> targetRange,
            boolean changes) {
        this.id = id;
        this.seriousGameID = seriousGameID;
        this.gamePlatformID = gamePlatformID;
        this.inputDeviceReruiredID = inputDeviceReruiredID;
        this.processor = processor;
        this.ram = ram;
        this.disk = disk;
        this.bandwidthID = bandwidthID;
        this.deploymentStyle = deploymentStyle;
        this.targetRange = targetRange;
        
        this.changes = changes;
    }
    public Integer getId() {
        return id;
    }
    public Integer getGamePlatformID() {
        return gamePlatformID;
    }
    public Integer getDisk() {
        return disk;
    }
    public Integer getInputDeviceReruiredID() {
        return inputDeviceReruiredID;
    }
    public String getProcessor() {
        return processor;
    }
    public Integer getRAM() {
        return ram;
    }
    public Integer getSeriousGameID() {
        return seriousGameID;
    }
    public Integer getBandwidthID() {
        return bandwidthID;
    }

    public boolean hasChanges() {
        return changes;
    }

    public List<GenericModel> getDeploymentStyle() {
        return deploymentStyle;
    }

    public List<GenericModel> getTargetRange() {
        return targetRange;
    }
}
