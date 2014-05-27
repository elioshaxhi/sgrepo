/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.customlib.seriousgame.client.models;

import com.customlib.seriousgame.client.Utilities;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nertil
 */
public class DataBase implements IsSerializable {

    private List<GenericModel> ageList;
    private List<GenericModel> availabilityList;
    private List<GenericModel> genreList;
    private List<GenericModel> marketList;
    private List<GenericModel> publisherList;
    private List<SeriousGame> seriousGameList;
    private List<GenericModel> statusList;
    private List<ContributersMessages> contributersMessagesList;
    private List<GenericModel> learningCurveList;
    private List<GenericModel> effectiveLearningTimeList;
    private List<GenericModel> gamePlatformList;
    private List<GenericModel> bandwidthList;
    private List<GenericModel> inputDeviceRequiredList;
    private List<GenericModel> deploymentStyleList;
    private List<GenericModel> targetRangeList;
    private List<GenericModelWithRelation> targetRangeTypesList;
    private List<GenericModel> componentTypesList;
    private List<GenericModel> algorithmTypesList;
    private List<GenericModel> contextTypesList;
    private List<GenericModel> contextEnvironmentsList;
    private List<GenericModelWithRelation> contextIndustriesSchoolsList;
    private List<GenericModel> contextIndustrySchoolTypesList;
    private List<GenericModel> contextLearnerRolesList;
    private List<GenericModel> contextInstructorRolesList;
    private List<GenericModel> contextLearningTopicsList;
    private List<GenericModelWithRelation> contextLearningSubTopicsList;
    private List<GenericModel> contextPedagogicalParadigmList;
    private List<GenericModel> contextPedagogicalParadigmListForComponent;    
    private List<GenericModel> contextLearningGoalsTypesList;
    private List<GenericModelWithRelation> contextLearningGoalsList;
    private List<GenericModelWithRelation> contextSoftSkillList;
    
    private List<Integer> years;
    
    private User currentUser;
    

    public DataBase()
    {
        ageList = new ArrayList<GenericModel>();
        availabilityList = new ArrayList<GenericModel>();
        genreList = new ArrayList<GenericModel>();
        learningCurveList = new ArrayList<GenericModel>();
        marketList = new ArrayList<GenericModel>();
        publisherList = new ArrayList<GenericModel>();        
        seriousGameList = new ArrayList<SeriousGame>();
        statusList = new ArrayList<GenericModel>();
        years = new ArrayList<Integer>();
        contributersMessagesList = new ArrayList<ContributersMessages>();
        effectiveLearningTimeList = new ArrayList<GenericModel>();
        targetRangeTypesList = new ArrayList<GenericModelWithRelation>();
        componentTypesList = new ArrayList<GenericModel>();
        algorithmTypesList =new ArrayList<GenericModel>();
        contextTypesList = new ArrayList<GenericModel>();
        contextEnvironmentsList = new ArrayList<GenericModel>();
        contextIndustriesSchoolsList = new ArrayList<GenericModelWithRelation>();
        contextIndustrySchoolTypesList = new ArrayList<GenericModel>();
        contextLearnerRolesList = new ArrayList<GenericModel>();
        contextInstructorRolesList = new ArrayList<GenericModel>();
        contextLearningTopicsList = new ArrayList<GenericModel>();
        contextLearningSubTopicsList = new ArrayList<GenericModelWithRelation>();
        contextPedagogicalParadigmList = new ArrayList<GenericModel>();
        contextPedagogicalParadigmListForComponent = new ArrayList<GenericModel>();
        contextLearningGoalsTypesList = new ArrayList<GenericModel>();
        contextLearningGoalsList = new ArrayList<GenericModelWithRelation>();
        contextSoftSkillList = new ArrayList<GenericModelWithRelation>();
    }

