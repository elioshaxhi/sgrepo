/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client.fieldsetsAnalysis;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.MyExtendedClasses;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Slider;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.SliderField;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

/**
 *
 * @author NERTIL
 */
public class ScoreLabelSet extends FieldSet {

	private Slider score;
	private MyExtendedClasses.SeriouGameLabelField motivation;

	public ScoreLabelSet(String fieldsetTitle, int scoreValue, String motivationValue) {
		setHeading(fieldsetTitle);
		setCollapsible(true);
//        setExpanded(false);
		FormLayout l1 = new FormLayout();
		l1.setLabelWidth(110);
		setLayout(l1);

		motivation = new MyExtendedClasses.SeriouGameLabelField(Constants.Strings.analysis_motivation());
		motivation.setValue(motivationValue);
//		motivation.setEnabled(false);

		score = new Slider();
		score.setMinValue(1);
		score.setMaxValue(5);
		score.setValue(scoreValue);
		score.setIncrement(1);
		score.setHeight(55);
		score.setVertical(true);
		score.setEnabled(false);
		score.setDraggable(false);


		SliderField sf = new SliderField(score);
		sf.setToolTip(Constants.Strings.tt_analysis_scoore());
		sf.setFieldLabel(Constants.Strings.analysis_scoore());
		sf.setEnabled(false);

		LayoutContainer c = new LayoutContainer();
		c.setLayout(new ColumnLayout());

		LayoutContainer l = new LayoutContainer();
		l.setStyleAttribute("paddingRight", "10px");
		l.setStyleAttribute("paddingTop", "10px");
		FormLayout layout = new FormLayout();
		layout.setLabelWidth(50);
		l.setLayout(layout);

		LayoutContainer r = new LayoutContainer();
		r.setStyleAttribute("paddingLeft", "10px");
		r.setStyleAttribute("paddingTop", "10px");
		r.setLayout(new FormLayout());

		l.add(sf, new FormData("100%"));
		r.add(motivation, new FormData("100%"));
		c.add(l, new ColumnData(.15));
		c.add(r, new ColumnData(.85));
		add(c, new FormData("-5"));
	}

	public int getScore() {
		return score.getValue();
	}

	public void setScore(int scoreValue) {
		((SliderField) score.getParent()).setValue(scoreValue);
		score.setEnabled(false);
		((SliderField) score.getParent()).setEnabled(false);
	}

	public MyExtendedClasses.SeriouGameLabelField getMotivation() {
		return motivation;
	}

	public void setMotivation(Field field, String value) {
		if (value != null) {
			value = value.replaceAll("\n", "<br>");
			field.setVisible(true);
			field.setValue(value);
		} else {
			this.setExpanded(false);
			this.setEnabled(false);
			field.setVisible(false);
		}
	}
}
