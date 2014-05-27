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
public class LearningEnvironment implements IsSerializable{

    private Integer id;
    private Integer seriousGameID;
    private String feedback;
    private String motivation;
    private String sociality;
    private String gradualityOfLearning;
    private String transfer;
    private String assessment;
    private String supportToExploitation;
    private String supportToLearnByDoing;
    private String personalizationAndAdaptation;
    private String other;    
    
    private boolean changes = false;

    public LearningEnvironment() {
    }

    public LearningEnvironment(Integer id, Integer seriousGameID, String feedback, String motivation, String sociality, String gradualityOfLearning, String transfer, String assessment, String supportToExploitation, String supportToLearnByDoing, String personalizationAndAdaptation, String other, boolean changes) {
        this.id = id;
        this.seriousGameID = seriousGameID;
        this.feedback = feedback;
        this.motivation = motivation;
        this.sociality = sociality;
        this.gradualityOfLearning = gradualityOfLearning;
        this.transfer = transfer;
        this.assessment = assessment;
        this.supportToExploitation = supportToExploitation;
        this.supportToLearnByDoing = supportToLearnByDoing;
        this.personalizationAndAdaptation = personalizationAndAdaptation;
        this.other = other;
        
        this.changes = changes;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSeriousGameID() {
        return seriousGameID;
    }

    public String getFeedback() {
        return feedback;
    }

    public String getMotivation() {
        return motivation;
    }

    public String getSociality() {
        return sociality;
    }

    public String getGradualityOfLearning() {
        return gradualityOfLearning;
    }

    public String getTransfer() {
        return transfer;
    }

    public String getAssessment() {
        return assessment;
    }

    public String getSupportToExploitation() {
        return supportToExploitation;
    }

    public String getSupportToLearnByDoing() {
        return supportToLearnByDoing;
    }

    public String getPersonalizationAndAdaptation() {
        return personalizationAndAdaptation;
    }

    public String getOther() {
        return other;
    }
    
    public boolean hasChanges() {
        return changes;
    }
}
