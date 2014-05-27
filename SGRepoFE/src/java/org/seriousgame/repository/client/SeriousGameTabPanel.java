/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.widget.TabPanel;
import org.seriousgame.repository.client.form.GeneralInfoFromFe;
import org.seriousgame.repository.client.form.AnalysisFe;
import org.seriousgame.repository.client.form.ArchitectureFormFe;
import org.seriousgame.repository.client.form.ContextFe;
import org.seriousgame.repository.client.form.LearningEnvironmentFormFe;

/**
 *
 * @author Nertil
 */
public class SeriousGameTabPanel extends TabPanel {

	private MainEntryPointInterface entrypoint = null;
	private GeneralInfoFromFe generalInfoFormFe = null;
	private LearningEnvironmentFormFe learningEnvironmentFormFe = null;
	private ArchitectureFormFe architectureFormFe = null;
	private ContextFe contextFe;
	private AnalysisFe analysisFe;
	private MyExtendedClasses.SeriouGameFormTabItem generalTab;
	private MyExtendedClasses.SeriouGameFormTabItem relationTab;
	private MyExtendedClasses.SeriouGameFormTabItem technologyTab;
	private MyExtendedClasses.SeriouGameFormTabItem learningEnvironmentTab;
	private MyExtendedClasses.SeriouGameFormTabItem architectureTab;
	private MyExtendedClasses.SeriouGameFormTabItem contextTab;
	private MyExtendedClasses.SeriouGameFormTabItem analysisTab;

	public SeriousGameTabPanel(MainEntryPoint entrypoint) {
		this.entrypoint = entrypoint;

		this.setResizeTabs(true);
		this.setTabWidth(145);
		this.setStyleName("serious-game-tab-panel");
		setBodyBorder(false);
		setBorderStyle(false);
		setTabScroll(true);
		generalInfoFormFe = new GeneralInfoFromFe(entrypoint);
		learningEnvironmentFormFe = new LearningEnvironmentFormFe(entrypoint);
		architectureFormFe = new ArchitectureFormFe(entrypoint);
		contextFe = new ContextFe(entrypoint);
		analysisFe = new AnalysisFe(entrypoint);

		generalTab = new MyExtendedClasses.SeriouGameFormTabItem(Constants.Strings.sg_general(), generalInfoFormFe);
		learningEnvironmentTab = new MyExtendedClasses.SeriouGameFormTabItem(Constants.Strings.le(), learningEnvironmentFormFe);
		architectureTab = new MyExtendedClasses.SeriouGameFormTabItem(Constants.Strings.arch(), architectureFormFe);
		contextTab = new MyExtendedClasses.SeriouGameFormTabItem(Constants.Strings.contexts(), contextFe);
		analysisTab = new MyExtendedClasses.SeriouGameFormTabItem(Constants.Strings.evaluation(), analysisFe);
		this.add(generalTab);
//        this.add(relationTab);
//        this.add(technologyTab);
		this.add(learningEnvironmentTab);
		this.add(architectureTab);
		this.add(contextTab);
		this.add(analysisTab);

	}

	public void SetData(SeriousGame sg) {
		generalInfoFormFe.setData(sg);
		learningEnvironmentFormFe.setData(sg);
             		architectureFormFe.setRecord(sg);
		contextFe.setRecord(sg);
		analysisFe.setRecord(sg);


	}
}
