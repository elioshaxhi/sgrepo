/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client.fieldsetsContext;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.Context;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.GenericModelWithRelation;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import java.util.List;

/**
 *
 * @author nomecognome
 */
public class ContextFieldSet extends FieldSet {

    private MainEntryPointInterface mainEntryPointInterface;
    private FormData formData;
    private List<GenericModelWithRelation> industrySubType;
    private MyExtendedClasses.SeriouGameLabelField componentName;
    private MyExtendedClasses.SeriouGameLabelField componentDescription;
    private MyExtendedClasses.SeriouGameLabelField typeOfContext;
    private MyExtendedClasses.SeriouGameLabelField environment;
    private MyExtendedClasses.SeriouGameLabelField industrySchoolType;
    private MyExtendedClasses.SeriouGameLabelField industrySchoolSubType;
    private MyExtendedClasses.SeriouGameLabelField industrySchoolDescription;
    private MyExtendedClasses.SeriouGameLabelField learn_roleType;
    private MyExtendedClasses.SeriouGameLabelField learn_roleDescription;
    private MyExtendedClasses.SeriouGameLabelField teacher_roleType;
    private MyExtendedClasses.SeriouGameLabelField teacher_roleDescription;
    private GenericContextFieldSet learnTopic;
    private GenericContextFieldSet pedagogicalParadigm;
    private GenericContextFieldSet learningGoals;

    public ContextFieldSet(final MainEntryPointInterface mainEntryPointInterface) {
        this.mainEntryPointInterface = mainEntryPointInterface;
        FormLayout layout = new FormLayout(LabelAlign.LEFT);
        layout.setLabelWidth(Utilities.formLabelWidth);
        setLayout(layout);

    }

    public void setData(String nomeComponent, final Context context) {
        formData = new FormData("-30");

        learnTopic = new GenericContextFieldSet(this.mainEntryPointInterface, Constants.Strings.context_learningTopics(), Constants.Strings.topic(), (List<GenericModel>) context.get(Context.tagLearningTopics));
        pedagogicalParadigm = new GenericContextFieldSet(mainEntryPointInterface, Constants.Strings.context_pedagogicalApproaches(), Constants.Strings.pedagogical(), (List<GenericModel>) context.get(Context.tagPedagogicalParadigm));
        learningGoals = new GenericContextFieldSet(mainEntryPointInterface, Constants.Strings.context_learningGoals(), Constants.Strings.goals(), (List<GenericModel>) context.get(Context.tagLearningGoals));

        setHeading(nomeComponent);
        setCollapsible(true);
        setExpanded(false);

        componentName = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.name());
        String componentValueName = (String) context.getName();

        setFieldValue(componentName, componentValueName);
        this.add(componentName, new FormData("-15"));

        componentDescription = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.description());
        String componentValueDescription = (String) context.getDescription();
        setFieldValue(componentDescription, componentValueDescription);
        this.add(componentDescription, new FormData("-15"));

        typeOfContext = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.context_type());
        GenericModel componentValueSpecificSG = Utilities.setStringModel(context, Context.tagTypeID, this.mainEntryPointInterface.getDatabase().getContextTypesList());
        String valType = null;
        if (componentValueSpecificSG != null) {
            valType = componentValueSpecificSG.getName();
        }
        setFieldValue(typeOfContext, valType);
        this.add(typeOfContext, new FormData("-15"));

        environment = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.context_environment());
        GenericModel environmentValue = Utilities.setStringModel(context, Context.tagEnvironmentID, this.mainEntryPointInterface.getDatabase().getContextEnvironmentsList());
        String valEnv = null;
        if (environmentValue != null) {
            valEnv = environmentValue.getName();
        }
        setFieldValue(environment, valEnv);
        if (componentValueName != null || componentValueDescription != null || valType != null || valEnv != null); else {
            this.setVisible(false);
        }
        this.add(environment, new FormData("-15"));
