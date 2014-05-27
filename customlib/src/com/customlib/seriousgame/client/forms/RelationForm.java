/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.customlib.seriousgame.client.forms;


import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.DbTables;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.grids.GenericGrid;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.interfaces.SeriousGameDataInteractionForm;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.ModelTag;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.*;
import java.util.List;


/**
 *
 * @author nertil
 */
public class RelationForm extends FormPanel implements SeriousGameDataInteractionForm
{
    private MainEntryPointInterface entrypoint = null;
    private GenericGrid ages;
    private GenericGrid genres;
    private GenericGrid markets;
    
    private ComboBox<GenericModel> learningCurve;
    private MyExtendedClasses.CostomTextArea notesLearningCurve;
    private ComboBox<GenericModel> effectiveLearningtime;    
    private MyExtendedClasses.CostomTextArea notesEffectiveLearningtime;
    
    private boolean changes = false;

    public RelationForm(MainEntryPointInterface entrypoint)
    {
        this.entrypoint = entrypoint;
        
        ages = new GenericGrid(entrypoint, Constants.Strings.sg_ages(), entrypoint.getDatabase().getAgeList());
        genres = new GenericGrid(entrypoint, Constants.Strings.sg_genres(), entrypoint.getDatabase().getGenreList());
        markets = new GenericGrid(entrypoint, Constants.Strings.sg_markets(), entrypoint.getDatabase().getMarketList());
    
        setBodyBorder(false);
        setHeaderVisible(false);
        setButtonAlign(HorizontalAlignment.CENTER);
        setLayout(new FormLayout());
        setScrollMode(Style.Scroll.AUTO);
        setPadding(5);
        
        FormData formData = new FormData("-15");
        AnchorData anchoreData = new AnchorData("100%", new Margins(0, 15, 5, 0));
        
        add(ages, anchoreData);
        add(genres, anchoreData);
        add(markets, anchoreData);
        
        LayoutContainer c = new LayoutContainer();
        c.setLayout(new ColumnLayout());

        LayoutContainer l = new LayoutContainer();
        l.setStyleAttribute("paddingRight", "10px");
        l.setLayout(new FormLayout());
        
        LayoutContainer r = new LayoutContainer();
        r.setStyleAttribute("paddingLeft", "10px");
        FormLayout layout = new FormLayout();
        layout.setLabelWidth(50);
        r.setLayout(layout);
        
        ListStore<GenericModel> learningCurveStore = new ListStore<GenericModel>();  
        learningCurveStore.add(entrypoint.getDatabase().getLearningCurveList());
        
        learningCurve = new ComboBox<GenericModel>(); 
        learningCurve.setForceSelection(true);
        learningCurve.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
        learningCurve.setFieldLabel(Constants.Strings.sg_learningCurve());  
        learningCurve.setDisplayField(ModelTag.name.toString());  
        learningCurve.setTriggerAction(ComboBox.TriggerAction.ALL);  
        learningCurve.setStore(learningCurveStore);
        learningCurve.setToolTip(Constants.Strings.tt_learningCurve());
        
        notesLearningCurve = new MyExtendedClasses.CostomTextArea(Constants.Strings.notes(), null);  
        
                
        l.add(learningCurve, new FormData("100%"));
        r.add(notesLearningCurve, new FormData("-20"));
        c.add(l, new ColumnData(.5));
        c.add(r, new ColumnData(.5));        
        add(c, formData);
        
        c = new LayoutContainer();
        c.setLayout(new ColumnLayout());

        l = new LayoutContainer();
        l.setStyleAttribute("paddingRight", "10px");
        l.setLayout(new FormLayout());
        
        r = new LayoutContainer();
        r.setStyleAttribute("paddingLeft", "10px");
        layout = new FormLayout();
        layout.setLabelWidth(50);
        r.setLayout(layout);
        
        ListStore<GenericModel> effectiveLearningTimeStore = new ListStore<GenericModel>();  
        effectiveLearningTimeStore.add(entrypoint.getDatabase().getEffectiveLearningTimeList());
        
        effectiveLearningtime = new ComboBox<GenericModel>(); 
        effectiveLearningtime.setForceSelection(true);
        effectiveLearningtime.getMessages().setBlankText(Constants.Strings.cb_selectionMessage());
        effectiveLearningtime.setFieldLabel(Constants.Strings.sg_EffectiveLearningTime());  
        effectiveLearningtime.setDisplayField(ModelTag.name.toString());  
        effectiveLearningtime.setTriggerAction(ComboBox.TriggerAction.ALL);  
        effectiveLearningtime.setStore(effectiveLearningTimeStore);
        effectiveLearningtime.setToolTip(Constants.Strings.tt_effectiveLearningTime());
        
        notesEffectiveLearningtime = new MyExtendedClasses.CostomTextArea(Constants.Strings.notes(), null);
        
        l.add(effectiveLearningtime, new FormData("100%"));
        r.add(notesEffectiveLearningtime, new FormData("-20"));
        
        c.add(l, new ColumnData(.5));
        c.add(r, new ColumnData(.5));       
        add(c, formData);

    }
    @Override
    public void updateRecord(SeriousGame record)
    {
        record.set(SeriousGame.TagAges, ages.getData());
        record.set(SeriousGame.TagGenres, genres.getData());
        record.set(SeriousGame.TagMarkets, markets.getData());
        
        Utilities.updateOtherList(record, DbTables.game_genres.toString(), genres.getData());
        Utilities.updateOtherList(record, DbTables.markets.toString(), markets.getData());
        
        if (learningCurve.getValue() != null) {
            record.set(SeriousGame.TagLearningCurveID, learningCurve.getValue().getId());
        } else {
            record.set(SeriousGame.TagLearningCurveID, null);
        }
        if (effectiveLearningtime.getValue() != null) {
            record.set(SeriousGame.TagEffectiveLearningTimeID, effectiveLearningtime.getValue().getId());
        } else {
            record.set(SeriousGame.TagEffectiveLearningTimeID, null);
        }
        record.set(SeriousGame.TagLearningCurveNotes, notesLearningCurve.getValue());
        record.set(SeriousGame.TagEffectiveLearningTimeNotes, notesEffectiveLearningtime.getValue());
        record.set(SeriousGame.TagRelationsChanges, hasChanges());
    }
    @Override
    public void setRecord(SeriousGame record)
    {
        List<GenericModel> otherList = (List<GenericModel>)record.get(SeriousGame.TagOthers);
        List<GenericModel> genresSeletion = (List<GenericModel>)record.get(SeriousGame.TagGenres);        
        List<GenericModel> marketsSeletion = (List<GenericModel>)record.get(SeriousGame.TagMarkets);
        Utilities.checkOtherList(genresSeletion, entrypoint.getDatabase().getGenreList(), otherList, DbTables.game_genres.toString());
        Utilities.checkOtherList(marketsSeletion, entrypoint.getDatabase().getMarketList(), otherList, DbTables.markets.toString());
        
        ages.setData((List<GenericModel>)record.get(SeriousGame.TagAges));
        genres.setData(genresSeletion);
        markets.setData(marketsSeletion);
        
        Integer learningCourveId = (Integer)record.get(SeriousGame.TagLearningCurveID);
        if(learningCourveId != null && learningCourveId.intValue() > 0)
            Utilities.setComboBoxSelection(new GenericModel(learningCourveId, "", ""), ModelTag.id.toString(), learningCurve);
        
        Integer effectiveLearningTime = (Integer)record.get(SeriousGame.TagEffectiveLearningTimeID);
        if(effectiveLearningTime != null && effectiveLearningTime.intValue() > 0)
            Utilities.setComboBoxSelection(new GenericModel(effectiveLearningTime, "", ""), ModelTag.id.toString(), effectiveLearningtime);
        
        notesLearningCurve.setValue((String)record.get(SeriousGame.TagLearningCurveNotes)); 
        notesEffectiveLearningtime.setValue((String)record.get(SeriousGame.TagEffectiveLearningTimeNotes)); 
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