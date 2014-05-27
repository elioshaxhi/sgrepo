/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.forms;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.SeriousGameTabPanel;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.forms.AlgorithmForm;
import com.customlib.seriousgame.client.interfaces.ClosureFormInterface;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FillData;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author nertil
 */
public class SeriousGameForm extends Window implements ClosureFormInterface {

	private MainEntryPointInterface entrypoint;
	private SeriousGameTabPanel sgTabPanel;
	private Button saveAndClose_button;
	private Button save_button;
	private boolean okClose = false;
	private SeriousGame record;

	public SeriousGameForm(final MainEntryPointInterface entrypoint) {
		this.entrypoint = entrypoint;
		setWidth((int) ((double) com.google.gwt.user.client.Window.getClientWidth() * Utilities.pageFactor));
		setHeight((int) ((double) com.google.gwt.user.client.Window.getClientHeight() * Utilities.pageFactor));
		setMinWidth(Utilities.formMinWidth);
		setLayout(new FillLayout(Style.Orientation.HORIZONTAL));
		setStyleName("form-font");
		setModal(true);
		setButtonAlign(Style.HorizontalAlignment.CENTER);
		setClosable(false);

		sgTabPanel = new SeriousGameTabPanel(entrypoint);
		sgTabPanel.init();

		saveAndClose_button = new Button(Constants.Strings.b_saveAndClose(), new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if (!sgTabPanel.getGeneralForm().isValid()) {
					sgTabPanel.SetSelection(0);
					return;
				}
				if (!sgTabPanel.getRelationPanel().isValid()) {
					sgTabPanel.SetSelection(1);
					return;
				}
				saveAndClose_button.setEnabled(false);
				save_button.setEnabled(false);
				sgTabPanel.mask();
				entrypoint.updateSeriousGame(getSeriousGame(), asyncUpdateAndClose);
			}
		});
		save_button = new Button(Constants.Strings.b_save(), new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if (!sgTabPanel.getGeneralForm().isValid()) {
					sgTabPanel.SetSelection(0);
					return;
				}
				if (!sgTabPanel.getRelationPanel().isValid()) {
					sgTabPanel.SetSelection(1);
					return;
				}
				saveAndClose_button.setEnabled(false);
				save_button.setEnabled(false);
				sgTabPanel.mask();
				entrypoint.updateSeriousGame(getSeriousGame(), asyncUpdate);
			}
		});
		Button cancel_button = new Button(Constants.Strings.b_cancel(), new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				hide();
			}
		});
		addButton(saveAndClose_button);
		addButton(save_button);
		addButton(cancel_button);

		add(sgTabPanel, new FillData());

		sgTabPanel.getGeneralForm().setChanges(true);

	}
	final AsyncCallback<SeriousGame> asyncUpdateAndClose = new AsyncCallback<SeriousGame>() {

		@Override
		public void onSuccess(SeriousGame result) {

			saveAndClose_button.setEnabled(true);
			save_button.setEnabled(true);
			sgTabPanel.unmask();
			if (result != null) {
				okClose = true;
				hide();
			} else {
				MessageBox.info("Exceprion", "Failed to update sg.", null);
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			MessageBox.info("Exceprion", "Failed to update sg.", null);
		}
	};
	final AsyncCallback<SeriousGame> asyncUpdate = new AsyncCallback<SeriousGame>() {

		@Override
		public void onSuccess(SeriousGame result) {
			saveAndClose_button.setEnabled(true);
			save_button.setEnabled(true);
			sgTabPanel.unmask();
			sgTabPanel.save();
			if (result != null) {
			} else {
				MessageBox.info("Exceprion", "Failed to update sg.", null);
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			MessageBox.info("Exceprion", "Failed to update sg.", null);
		}
	};

	/**
	 * @return the okClose
	 */
	@Override
	public boolean isOkClose() {
		return okClose;
	}

	/**
	 * @return the record
	 */
	public SeriousGame getSeriousGame() {
		sgTabPanel.getGeneralForm().updateRecord(record);
		sgTabPanel.getRelationPanel().updateRecord(record);
		sgTabPanel.getTechnologyForm().updateRecord(record);
		sgTabPanel.getLearningEnvironmentForm().updateRecord(record);
		sgTabPanel.getArchitectureForm().updateRecord(record);
		sgTabPanel.getContextAndAnalysisForm().updateRecord(record);
		return record;
	}

	/**
	 * @param record the record to set
	 */
	public void setRecord(SeriousGame record) {
		this.record = record;
		sgTabPanel.getGeneralForm().setRecord(this.record);
		sgTabPanel.getRelationPanel().setRecord(this.record);
		sgTabPanel.getTechnologyForm().setRecord(this.record);
		sgTabPanel.getLearningEnvironmentForm().setRecord(this.record);
		sgTabPanel.getArchitectureForm().setRecord(this.record);
		sgTabPanel.getContextAndAnalysisForm().setRecord(this.record);
	}
}
