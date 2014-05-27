/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.grids;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.forms.AlgorithmForm;
import com.customlib.seriousgame.client.forms.GenericArchitectureForm;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.ModelTag;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.tips.QuickTip;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nertil
 */
public class AlgorithmGrid extends ContentPanel {

	private MainEntryPointInterface entrypoint = null;

	private Grid<GenericModel> grid;
	private CheckBoxSelectionModel<GenericModel> cbSelectionModel;
	private Button b_add;
	private ToolBar toolBar;
	private List<GenericModel> components;
	private GenericModel tmp;

	public AlgorithmGrid(final MainEntryPointInterface entrypoint, final String header, ArrayList<GenericModel> sourceData) {
		this.entrypoint = entrypoint;
		setHeaderVisible(false);
		setLayout(new FitLayout());
		setHeight(150);

		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		cbSelectionModel = new CheckBoxSelectionModel<GenericModel>();

		configs.add(cbSelectionModel.getColumn());

		ColumnConfig column = new ColumnConfig(ModelTag.name.toString(), Constants.Strings.name(), 250);
		configs.add(column);

		column = new ColumnConfig(ModelTag.description.toString(), Constants.Strings.description(), 150);
		column.setRenderer(Utilities.getRenderCellWithTooltip());
		configs.add(column);

		column = new ColumnConfig(ModelTag.specificSGCase.toString(), Constants.Strings.arch_specificSGCase(), 150);
		column.setRenderer(Utilities.getRenderCellWithTooltip());
		configs.add(column);

		ListStore<GenericModel> store = new ListStore<GenericModel>();
		store.add(sourceData);

		ColumnModel cm = new ColumnModel(configs);
		grid = new Grid<GenericModel>(store, cm);
		grid.setSelectionModel(cbSelectionModel);
		grid.addPlugin(cbSelectionModel);
		grid.setAutoExpandColumn(ModelTag.name.toString());
		grid.setStyleAttribute("border", "none");
		grid.getView().setAdjustForHScroll(false);
		grid.getView().setAutoFill(true);
		grid.setBorders(false);
		grid.setStripeRows(true);

		b_add = new Button(Constants.Strings.b_add(), IconHelper.create("images/new.gif"));
		b_add.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				AlgorithmForm form = new AlgorithmForm(entrypoint, Constants.Strings.b_new() + " " + header, components);
				form.addListener(Events.Hide, new Listener<WindowEvent>() {

					@Override
					public void handleEvent(WindowEvent be) {
						AlgorithmForm f = (AlgorithmForm) be.getSource();
						if (f.isOkClose()) {
							tmp = f.getAlgorithm();
							addModel(tmp);
						}
					}
				});
				form.show();
			}
		});
		Button b_delete = new Button(Constants.Strings.b_delete(), IconHelper.create("images/delete.gif"), new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				removeSelectedElements();
			}
		});

		Button b_edit = new Button(Constants.Strings.b_edit(), IconHelper.create("images/edit.png"), new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				AlgorithmForm form = new AlgorithmForm(entrypoint, Constants.Strings.b_edit() + " " + header, components);
				if (getSelection() == null) {
					MessageBox.alert(Constants.Strings.alert(), Constants.Strings.zeroSelection(), null);
				} else {
					form.setAlgorithm(getSelection());
					form.addListener(Events.Hide, new Listener<WindowEvent>() {

						@Override
						public void handleEvent(WindowEvent be) {
							AlgorithmForm f = (AlgorithmForm) be.getSource();
							if (f.isOkClose()) {
								removeSelectedElements();
								tmp = f.getAlgorithm();
								addModel(tmp);
							}
						}
					});
					form.show();
				}
			}
		});

		Html title = new Html(header);
		title.addStyleName("relations-title");
		title.setStyleAttribute("font", "bold 11px tahoma,arial,verdana,sans-serif");
		title.setStyleAttribute("marginLeft", "5px");

		toolBar = new ToolBar();
		toolBar.add(title);
		toolBar.add(new FillToolItem());
		toolBar.add(b_add);
		toolBar.add(b_edit);
		toolBar.add(b_delete);

		add(grid);
		setTopComponent(toolBar);

		new QuickTip(grid);

	}

	public void setData(List<GenericModel> data) {
		grid.getStore().removeAll();
		grid.getStore().add(data);
	}

	public List<GenericModel> getData() {
		return grid.getStore().getModels();
	}

	/**
	 * @param components the components to set
	 */
	public void setComponents(List<GenericModel> components) {
		this.components = components;
	}

	public void addModel(GenericModel record) {
		grid.getStore().add(record);
	}

	public void removeSelectedElements() {
		List<GenericModel> records = cbSelectionModel.getSelectedItems();
		for (GenericModel model : records) {
			grid.getStore().remove(model);
		}
	}

	public GenericModel getSelection() {
		List<GenericModel> records = cbSelectionModel.getSelectedItems();
		if (records.isEmpty()) {
			MessageBox.alert(Constants.Strings.alert(), Constants.Strings.zeroSelection(), null);
			return null;
		}
		if (records.size() > 1) {
			MessageBox.alert(Constants.Strings.alert(), Constants.Strings.moreSelection(), null);
			return null;
		}
		return records.get(0);
	}
}