    public DataBase(List<GenericModel> ageList, 
            List<GenericModel> availabilityList, 
            List<GenericModel> genreList, 
            List<GenericModel> learningCurveList, 
            List<GenericModel> marketList, 
            List<GenericModel> publisherList, 
            List<SeriousGame> seriousGameList, 
            List<GenericModel> statusList,
            List<ContributersMessages> contributersMessageses,
            User currentUser,
            List<GenericModel> effectiveLearningTimeList,
            List<GenericModel> gamePlatformList,
            List<GenericModel> bandwidthList,
            List<GenericModel> inputDeviceRequiredList,
            List<GenericModel> deploymentStyleList,
            List<GenericModel> targetRangeList,
            List<GenericModelWithRelation> targetRangeTypesList,
            List<GenericModel> componentTypesList,
            List<GenericModel> algorithmTypesList,
            List<GenericModel> contextTypesList,
            List<GenericModel> contextEnvironmentsList,
            List<GenericModelWithRelation> contextIndustriesSchoolsList,
            List<GenericModel> contextIndustrySchoolTypesList,
            List<GenericModel> contextLearnerRolesList,
            List<GenericModel> contextInstructorRolesList,
            List<GenericModel> contextLearningTopicsList,
            List<GenericModelWithRelation> contextLearningSubTopicsList,
            List<GenericModel> contextPedagogicalParadigmList,
            List<GenericModel> contextLearningGoalsTypesList,
            List<GenericModelWithRelation> contextLearningGoalsList,
            List<GenericModelWithRelation> contextSoftSkillList) {
        
        this.ageList = ageList;
        this.availabilityList = availabilityList;
        this.genreList = genreList;
        this.learningCurveList = learningCurveList;
        this.marketList = marketList;
        this.publisherList = publisherList;
        this.seriousGameList = seriousGameList;
        this.statusList = statusList;
        this.contributersMessagesList = contributersMessageses;
        this.currentUser = currentUser;
        this.effectiveLearningTimeList = effectiveLearningTimeList;
        this.gamePlatformList = gamePlatformList;
        this.bandwidthList = bandwidthList;
        this.inputDeviceRequiredList = inputDeviceRequiredList;
        this.deploymentStyleList = deploymentStyleList;
        this.targetRangeList = targetRangeList;
        this.targetRangeTypesList = targetRangeTypesList;
        this.componentTypesList = componentTypesList;
        this.algorithmTypesList = algorithmTypesList;
        
        this.contextTypesList = contextTypesList;
        this.contextEnvironmentsList = contextEnvironmentsList;
        this.contextIndustriesSchoolsList = contextIndustriesSchoolsList;
        this.contextIndustrySchoolTypesList = contextIndustrySchoolTypesList;
        this.contextLearnerRolesList = contextLearnerRolesList;
        this.contextInstructorRolesList = contextInstructorRolesList;
        this.contextLearningTopicsList = contextLearningTopicsList;
        this.contextLearningSubTopicsList = contextLearningSubTopicsList;
        this.contextPedagogicalParadigmList = contextPedagogicalParadigmList;
        this.contextLearningGoalsTypesList = contextLearningGoalsTypesList;
        this.contextLearningGoalsList = contextLearningGoalsList;
        this.contextSoftSkillList = contextSoftSkillList;
        
	    this.contextPedagogicalParadigmListForComponent = new ArrayList<GenericModel>();
	    for (GenericModel model : this.contextPedagogicalParadigmList) {
		    this.contextPedagogicalParadigmListForComponent.add(model);
	    }

//	    GenericModel other = null;
//	    for (GenericModel model : this.contextPedagogicalParadigmList) {
//		    if (model.getName().equals("Other")) {
//			    other = model;
//			    continue;
//		    }
//		    this.contextPedagogicalParadigmListForComponent.add(model);
//        }
//        Collections.sort(this.contextPedagogicalParadigmListForComponent, new Comparator<GenericModel>() {
//
//            @Override
//            public int compare(GenericModel o1, GenericModel o2) {
//		    return o1.getName().compareTo(o2.getName());
//            }
//	    });
//	    this.contextPedagogicalParadigmListForComponent.add(other);

        
        years = new ArrayList<Integer>();
        int startYear = (new Date()).getYear() + 1900;
        for (int i = 0; i < 20; i++) {
            years.add(startYear-i);      
        }
    }
    
    

