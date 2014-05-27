/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.forms;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.ScoreFieldSet;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.grids.ContextGrid;
import com.customlib.seriousgame.client.grids.GenericGrid;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.interfaces.SeriousGameDataInteractionForm;
import com.customlib.seriousgame.client.models.Analysis;
import com.customlib.seriousgame.client.models.ContextAndAnalysis;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.AnchorData;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NERTIL
 */
public class ContextAndAnalysisForm extends FormPanel implements SeriousGameDataInteractionForm {

    private MainEntryPointInterface entrypoint;
    
    private ContextGrid context;
    
    private MyExtendedClasses.CostomTextArea methodology;
    
    private GenericGrid referenceToContext;
    private ScoreFieldSet effectiveness;
    private ScoreFieldSet efficiency;
    private ScoreFieldSet usability;
    private ScoreFieldSet diffusion;
    private ScoreFieldSet feedback;
    private ScoreFieldSet exploitability;
    private ScoreFieldSet reusability;
    private ScoreFieldSet capabilityOfMotivating;
    private ScoreFieldSet capabilityOfEngaging;
    
    private boolean changes = false;
    
    public ContextAndAnalysisForm(final MainEntryPointInterface entrypoint) {
        this.entrypoint = entrypoint;
        context = new ContextGrid(entrypoint, new ArrayList<GenericModel>());
        referenceToContext = new GenericGrid(entrypoint, Constants.Strings.analysis_referenceOfContext(), new ArrayList<GenericModel>());
    
        setBodyBorder(false);
        setHeaderVisible(false);
        setButtonAlign(Style.HorizontalAlignment.CENTER);
        setLayout(new FormLayout());
        setScrollMode(Style.Scroll.AUTO);
        setPadding(5);
        
        FormData formData = new FormData("-15");
        AnchorData anchoreData = new AnchorData("100%", new Margins(0, 15, 10, 0));
        AnchorData fsData = new AnchorData("100%", new Margins(0, 0, 10, 0));
        
        FieldSet fsAnalysis = new FieldSet();
        fsAnalysis.setHeading(Constants.Strings.analysis());
//        fsAnalysis.setToolTip(Constants.Strings.tt_analysis());
        FormLayout l1 = new FormLayout();
        l1.setLabelWidth(110);
        fsAnalysis.setLayout(l1);
        
        fsAnalysis.add(referenceToContext, fsData);
        
        methodology = new MyExtendedClasses.CostomTextArea(Constants.Strings.analysis_methodology(), Constants.Strings.tt_analysis_methodology());
        fsAnalysis.add(methodology, fsData);
        
        effectiveness = new ScoreFieldSet(Constants.Strings.analysis_effectiveness(), 1, "");
        effectiveness.setToolTip(Constants.Strings.tt_analysis_effectiveness());
        fsAnalysis.add(effectiveness, fsData);
        
        efficiency = new ScoreFieldSet(Constants.Strings.analysis_efficiency(), 1, "");
        efficiency.setToolTip(Constants.Strings.tt_analysis_efficiency());
        fsAnalysis.add(efficiency, fsData);
        
        usability = new ScoreFieldSet(Constants.Strings.analysis_usability(), 1, "");
        usability.setToolTip(Constants.Strings.tt_analysis_usability());
        fsAnalysis.add(usability, fsData);
        
        diffusion = new ScoreFieldSet(Constants.Strings.analysis_diffusion(), 1, "");
        diffusion.setToolTip(Constants.Strings.tt_analysis_diffusion());
        fsAnalysis.add(diffusion, fsData);
        
        feedback = new ScoreFieldSet(Constants.Strings.analysis_feedback(), 1, "");
        fsAnalysis.add(feedback, fsData);
        
        exploitability = new ScoreFieldSet(Constants.Strings.analysis_exploitability(), 1, "");
        exploitability.setToolTip(Constants.Strings.tt_analysis_exploitability());
        fsAnalysis.add(exploitability, fsData);
        
        reusability = new ScoreFieldSet(Constants.Strings.analysis_reusability(), 1, "");
        fsAnalysis.add(reusability, fsData);
        
        capabilityOfMotivating = new ScoreFieldSet(Constants.Strings.analysis_capabilityOfMotivation(), 1, "");
        fsAnalysis.add(capabilityOfMotivating, fsData);
        
        capabilityOfEngaging = new ScoreFieldSet(Constants.Strings.analysis_capabilityOfEngaging(), 1, "");
        fsAnalysis.add(capabilityOfEngaging, fsData);
        
        add(context, anchoreData);
        add(fsAnalysis, anchoreData);
    }
    public void contextListChange()
    {
        referenceToContext.setSourceData(context.getData());
    }

