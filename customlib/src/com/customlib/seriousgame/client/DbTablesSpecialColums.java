/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author NERTIL
 */
public enum DbTablesSpecialColums implements IsSerializable {
    targetRangeID("TargetRangeID"),
    targetRangeTypeID("TargetRangeTypeID"),
    industrySchoolID("IndustrySchoolID"),
    learningTopicID("LearningTopicID"),
    learningGoalsID("LearningGoalsID"),
    learningGoalsTypesID("LearningGoalsTypesID"),
    seriousGameID("SeriousGameID"),
    ageID("AgeID"),
    marketID("MarketID"),
    genreID("GenreID"),
    userID("UserID"),
    relationTable("RelationalTable"),
    deploymentStyleID("DeployementStyleID"),
    notes("Notes"),
    le_feedback("Feedback"),
    le_motivation("Motivation"),
    le_sociality("Sociality"),
    le_gradualityOfLearning("GradualityOfLearning"),
    le_transfer("Transfer"),
    le_assessment("Assessment"),
    le_supportToExploitation("SupportToExploitation"),
    le_supportToLearnByDoing("SupportToLearnByDoing"),
    le_personalizationAndAdaptation("PersonalizationAndAdaptation"),
    le_other("Other"),
    contextID("ContextID"),
    componentID("ComponentID"),
    learningTopicTypeID("LearningTopicTypeID"),
    learningSubTopicID("LearningSubTopicID"),
    pedagogicalParadigmTypeID("PedagogicalParadigmTypeID"),
    learningGoalsTypeID("LearningGoalsTypeID"),
    learningGoalsSubTypeID("LearningGoalsSubTypeID"),
    learningGoalsSoftSkillsTypeID("LearningGoalsSoftSkillsTypeID"),
    methodology("Methodology"),
    referenceContext("ReferenceContext"),
    effectiveness("Effectiveness"),
    effectivenessDescription("EffectivenessDescription"),
    efficiency("Efficiency"),
    efficiencyDescription("EfficiencyDescription"),
    usability("Usability"),
    usabilityDescription("UsabilityDescription"),
    diffusion("Diffusion"),
    diffusionDescription("DiffusionDescription"),
    feedbackAssessmentSupport("FeedbackAssessmentSupport"),
    feedbackAssessmentSupportDescription("FeedbackAssessmentSupportDescription"),
    exploitabilityLearningContext("ExploitabilityLearningContext"),
    exploitabilityLearningContextDescription("ExploitabilityLearningContextDescription"),
    reusabilityDifferentLearningContext("ReusabilityDifferentLearningContext"),
    reusabilityDifferentLearningContextDescription("ReusabilityDifferentLearningContextDescription"),
    capabilityMotivatingUser("CapabilityMotivatingUser"),
    capabilityMotivatingUserDescription("CapabilityMotivatingUserDescription"),
    capabilityEngagingUser("CapabilityEngagingUser"),
    capabilityEngagingUserDescription("CapabilityEngagingUserDescription"),
    originalName("OriginalName");

    private String column;

    DbTablesSpecialColums()
    {
    }
    DbTablesSpecialColums(String column)
    {
        this.column = column;
    }
    @Override
    public String toString()
    {
        return column;
    }
}