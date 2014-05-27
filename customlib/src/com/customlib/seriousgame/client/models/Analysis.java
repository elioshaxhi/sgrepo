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
public class Analysis implements IsSerializable{
    
    private String referenceContext;
    private String methodology;
    
    private int effectiveness;
    private String effectivenessDescription;
    private int efficiency;
    private String efficiencyDescription;
    private int usability;
    private String usabilityDescription;
    private int diffusion;
    private String diffusionDescription;
    private int feedbackAssessmentSupport;
    private String feedbackAssessmentSupportDescription;
    private int exploitabilityLearningContext;
    private String exploitabilityLearningContextDescription;
    private int reusabilityDifferentLearningContext;
    private String reusabilityDifferentLearningContextDescription;
    private int capabilityMotivatingUser;
    private String capabilityMotivatingUserDescription;
    private int capabilityEngagingUser;
    private String capabilityEngagingUserDescription;

    public Analysis() {
    }

    public Analysis(String referenceContext, String methodology, int effectiveness, String effectivenessDescription, int efficiency, String efficiencyDescription, int usability, String usabilityDescription, int diffusion, String diffusionDescription, int feedbackAssessmentSupport, String feedbackAssessmentSupportDescription, int exploitabilityLearningContext, String exploitabilityLearningContextDescription, int reusabilityDifferentLearningContext, String reusabilityDifferentLearningContextDescription, int capabilityMotivatingUser, String capabilityMotivatingUserDescription, int capabilityEngagingUser, String capabilityEngagingUserDescription) {
        this.referenceContext = referenceContext;
        this.methodology = methodology;
        this.effectiveness = effectiveness;
        this.effectivenessDescription = effectivenessDescription;
        this.efficiency = efficiency;
        this.efficiencyDescription = efficiencyDescription;
        this.usability = usability;
        this.usabilityDescription = usabilityDescription;
        this.diffusion = diffusion;
        this.diffusionDescription = diffusionDescription;
        this.feedbackAssessmentSupport = feedbackAssessmentSupport;
        this.feedbackAssessmentSupportDescription = feedbackAssessmentSupportDescription;
        this.exploitabilityLearningContext = exploitabilityLearningContext;
        this.exploitabilityLearningContextDescription = exploitabilityLearningContextDescription;
        this.reusabilityDifferentLearningContext = reusabilityDifferentLearningContext;
        this.reusabilityDifferentLearningContextDescription = reusabilityDifferentLearningContextDescription;
        this.capabilityMotivatingUser = capabilityMotivatingUser;
        this.capabilityMotivatingUserDescription = capabilityMotivatingUserDescription;
        this.capabilityEngagingUser = capabilityEngagingUser;
        this.capabilityEngagingUserDescription = capabilityEngagingUserDescription;
    }

    public String getReferenceContext() {
        return referenceContext;
    }

    public void setReferenceContext(String referenceContext) {
        this.referenceContext = referenceContext;
    }

    public String getMethodology() {
        return methodology;
    }

    public int getEffectiveness() {
        return effectiveness;
    }

    public String getEffectivenessDescription() {
        return effectivenessDescription;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public String getEfficiencyDescription() {
        return efficiencyDescription;
    }

    public int getUsability() {
        return usability;
    }

    public String getUsabilityDescription() {
        return usabilityDescription;
    }

    public int getDiffusion() {
        return diffusion;
    }

    public String getDiffusionDescription() {
        return diffusionDescription;
    }

    public int getFeedbackAssessmentSupport() {
        return feedbackAssessmentSupport;
    }

    public String getFeedbackAssessmentSupportDescription() {
        return feedbackAssessmentSupportDescription;
    }

    public int getExploitabilityLearningContext() {
        return exploitabilityLearningContext;
    }

    public String getExploitabilityLearningContextDescription() {
        return exploitabilityLearningContextDescription;
    }

    public int getReusabilityDifferentLearningContext() {
        return reusabilityDifferentLearningContext;
    }

    public String getReusabilityDifferentLearningContextDescription() {
        return reusabilityDifferentLearningContextDescription;
    }

    public int getCapabilityMotivatingUser() {
        return capabilityMotivatingUser;
    }

    public String getCapabilityMotivatingUserDescription() {
        return capabilityMotivatingUserDescription;
    }

    public int getCapabilityEngagingUser() {
        return capabilityEngagingUser;
    }

    public String getCapabilityEngagingUserDescription() {
        return capabilityEngagingUserDescription;
    }
    
    
}
