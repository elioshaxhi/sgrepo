/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.grids;

import com.customlib.seriousgame.client.*;
import com.customlib.seriousgame.client.forms.GenericForm;
import com.customlib.seriousgame.client.forms.SeriousGameForm;
import com.customlib.seriousgame.client.interfaces.GridBasicFuncionalities;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.interfaces.ReloadDataInterface;
import com.customlib.seriousgame.client.models.ContributersMessages;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.PagingModelMemoryProxy;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.grid.*;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.tips.QuickTip;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.KeyboardListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author nertil
 */
public class SeriousGameGrid extends ContentPanel implements GridBasicFuncionalities, ReloadDataInterface {

	private MainEntryPointInterface entrypoint = null;

	PagingToolBar toolBar = null;
	NumberField pageSizeField = null;
	PagingLoader loader = null;
	PagingModelMemoryProxy proxy = null;

	MyExtendedClasses.CostomGrid<SeriousGame> grid = null;

	CheckBoxSelectionModel<SeriousGame> cbSelectionModel = null;

	private int pageSize = 8;
	private int currentPage = 1;

	private Listener<MessageBoxEvent> focusListner = null;
	int dimensionForGrid = 0;

	public SeriousGameGrid(MainEntryPointInterface entrypoint) {
		this.entrypoint = entrypoint;
	}

	public void setDimension(int hHeight) {
		dimensionForGrid = (Window.getClientHeight() - hHeight);
		int heightRowGrid = 21;
		dimensionForGrid -= modulo(dimensionForGrid, heightRowGrid);
		this.pageSize = (dimensionForGrid / heightRowGrid);
	}

	public int modulo(int a, int b) {
		return a % b;
	}