//innizio industry
        FieldSet industry = new FieldSet();
        industry.setLayout(new FormLayout());
        industry.setHeading(Constants.Strings.context_industrySchool());
        industry.setCollapsible(true);

        industrySchoolType = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.type());
        GenericModel industrySchoolTypeValue = Utilities.setStringModel(context, Context.tagIndustrySchoolID, this.mainEntryPointInterface.getDatabase().getContextIndustrySchoolTypesList());
        String val = null;
        if (industrySchoolTypeValue != null) {
            val = industrySchoolTypeValue.getName();
        }
        setFieldValue(industrySchoolType, val);
        industry.add(industrySchoolType, new FormData("-3"));

        String valSub = null;
        industrySchoolSubType = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.subtype());
        if (industrySchoolTypeValue != null) {
            industrySubType = this.mainEntryPointInterface.getDatabase().getRelationSubList(this.mainEntryPointInterface.getDatabase().getContextIndustriesSchoolsList(), industrySchoolTypeValue.getId());
            GenericModelWithRelation industrySchoolSubTypeValue = Utilities.setStringModel2(context, Context.tagIndustrySchoolSubTypeID, industrySubType);

            valSub = industrySchoolSubTypeValue.getName();
        }
        setFieldValue(industrySchoolSubType, valSub);
        industry.add(industrySchoolSubType, new FormData("-3"));

        industrySchoolDescription = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.description());
        String industrySchoolDescriptionValue = (String) context.get(Context.tagIndustrySchoolDescription);
        setFieldValue(industrySchoolDescription, industrySchoolDescriptionValue);
        industry.add(industrySchoolDescription, new FormData("-3"));
        
        if (industrySchoolDescriptionValue != null && industrySchoolDescriptionValue.length() == 0) {
            industrySchoolDescriptionValue = null;
        }

        if (val != null || valSub != null || (industrySchoolDescriptionValue != null)) {
            industry.setExpanded(true);

        } else {
            industry.setExpanded(false);
            industry.setEnabled(false);
        }
//learn role
        FieldSet learn_role = new FieldSet();
        learn_role.setLayout(new FormLayout());
        learn_role.setHeading(Constants.Strings.context_learnerRole());
        learn_role.setCollapsible(true);

        learn_roleType = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.type());
        List<GenericModel> learnlist = this.mainEntryPointInterface.getDatabase().getContextLearnerRolesList();
        GenericModel learn_roleTypeValue = Utilities.setStringModel(context, Context.tagLearnerRoleID, learnlist);
        String val_learn = null;
        if (learn_roleTypeValue != null) {
            val_learn = learn_roleTypeValue.getName();

        }
        setFieldValue(learn_roleType, val_learn);
        learn_role.add(learn_roleType, new FormData("-3"));

        learn_roleDescription = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.description());
        String learn_roleDescriptionValue = (context.get(Context.tagLearnerRoleDescription));
        setFieldValue(learn_roleDescription, learn_roleDescriptionValue);
        learn_role.add(learn_roleDescription, new FormData("-3"));
        if (val_learn != null || learn_roleDescriptionValue != null) {
            learn_role.setExpanded(true);
        } else {
            learn_role.setExpanded(false);
            learn_role.setEnabled(false);
        }
//fine learn role
//teacher role		
        FieldSet teacher_role = new FieldSet();
        teacher_role.setLayout(new FormLayout());
        teacher_role.setHeading(Constants.Strings.context_teacherRole());
        teacher_role.setCollapsible(true);

        teacher_roleType = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.type());
        List<GenericModel> teacherlist = this.mainEntryPointInterface.getDatabase().getContextInstructorRolesList();
        GenericModel teacher_roleTypeValue = Utilities.setStringModel(context, Context.tagInstructorRoleID, teacherlist);
        String val_teacher = null;
        if (teacher_roleTypeValue != null) {
            val_teacher = teacher_roleTypeValue.getName();

        }
        setFieldValue(teacher_roleType, val_teacher);
        teacher_role.add(teacher_roleType, new FormData("-3"));

        teacher_roleDescription = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.description());
        String teacher_roleDescriptionValue = (context.get(Context.tagInstructorRoleDescription));
        setFieldValue(teacher_roleDescription, teacher_roleDescriptionValue);
        teacher_role.add(teacher_roleDescription, new FormData("-3"));

        if (val_teacher != null || teacher_roleDescriptionValue != null) {
            teacher_role.setExpanded(true);
        } else {
            teacher_role.setExpanded(false);
            teacher_role.setEnabled(false);
        }

//fine teacher 		
        this.add(industry, new FormData("-15"));
        this.add(learn_role, new FormData("-15"));
        this.add(teacher_role, new FormData("-15"));

        this.add(learnTopic, new FormData("-15"));
        this.add(pedagogicalParadigm, new FormData("-15"));
        this.add(learningGoals, new FormData("-15"));
    }

    private void setFieldValue(Field field, String value) {
        if (value != null) {
            value = value.replaceAll("\n", "<br>");
            field.setVisible(true);
            field.setValue(value);
        } else {
            field.setVisible(false);
        }
    }
}
