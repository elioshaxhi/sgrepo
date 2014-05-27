/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.customlib.seriousgame.client.models;

import java.util.List;

public class Context extends GenericModel
{
    public static final String tagTypeID = "typeID";  
    public static final String tagType="type";
    public static final String tagEnvironmentID = "environmentID"; 
    public static final String tagEnvironment = "environment"; 
    public static final String tagIndustrySchoolID = "industrySchoolID"; 
    public static final String tagIndustrySchoolSubTypeID = "industrySchoolSubTypeID"; 
    public static final String tagIndustrySchoolDescription = "industrySchoolDescription"; 
    public static final String tagLearnerRoleID = "learnerRoleID"; 
    public static final String tagLearnerRoleDescription = "learnerRoleDescription"; 
    public static final String tagInstructorRoleID = "instructorRoleID"; 
    public static final String tagInstructorRoleDescription = "instructorRoleDescription";
    public static final String tagLearningTopics = "learningTopics"; 
    public static final String tagPedagogicalParadigm = "pedagogicalParadigm"; 
    public static final String tagLearningGoals = "learningGoals"; 

    public Context()
    {
    }
    
    public Context(int id, String name, String description,
            int typeID, String type,
            int environmentID, String environment,
            int industrySchoolID, int industrySchoolSubTypeID, String industrySchoolDescription,
            int learnerRoleID, String learnerRoleDescription,
            int instructorRoleID, String instructorRoleDescription,
            List<GenericModel> learningTopics, 
            List<GenericModel> pedagogicalParadigm,
            List<GenericModel> learningGoals)
    {
        super(id, name, description);
        set(Context.tagTypeID, typeID);
        set(Context.tagType, type);
        set(Context.tagEnvironmentID, environmentID);
        set(Context.tagEnvironment, environment);
        set(Context.tagIndustrySchoolID, industrySchoolID);
        set(Context.tagIndustrySchoolSubTypeID, industrySchoolSubTypeID);
        set(Context.tagIndustrySchoolDescription, industrySchoolDescription);
        set(Context.tagLearnerRoleID, learnerRoleID);
        set(Context.tagLearnerRoleDescription, learnerRoleDescription);
        set(Context.tagInstructorRoleID, instructorRoleID);
        set(Context.tagInstructorRoleDescription, instructorRoleDescription);
        set(Context.tagLearningTopics, learningTopics);
        set(Context.tagPedagogicalParadigm, pedagogicalParadigm);
        set(Context.tagLearningGoals, learningGoals);
    }
}