    /**
     * @return the ageList
     */
    public List<GenericModel> getAgeList() {
        return ageList;
    }

    /**
     * @return the availabilityList
     */
    public List<GenericModel> getAvailabilityList() {
        return availabilityList;
    }

    /**
     * @return the genreList
     */
    public List<GenericModel> getGenreList() {
        return genreList;
    }

    /**
     * @return the learningCurveList
     */
    public List<GenericModel> getLearningCurveList() {
        return learningCurveList;
    }

    /**
     * @return the marketList
     */
    public List<GenericModel> getMarketList() {
        return marketList;
    }

    /**
     * @return the publisherList
     */
    public List<GenericModel> getPublisherList() {
        return publisherList;
    }

    /**
     * @return the seriousGameList
     */
    public List<SeriousGame> getSeriousGameList() {
        return seriousGameList;
    }

    /**
     * @return the statusList
     */
    public List<GenericModel> getStatusList() {
        return statusList;
    }

    /**
     * @return the years
     */
    public List<Integer> getYears() {
        return years;
    }

    /**
     * @param publisherList the publisherList to set
     */
    public void setPublisherList(List<GenericModel> publisherList) {
        this.publisherList = publisherList;
    }

    /**
     * @param availabilityList the availabilityList to set
     */
    public void setAvailabilityList(List<GenericModel> availabilityList) {
        this.availabilityList = availabilityList;
    }

    /**
     * @param statusList the statusList to set
     */
    public void setStatusList(List<GenericModel> statusList) {
        this.statusList = statusList;
    }

    /**
     * @param seriousGameList the seriousGameList to set
     */
    public void setSeriousGameList(List<SeriousGame> seriousGameList) {
        this.seriousGameList = seriousGameList;
    }

    /**
     * @param genreList the genreList to set
     */
    public void setGenreList(List<GenericModel> genreList) {
        this.genreList = genreList;
    }

    /**
     * @param marketList the marketList to set
     */
    public void setMarketList(List<GenericModel> marketList) {
        this.marketList = marketList;
    }

    /**
     * @return the ContributersMessagesList
     */
    public List<ContributersMessages> getContributersMessagesList() {
        return contributersMessagesList;
    }
    

    /**
     * @param ContributersMessagesList the ContributersMessagesList to set
     */
    public void setContributersMessagesList(List<ContributersMessages> ContributersMessagesList) {
        this.contributersMessagesList = ContributersMessagesList;
    }

    /**
     * @return the current
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * @param current the current to set
     */
    public void setCurrentUser(User current) {
        this.currentUser = current;
    }
    public void resetOthers()
    {
        for (GenericModel model : marketList) {
            if (((String) model.get(ModelTag.name.toString())).startsWith(Utilities.otherString + " (")) {
                model.set(ModelTag.name.toString(), Utilities.otherString);
                break;
            }
        }
        for (GenericModel model : genreList) {
            if (((String) model.get(ModelTag.name.toString())).startsWith(Utilities.otherString + " (")) {
                model.set(ModelTag.name.toString(), Utilities.otherString);
                break;
            }
        }
        for (GenericModel model : statusList) {
            if ( model.getName().startsWith(Utilities.otherString + " (")) {
                model.set(ModelTag.name.toString(), Utilities.otherString);
                break;
            }
        }
        for (GenericModel model : gamePlatformList) {
            if ( model.getName().startsWith(Utilities.otherString + " (")) {
                model.set(ModelTag.name.toString(), Utilities.otherString);
                break;
            }
        }
        for (GenericModel model : inputDeviceRequiredList) {
            if ( model.getName().startsWith(Utilities.otherString + " (")) {
                model.set(ModelTag.name.toString(), Utilities.otherString);
                break;
            }
        }
    }

    /**
     * @return the effectiveLearningTimeList
     */
    public List<GenericModel> getEffectiveLearningTimeList() {
        return effectiveLearningTimeList;
    }

    /**
     * @return the bandwidthList
     */
    public List<GenericModel> getBandwidthList() {
        return bandwidthList;
    }