    @Override
    public void updateRecord(SeriousGame record) {
        ContextAndAnalysis contextAndAnalysis = new ContextAndAnalysis(context.getData(),
                new Analysis(Utilities.getStringFromList(Utilities.getIDList(referenceToContext.getData())), methodology.getValue(),
                effectiveness.getScore(), effectiveness.getMotivation(), 
                efficiency.getScore(), efficiency.getMotivation(), 
                usability.getScore(), usability.getMotivation(), 
                diffusion.getScore(), diffusion.getMotivation(), 
                feedback.getScore(), feedback.getMotivation(), 
                exploitability.getScore(), exploitability.getMotivation(), 
                reusability.getScore(), reusability.getMotivation(), 
                capabilityOfMotivating.getScore(), capabilityOfMotivating.getMotivation(), 
                capabilityOfEngaging.getScore(), capabilityOfEngaging.getMotivation()),
                hasChanges());
        record.set(SeriousGame.TagContextAndAnalysi, contextAndAnalysis);
    }

    @Override
    public void setRecord(SeriousGame record) {
        ContextAndAnalysis contextAndAnalysis = record.get(SeriousGame.TagContextAndAnalysi);
        if(contextAndAnalysis == null)
            return;
        context.setData(contextAndAnalysis.getContexts());
        
        Analysis analysis = contextAndAnalysis.getAnalysis();
        if(analysis == null)
            return;
        List<Integer> contextIds = Utilities.getListFromString(analysis.getReferenceContext());
        List<GenericModel> referenceContext = new ArrayList<GenericModel>();
        if(this.context.getData() != null)
        {
            for (GenericModel c : this.context.getData()) {
                for (Integer i : contextIds) {
                    if(c.getId().intValue() == i.intValue())
                    {
                        referenceContext.add(c);
                    }
                }
            }
        }
//        referenceToContext.setSourceData(contextAndAnalysis.getContexts());
        referenceToContext.setData(referenceContext);
        methodology.setValue(analysis.getMethodology());
        
        effectiveness.setScore(analysis.getEffectiveness());
        effectiveness.setMotivation(analysis.getEffectivenessDescription());
        efficiency.setScore(analysis.getEfficiency());
        efficiency.setMotivation(analysis.getEfficiencyDescription());
        usability.setScore(analysis.getUsability());
        usability.setMotivation(analysis.getUsabilityDescription());
        diffusion.setScore(analysis.getDiffusion());
        diffusion.setMotivation(analysis.getDiffusionDescription());
        feedback.setScore(analysis.getFeedbackAssessmentSupport());
        feedback.setMotivation(analysis.getFeedbackAssessmentSupportDescription());
        exploitability.setScore(analysis.getExploitabilityLearningContext());
        exploitability.setMotivation(analysis.getExploitabilityLearningContextDescription());
        reusability.setScore(analysis.getReusabilityDifferentLearningContext());
        reusability.setMotivation(analysis.getReusabilityDifferentLearningContextDescription());
        capabilityOfMotivating.setScore(analysis.getCapabilityMotivatingUser());
        capabilityOfMotivating.setMotivation(analysis.getCapabilityMotivatingUserDescription());
        capabilityOfEngaging.setScore(analysis.getCapabilityEngagingUser());
        capabilityOfEngaging.setMotivation(analysis.getCapabilityEngagingUserDescription());
    }
    
    @Override
    public boolean hasChanges() {
        return this.changes;
    }

    @Override
    public void setChanges(boolean changes) {
        this.changes = changes;
    }
}
