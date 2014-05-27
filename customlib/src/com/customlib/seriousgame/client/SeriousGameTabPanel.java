/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client;

import com.customlib.seriousgame.client.forms.*;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.interfaces.SeriousGameDataInteractionForm;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TabPanelEvent;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;

/**
 *
 * @author nertil
 */
public class SeriousGameTabPanel extends TabPanel {

	private MainEntryPointInterface entrypoint = null;
	private GeneralInfoForm generalForm;
	private RelationForm relationForm;
	private TechnologyForm technologyForm;
	private LearningEnvironmentForm learningEnvironmentForm;
	private ArchitectureForm architectureForm;
	private AlgorithmForm algorithmForm;
	private ContextAndAnalysisForm contextAndAnalysisForm;
	private MyExtendedClasses.SeriouGameFormTabItem generalTab;
	private MyExtendedClasses.SeriouGameFormTabItem relationTab;
	private MyExtendedClasses.SeriouGameFormTabItem technologyTab;
	private MyExtendedClasses.SeriouGameFormTabItem learningEnvironmentTab;
	private MyExtendedClasses.SeriouGameFormTabItem architectureTab;
	private MyExtendedClasses.SeriouGameFormTabItem contextAndAnalysisTab;

	public SeriousGameTabPanel(MainEntryPointInterface entrypoint) {
		this.entrypoint = entrypoint;
		generalForm = new GeneralInfoForm(entrypoint);
		relationForm = new RelationForm(entrypoint);
		technologyForm = new TechnologyForm(entrypoint);
		learningEnvironmentForm = new LearningEnvironmentForm(entrypoint);
		architectureForm = new ArchitectureForm(entrypoint);
		contextAndAnalysisForm = new ContextAndAnalysisForm(entrypoint);
	}

	public void init() {
		this.setResizeTabs(true);
		this.setStyleName("serius-game-tab-panel");
		setBodyBorder(false);
		setBorderStyle(false);
		setTabScroll(true);

		generalTab = new MyExtendedClasses.SeriouGameFormTabItem(Constants.Strings.sg_general(), generalForm);
		relationTab = new MyExtendedClasses.SeriouGameFormTabItem(Constants.Strings.relations(), relationForm);
		technologyTab = new MyExtendedClasses.SeriouGameFormTabItem(Constants.Strings.tec(), getTechnologyForm());
		learningEnvironmentTab = new MyExtendedClasses.SeriouGameFormTabItem(Constants.Strings.le(), getLearningEnvironmentForm());
		architectureTab = new MyExtendedClasses.SeriouGameFormTabItem(Constants.Strings.arch(), getArchitectureForm());
		contextAndAnalysisTab = new MyExtendedClasses.SeriouGameFormTabItem(Constants.Strings.contextAndAnalysis(), getContextAndAnalysisForm());

		this.add(generalTab);
		this.add(relationTab);
		this.add(technologyTab);
		this.add(learningEnvironmentTab);
		this.add(architectureTab);
		this.add(contextAndAnalysisTab);

		this.addListener(Events.Select, new Listener<TabPanelEvent>() {
			@Override
			public void handleEvent(TabPanelEvent be) {
				SeriousGameDataInteractionForm form = (SeriousGameDataInteractionForm) be.getItem().getWidget(0);
				if (form != null) {
					form.setChanges(true);
				}
			}
		});

	}

	public void SetSelection(int tabIndex) {
		switch (tabIndex) {
			case 0:
				setSelection(generalTab);
				break;
			case 1:
				setSelection(relationTab);
				break;
			case 2:
				setSelection(technologyTab);
				break;
			case 3:
				setSelection(learningEnvironmentTab);
				break;
			default:
				setSelection(generalTab);
				break;
		}
	}

	public GeneralInfoForm getGeneralForm() {
		return generalForm;
	}

	public RelationForm getRelationPanel() {
		return relationForm;
	}

	public ArchitectureForm getArchitectureForm() {
		return architectureForm;
	}

	public AlgorithmForm getAlgorithmForm() {
		return algorithmForm;
	}

	public ContextAndAnalysisForm getContextAndAnalysisForm() {
		return contextAndAnalysisForm;
	}

	public LearningEnvironmentForm getLearningEnvironmentForm() {
		return learningEnvironmentForm;
	}

	public TechnologyForm getTechnologyForm() {
		return technologyForm;
	}

	public void save() {
		for (TabItem tabItem : this.getItems()) {
			SeriousGameDataInteractionForm form = (SeriousGameDataInteractionForm) tabItem.getWidget(0);
			if (form != null) {
				form.setChanges(false);
			}
		}
		SeriousGameDataInteractionForm form = (SeriousGameDataInteractionForm) this.getSelectedItem().getWidget(0);
		if (form != null) {
			form.setChanges(true);
		}
	}
}