    /**
     * @return the deploymentStyleList
     */
    public List<GenericModel> getDeploymentStyleList() {
        return deploymentStyleList;
    }

    /**
     * @return the gamePlatformList
     */
    public List<GenericModel> getGamePlatformList() {
        return gamePlatformList;
    }

    /**
     * @return the inputDeviceRequiredList
     */
    public List<GenericModel> getInputDeviceRequiredList() {
        return inputDeviceRequiredList;
    }

    /**
     * @return the targetRangeList
     */
    public List<GenericModel> getTargetRangeList() {
        return targetRangeList;
    }
    
    /**
     * @return the targetRangeListsType
     */
    public List<GenericModelWithRelation> getTargetRangeTypesList() {
        return targetRangeTypesList;
    }
    public List<GenericModel> getComponentTypesList() {
        return componentTypesList;
    }
    public List<GenericModel> getAlgorithmTypesList() {
        return algorithmTypesList;
    }
    
    public List<GenericModelWithRelation> getRelationSubList(List<GenericModelWithRelation> list, int relationId)
    {
        List<GenericModelWithRelation> retval = new ArrayList<GenericModelWithRelation>();
        for (GenericModelWithRelation model : list) {
            if(model.getRelationId().intValue() == relationId)
                retval.add(model);
        }
        return retval;
    }

    /**
     * @return the contextEnvironmentsList
     */
    public List<GenericModel> getContextEnvironmentsList() {
        return contextEnvironmentsList;
    }

    /**
     * @return the contextIndustriesSchoolsList
     */
    public List<GenericModelWithRelation> getContextIndustriesSchoolsList() {
        return contextIndustriesSchoolsList;
    }

    /**
     * @return the contextIndustrySchoolTypesList
     */
    public List<GenericModel> getContextIndustrySchoolTypesList() {
        return contextIndustrySchoolTypesList;
    }

    /**
     * @return the contextInstructorRolesList
     */
    public List<GenericModel> getContextInstructorRolesList() {
        return contextInstructorRolesList;
    }

    /**
     * @return the contextLearnerRolesList
     */
    public List<GenericModel> getContextLearnerRolesList() {
        return contextLearnerRolesList;
    }

    /**
     * @return the contextLearningSubTopicsList
     */
    public List<GenericModelWithRelation> getContextLearningSubTopicsList() {
        return contextLearningSubTopicsList;
    }

    /**
     * @return the contextLearningTopicsList
     */
    public List<GenericModel> getContextLearningTopicsList() {
        return contextLearningTopicsList;
    }

    /**
     * @return the contextSoftSkillList
     */
    public List<GenericModelWithRelation> getContextSoftSkillList() {
        return contextSoftSkillList;
    }

    /**
     * @return the contextTypesList
     */
    public List<GenericModel> getContextTypesList() {
        return contextTypesList;
    }

    /**
     * @param targetRangeList the targetRangeList to set
     */
    public void setTargetRangeList(List<GenericModel> targetRangeList) {
        this.targetRangeList = targetRangeList;
    }

    /**
     * @return the contextLearningGoalsList
     */
    public List<GenericModelWithRelation> getContextLearningGoalsList() {
        return contextLearningGoalsList;
    }

    /**
     * @return the contextLearningGoalsTypesList
     */
    public List<GenericModel> getContextLearningGoalsTypesList() {
        return contextLearningGoalsTypesList;
    }

    /**
     * @return the contextPedagogicalParadigmList
     */
    public List<GenericModel> getContextPedagogicalParadigmList() {
	    return contextPedagogicalParadigmList;
	}

	/**
	 * @return
	 */
    public List<GenericModel> getContextPedagogicalParadigmListForComponent() {
	    return this.contextPedagogicalParadigmListForComponent;
    }

    /**
     * @param contextPedagogicalParadigmList the contextPedagogicalParadigmList to set
     */
    public void setContextPedagogicalParadigmList(List<GenericModel> contextPedagogicalParadigmList) {
        this.contextPedagogicalParadigmList = contextPedagogicalParadigmList;
    }
    
    
                
}
