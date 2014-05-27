/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client.fieldsetsContext;

import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.GenericModelWithRelation;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author nomecognome
 */
public class GenericContextFieldSet extends FieldSet {

	private MainEntryPointInterface mainEntryPointInterface;
	private List<GenericModelWithRelation> skill = null;
	private boolean flag = true;

	public GenericContextFieldSet(final MainEntryPointInterface mainEntryPointInterface, String nomeComponent, String oneOfThree, List<GenericModel> coda) {
		this.mainEntryPointInterface = mainEntryPointInterface;

		setLayout(new FormLayout());
		setHeading(nomeComponent);
		setCollapsible(true);
		setExpanded(false);
		if (coda.isEmpty()) {
			flag = false;
		}
		ListIterator iter = coda.listIterator();
		if (oneOfThree.equals("topic")) {
			while (iter.hasNext()) {
				GenericModel gm = (GenericModel) iter.next();


				ChieldrenContextFieldSet chieldrenTopicsFiledSet = new ChieldrenContextFieldSet(this.mainEntryPointInterface, this.mainEntryPointInterface.getDatabase().getContextLearningTopicsList(), this.mainEntryPointInterface.getDatabase().getContextLearningSubTopicsList(), skill);
				chieldrenTopicsFiledSet.setTopicData(gm);
				this.add(chieldrenTopicsFiledSet, new FormData("-30"));

			}
			if (!flag) {
				this.setEnabled(false);
			}

		} else if (oneOfThree.equals("pedagogical")) {
			while (iter.hasNext()) {
				GenericModel gm = (GenericModel) iter.next();


				ChieldrenContextFieldSet chieldrenPedagogicalFiledSet = new ChieldrenContextFieldSet(this.mainEntryPointInterface, this.mainEntryPointInterface.getDatabase().getContextPedagogicalParadigmList(), skill, skill);
				chieldrenPedagogicalFiledSet.setTopicData(gm);
				this.add(chieldrenPedagogicalFiledSet, new FormData("-30"));

			}
			if (!flag) {
				this.setEnabled(false);
			}

		} else if (oneOfThree.equals("goals")) {
			while (iter.hasNext()) {
				GenericModel gm = (GenericModel) iter.next();


				ChieldrenContextFieldSet chieldrenGoalsFiledSet = new ChieldrenContextFieldSet(this.mainEntryPointInterface, this.mainEntryPointInterface.getDatabase().getContextLearningGoalsTypesList(), this.mainEntryPointInterface.getDatabase().getContextLearningGoalsList(), this.mainEntryPointInterface.getDatabase().getContextSoftSkillList());
				chieldrenGoalsFiledSet.setTopicData(gm);
				this.add(chieldrenGoalsFiledSet, new FormData("-30"));

			}
			if (!flag) {
				this.setEnabled(false);
			}

		}

	}
}
