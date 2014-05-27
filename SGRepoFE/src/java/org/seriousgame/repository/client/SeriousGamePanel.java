/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client;

import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.models.Attachment;
import com.customlib.seriousgame.client.models.ModelTag;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.data.BaseFilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.ListViewEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.List;

public class SeriousGamePanel extends ContentPanel {

	private MainEntryPoint entrypoint;
	private ListView<SeriousGame> view;
	private PagingLoader<PagingLoadResult<ModelData>> loader;
	private int pageSize = 10;
	private final int cardWidth = 302;//card css width + 2*margin + 2*padding + 2*border
	private final int cardHeight = 232;//card css height + 2*margin + 2*padding + 2*border
	private PagingToolBar bottomToolbar = null;
	private boolean canLoadFromServer = true;
	private TextField<String> search;
	private SeriousGameForm form = null;

	public SeriousGamePanel(final MainEntryPoint entrypoint) {
		setLayout(new FlowLayout());
		setHeaderVisible(false);
		setBodyBorder(false);
		addStyleName("sg-panel");
		this.entrypoint = entrypoint;
		addListener(Events.Resize, new Listener<ComponentEvent>() {
			@Override
			public void handleEvent(ComponentEvent be) {
				loadSeriusGames();
			}
		});
		addListener(Events.Attach, new Listener<ComponentEvent>() {
			@Override
			public void handleEvent(ComponentEvent be) {
				onInitialize();
			}
		});

		RpcProxy<PagingLoadResult<SeriousGame>> proxy = new RpcProxy<PagingLoadResult<SeriousGame>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<SeriousGame>> callback) {
				entrypoint.mask(true);
				String searchString = "";
				if (search.getValue() != null && search.getValue().length() > 0) {
					searchString = search.getValue();
				}
				entrypoint.getServices().getSeriousGames((FilterPagingLoadConfig) loadConfig, searchString, callback);
			}
		};
		loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy) {
			@Override
			protected Object newLoadConfig() {
				BasePagingLoadConfig config = new BaseFilterPagingLoadConfig();
				return config;
			}
		};
		loader.setRemoteSort(true);
		loader.addListener(Loader.Load, new LoadListener() {
			@Override
			public void loaderLoad(LoadEvent le) {
				entrypoint.mask(false);
				canLoadFromServer = true;
			}
		});

		bottomToolbar = new PagingToolBar(pageSize);
		bottomToolbar.bind(loader);

		bottomToolbar.setAlignment(Style.HorizontalAlignment.CENTER);
		bottomToolbar.addStyleName("sg-toolbar-bottom");
		bottomToolbar.remove(bottomToolbar.getItem(2));
		bottomToolbar.remove(bottomToolbar.getItem(2));
		bottomToolbar.remove(bottomToolbar.getItem(2));
		bottomToolbar.remove(bottomToolbar.getItem(2));
		bottomToolbar.remove(bottomToolbar.getItem(2));

		bottomToolbar.remove(bottomToolbar.getItem(4));
		bottomToolbar.remove(bottomToolbar.getItem(4));
		bottomToolbar.remove(bottomToolbar.getItem(4));

		bottomToolbar.insert(bottomToolbar.getItem(4), 2);

		Html spacer = new Html();
		spacer.setStyleName("toolbar-spacer");
		bottomToolbar.insert(spacer, 2);
		spacer = new Html();
		spacer.setStyleName("toolbar-spacer");
		bottomToolbar.insert(spacer, 4);

		KeyListener keyListener = new KeyListener() {
			@Override
			public void componentKeyUp(ComponentEvent event) {
				if (event.getKeyCode() == 13) {
					loadSeriusGames();
				}
			}
		};

		search = new TextField<String>();
		search.setEmptyText("Insert text to search serious game...");
		search.addStyleName("sg-toolbar-top-search");
		search.setStylePrimaryName("sg-toolbar-top-search");
		search.addKeyListener(keyListener);

		Button b_search = new Button("", IconHelper.create("images/search.png", 24, 24), new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				loadSeriusGames();
			}
		});
		b_search.setScale(Style.ButtonScale.MEDIUM);

		ToolBar topToolbar = new ToolBar();
		topToolbar.setAlignment(Style.HorizontalAlignment.RIGHT);
		topToolbar.addStyleName("sg-toolbar-top");
		spacer = new Html();
		topToolbar.add(search);
		spacer.setWidth("5px");
		topToolbar.add(spacer);
		topToolbar.add(b_search);

		this.setBottomComponent(bottomToolbar);
		this.setTopComponent(topToolbar);
	}

	@Override
	protected void onRender(Element parent, int index) {
		super.onRender(parent, index);
		init();
	}
	final AsyncCallback<SeriousGame> asyncGetSG = new AsyncCallback<SeriousGame>() {
		@Override
		public void onSuccess(SeriousGame result) {
			if (result != null) {
//                SeriousGameForm form = new SeriousGameForm(entrypoint);
//                form.setHeading("Preview " + Constants.Strings.sg().toLowerCase());
//                form.setRecord(result);
//                form.show();
//questo era attivo
//                if(form == null){
//                    form = new SeriousGameForm(entrypoint);
//                }
				form = new SeriousGameForm(entrypoint);//**********prima la new si facceva una sola volta
				form.show(result);
			} else {
				MessageBox.info("Exceprion", "Failed to get SG.", null);
			}
			entrypoint.mask(false);
		}

		@Override
		public void onFailure(Throwable caught) {
			entrypoint.mask(false);
			MessageBox.info("Exceprion", "Failed to get SG..", null);
		}
	};

	private void init() {

		ListStore<SeriousGame> store = new ListStore<SeriousGame>(loader);
		view = new ListView<SeriousGame>() {
			@Override
			protected SeriousGame prepareData(SeriousGame model) {
				List<Attachment> attachments = (List<Attachment>) model.get(SeriousGame.TagAttachments);
				if (attachments != null && !attachments.isEmpty()) {
					String filename = "";
					for (Attachment attachment : attachments) {
						String tempfName = attachment.get(ModelTag.name.toString());
						if (Utilities.isImage(tempfName)) {
							filename = tempfName;
							break;
						}
					}
					if (filename.length() == 0) {
						model.set("noimage", true);
						model.set("path", GWT.getHostPageBaseURL() + "/images/default.png");
					} else {
						model.set("noimage", false);
						model.set("path", GWT.getHostPageBaseURL() + Utilities.uploadedFolder + "/" + filename);
					}
				} else {
					model.set("noimage", true);
					model.set("path", GWT.getHostPageBaseURL() + "/images/default.png");
				}
				return model;
			}
		};

		view.setTemplate(getTemplate());
		view.setStore(store);
		view.setBorders(false);
		view.setItemSelector("div.sg-card");
		view.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
		view.addListener(Events.Select, new Listener<ListViewEvent<SeriousGame>>() {
			@Override
			public void handleEvent(ListViewEvent<SeriousGame> be) {
				entrypoint.mask(true);
				SeriousGame selection = be.getModel();
				entrypoint.getSeriousGame(selection.getId(), selection.getId(), asyncGetSG);
			}
		});
		view.getSelectionModel().addListener(Events.SelectionChange,
			new Listener<SelectionChangedEvent<SeriousGame>>() {
			@Override
			public void handleEvent(SelectionChangedEvent<SeriousGame> be) {
//                entrypoint.mask(true);
//                SeriousGame selection = be.getSelection().get(0);
//                entrypoint.getSeriousGame(selection.getId(), selection.getId(), asyncGetSG);
			}
		});
		this.add(view);

	}

	private void onInitialize() {
		search.el().firstChild().removeStyleName("x-form-field");
		search.el().firstChild().removeStyleName("x-form-text");
		search.el().firstChild().addStyleName("sg-toolbar-top-search-field");
	}

	private void loadSeriusGames() {
		int panelWidth = this.getBody().getWidth();
		int panelHeight = this.getBody().getHeight();

		int columns = panelWidth / cardWidth;
		int rows = panelHeight / cardHeight;

		pageSize = rows * columns;
		bottomToolbar.setPageSize(pageSize);
		if (canLoadFromServer) {
			canLoadFromServer = false;
			loader.load(0, pageSize);
		}
	}

	private native String getTemplate() /*-{
	 return ['<tpl for=".">',
	 '<div class="sg-card">',
	 '<div class="sg-title">{Title}</div>',
	 '<div>',
	 '<p>',
	 '<tpl if="!noimage">',
	 '<img class="sg-image" src="{path}"/>',
	 '</tpl>',
	 '{FreeDescription}',
	 '</p>',
	 '</div>',
	 '</div>',
	 '</tpl>',
	 ''].join("");

	 }-*/;
}