	public void init() {

		setBodyBorder(false);
		setHeaderVisible(false);
		setButtonAlign(HorizontalAlignment.CENTER);
		setLayout(new FitLayout());
		//setSize("100%", "590px");

		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

		cbSelectionModel = new CheckBoxSelectionModel<SeriousGame>();

		configs.add(cbSelectionModel.getColumn());

		ColumnConfig column = new ColumnConfig(SeriousGame.TagTitle, Constants.Strings.sg_title(), 400);
		configs.add(column);

		column = new ColumnConfig(SeriousGame.TagOwner, Constants.Strings.sg_owner(), 120);
		configs.add(column);

		column = new ColumnConfig(SeriousGame.TagDate, Constants.Strings.sg_date(), 120);
		column.setDateTimeFormat(DateTimeFormat.getLongDateFormat());
		configs.add(column);

		column = new ColumnConfig(SeriousGame.TagMarketsString, Constants.Strings.sg_markets(), 150);
		column.setRenderer(Utilities.getRenderCellWithTooltip());
		configs.add(column);

		column = new ColumnConfig(SeriousGame.TagGenresString, Constants.Strings.sg_genres(), 150);
		column.setRenderer(Utilities.getRenderCellWithTooltip());
		configs.add(column);

		column = new ColumnConfig(SeriousGame.TagKeywords, Constants.Strings.sg_keywords(), 120);
		configs.add(column);

		column = new ColumnConfig(SeriousGame.TagFreeDescription, Constants.Strings.sg_freeDescription(), 150);
		column.setHidden(true);
		column.setRenderer(Utilities.getRenderCellWithTooltip());
		configs.add(column);

		column = new ColumnConfig(SeriousGame.TagWebSite, Constants.Strings.sg_webSite(), 150);
		column.setHidden(true);
		column.setRenderer(new GridCellRenderer() {
			@Override
			public Object render(ModelData model, String property, ColumnData config, int rowIndex, int colIndex, ListStore store, Grid grid) {
				String url = model.get(SeriousGame.TagWebSite);
				if (url == null || url.length() == 0) {
					return "";
				}

				return "<a href=\"" + url + "\" target=\"_blank\">" + url + "</a>";
			}
		});
		configs.add(column);

		proxy = new PagingModelMemoryProxy(new ArrayList<SeriousGame>());

		loader = new BasePagingLoader(proxy);
		loader.setRemoteSort(true);

		ListStore<SeriousGame> store = new ListStore<SeriousGame>(loader);

		ColumnModel cm = new ColumnModel(configs);
		grid = new MyExtendedClasses.CostomGrid<SeriousGame>(store, cm, cbSelectionModel, SeriousGame.TagTitle, this);

		Button delete = new Button(Constants.Strings.b_delete(), IconHelper.create("images/delete.gif"), new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				deleteElements();
			}
		});
		Button newElement = new Button(Constants.Strings.b_new(), IconHelper.create("images/new.gif"), new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				newElement();
			}
		});
		Button edit = new Button(Constants.Strings.b_edit(), IconHelper.create("images/edit.png"), new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				editElement();
			}
		});

		Button contribute = new Button(Constants.Strings.b_contribute(), IconHelper.create("images/gears.gif"), new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				List<SeriousGame> records = cbSelectionModel.getSelectedItems();
				if (records.isEmpty()) {
					MessageBox.alert(Constants.Strings.alert(), Constants.Strings.zeroSelection(), focusListner);
					return;
				}
				ArrayList<Integer> ids = new ArrayList<Integer>();
				for (SeriousGame seriousGame : records) {
					if (entrypoint.getDatabase().getCurrentUser().getId().intValue() == ((Integer) seriousGame.get(SeriousGame.TagOwnerId)).intValue()
						|| ((Boolean) seriousGame.get(SeriousGame.TagIsCurrentUserContributor))) {
						MessageBox.alert(Constants.Strings.alert(), Constants.Strings.msg_canContribute(), focusListner);
						return;
					}
					ids.add(seriousGame.getId());
				}
				entrypoint.exequteQuery(Queries.insertRelations(DbTables.contributors_messages.toString(), ContributersMessages.TagSenderId, DbTablesSpecialColums.seriousGameID.toString(),
					entrypoint.getDatabase().getCurrentUser().getId(), ids), asyncExequteQuery);
				entrypoint.exequteQuery(Queries.insertRelations(DbTables.contributors.toString(), DbTablesSpecialColums.userID.toString(), DbTablesSpecialColums.seriousGameID.toString(),
					entrypoint.getDatabase().getCurrentUser().getId(), ids), asyncExequteQueryReload);
			}
		});
		pageSizeField = new NumberField();
		pageSizeField.setWidth(40);
		pageSizeField.setSelectOnFocus(true);
		pageSizeField.setValue(this.pageSize);
		pageSizeField.addKeyListener(new KeyListener() {
			@Override
			public void componentKeyPress(ComponentEvent event) {
				if (event.getKeyCode() == KeyboardListener.KEY_ENTER) {
					int size = pageSizeField.getValue().intValue();
					pageSize = size;
					toolBar.setPageSize(size);
					loader.load(0, pageSize);
				}
			}
		});

		toolBar = new PagingToolBar(pageSize);
		toolBar.bind(loader);
		toolBar.insert(contribute, 11);
		toolBar.insert(delete, 11);
		toolBar.insert(edit, 11);
		toolBar.insert(newElement, 11);
		toolBar.insert(new SeparatorToolItem(), 11);
		toolBar.insert(pageSizeField, 10);
		toolBar.getMessages().setEmptyMsg("");

		loader.load(0, pageSize);

		add(grid);
		setBottomComponent(toolBar);

		mask(Constants.Strings.loading());

		focusListner = new Listener<MessageBoxEvent>() {

			@Override
			public void handleEvent(MessageBoxEvent be) {
				grid.focus();
			}
		};

		new QuickTip(grid);
	}

	public void reload() {
		proxy.setData(entrypoint.getDatabase().getSeriousGameList());
		toolBar.refresh();
		toolBar.setActivePage(currentPage);
		unmask();
	}

	@Override
	public void getDataFromServer() {
		mask(Constants.Strings.loading());
		entrypoint.getSeriousGames(entrypoint.getDatabase().getCurrentUser().getId(), asyncGetseriousGames);
	}

	@Override
	public void deleteElements() {

		List<SeriousGame> records = cbSelectionModel.getSelectedItems();
		if (records.isEmpty()) {
			MessageBox.alert(Constants.Strings.alert(), Constants.Strings.zeroSelection(), focusListner);
			return;
		}
		for (Iterator<SeriousGame> it = records.iterator(); it.hasNext();) {
			SeriousGame seriousGame = it.next();
			if (entrypoint.getDatabase().getCurrentUser().getId().intValue() != ((Integer) seriousGame.get(SeriousGame.TagOwnerId)).intValue()
				&& !((Boolean) seriousGame.get(SeriousGame.TagIsCurrentUserContributor))) {
				MessageBox.alert(Constants.Strings.alert(), Constants.Strings.enableToDelete(), focusListner);
				return;
			}
		}
		MessageBox.confirm(Constants.Strings.alert(), Constants.Strings.deleteMessage(), new Listener<MessageBoxEvent>() {

			@Override
			public void handleEvent(MessageBoxEvent be) {
				if (be.getButtonClicked().getItemId().equals(Dialog.YES)) {
					currentPage = toolBar.getActivePage();
					List<SeriousGame> records = cbSelectionModel.getSelectedItems();
					List<Integer> recordsIds = new ArrayList<Integer>();
					mask(Constants.Strings.loading());
					for (int i = 0; i < records.size(); i++) {
						recordsIds.add(records.get(i).getId());
						grid.getStore().remove(records.get(i));
					}
					entrypoint.exequteQuery(Queries.delete(SeriousGame.TableName, SeriousGame.TagId, recordsIds), asyncExequteQuery);
				} else {
					grid.focus();
				}
			}
		});

	}

	@Override
	public void editElement() {
		List<SeriousGame> records = cbSelectionModel.getSelectedItems();
		if (records.isEmpty()) {
			MessageBox.alert(Constants.Strings.alert(), Constants.Strings.zeroSelection(), focusListner);
			return;
		}
		if (records.size() > 1) {
			MessageBox.alert(Constants.Strings.alert(), Constants.Strings.moreSelection(), focusListner);
			return;
		}
		mask(Constants.Strings.loading());
		entrypoint.getSeriousGame(entrypoint.getDatabase().getCurrentUser().getId(), records.get(0).getId(), asyncEditSG);
	}

	@Override
	public void newElement() {
		GenericForm gf = new GenericForm(entrypoint, Constants.Strings.sg(), null);
		gf.renameFields(Constants.Strings.sg_title(), Constants.Strings.sg_freeDescription());
		gf.addListener(Events.Hide, new Listener<WindowEvent>() {

			@Override
			public void handleEvent(WindowEvent be) {
				GenericForm form = (GenericForm) be.getSource();
				if (form.isOkClose()) {
					mask(Constants.Strings.loading());
					SeriousGame record = new SeriousGame(entrypoint.getDatabase().getCurrentUser().getId(), form.getName(), form.getDescription());
					entrypoint.insertNewSeriousGame(record, asyncInsertNewSG);
				}
			}
		});
		gf.show();
	}
	final AsyncCallback<List<SeriousGame>> asyncGetseriousGames = new AsyncCallback<List<SeriousGame>>() {

		@Override
		public void onSuccess(List<SeriousGame> result) {
			if (result != null) {
				grid.focus();
				entrypoint.getDatabase().setSeriousGameList(result);
				reload();
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			MessageBox.info("Exceprion", "Failed to get seriousGame.", focusListner);
		}
	};
	final AsyncCallback<Integer> asyncExequteQuery = new AsyncCallback<Integer>() {

		@Override
		public void onSuccess(Integer result) {
			unmask();
		}

		@Override
		public void onFailure(Throwable caught) {
			MessageBox.info("Exceprion", "Failed to insert data.", focusListner);
		}
	};
	final AsyncCallback<Integer> asyncExequteQueryReload = new AsyncCallback<Integer>() {

		@Override
		public void onSuccess(Integer result) {
			getDataFromServer();
		}

		@Override
		public void onFailure(Throwable caught) {
			MessageBox.info("Exceprion", "Failed to insert data.", focusListner);
		}
	};
	final AsyncCallback<SeriousGame> asyncInsertNewSG = new AsyncCallback<SeriousGame>() {

		@Override
		public void onSuccess(SeriousGame result) {
			unmask();
			if (result != null) {
				editSeriousGame(result, true);
			} else {
				MessageBox.info("Exceprion", "Failed to insert new SG.", focusListner);
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			unmask();
			MessageBox.info("Exceprion", "Failed to insert new SG.", focusListner);
		}
	};
	final AsyncCallback<SeriousGame> asyncEditSG = new AsyncCallback<SeriousGame>() {

		@Override
		public void onSuccess(SeriousGame result) {
			unmask();
			if (result != null) {
				editSeriousGame(result, false);
			} else {
				MessageBox.info("Exceprion", "Failed to get SG.", focusListner);
			}
		}

		@Override
		public void onFailure(Throwable caught) {
			unmask();
			MessageBox.info("Exceprion", "Failed to get SG..", focusListner);
		}
	};

	private void editSeriousGame(SeriousGame record, boolean isNew) {
		SeriousGameForm form = new SeriousGameForm(entrypoint);
		if (isNew) {
			form.setHeading(Constants.Strings.b_new() + " " + Constants.Strings.sg().toLowerCase());
		} else {
			form.setHeading(Constants.Strings.b_edit() + " " + Constants.Strings.sg().toLowerCase());
		}
		form.setRecord(record);
		form.show();
		form.addListener(Events.Hide, new Listener<WindowEvent>() {

			@Override
			public void handleEvent(WindowEvent be) {
				getDataFromServer();
				entrypoint.getDatabase().resetOthers();
			}
		});
	}

}
