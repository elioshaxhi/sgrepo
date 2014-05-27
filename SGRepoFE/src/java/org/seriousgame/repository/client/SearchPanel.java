/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.forms.SeriousGameForm;
import com.customlib.seriousgame.client.models.Attachment;
import com.customlib.seriousgame.client.models.ModelTag;
import com.customlib.seriousgame.client.models.SeriousGame;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.AnchorData;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.List;

public class SearchPanel extends LayoutContainer {

    private MainEntryPoint entrypoint;
    private ListView<SeriousGame> view;
    private TextField<String> search;

    public SearchPanel(MainEntryPoint entrypoint) {
        setLayout(new FlowLayout());
        this.entrypoint = entrypoint;
    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);
        setStyleAttribute("borderTop", "1px solid lightgray");
        
        search = new TextField<String>();
        search.setEmptyText("Insert text to search serious game...");
        search.setWidth(500);
        search.setStyleAttribute("marginLeft", "200px");

        Button b_search = new Button("Search", new SelectionListener<ButtonEvent>() {
            @Override
            public void componentSelected(ButtonEvent ce) {
                if(search.getValue() != null && search.getValue().length() > 0)
                {
                    entrypoint.mask(true);
                    entrypoint.getSeriousGame(search.getValue(), asyncSearchGetSG);
                }
            }
        });
        HorizontalPanel searchPanel = new HorizontalPanel();
        searchPanel.setStyleAttribute("marginTop", "15px");
        searchPanel.add(search);
        searchPanel.add(b_search);
        searchPanel.setSpacing(5);

        ListStore<SeriousGame> store = new ListStore<SeriousGame>();
        store.add(new ArrayList<SeriousGame>());
        view = new ListView<SeriousGame>() {

            @Override
            protected SeriousGame prepareData(SeriousGame model) {
                List<Attachment> attachments = (List<Attachment>) model.get(SeriousGame.TagAttachments);
                if (attachments != null && !attachments.isEmpty()) {
                    model.set("path", GWT.getHostPageBaseURL() + Utilities.uploadedFolder + "/" + attachments.get(0).get(ModelTag.name.toString()));
                } else {
                    model.set("path", GWT.getHostPageBaseURL() + "/images/default.png");
                }
                return model;
            }
        };
        view.setTemplate(getTemplate());
        view.setStore(store);
        view.setBorders(false);
        view.setItemSelector("div.search-card");
        view.getSelectionModel().setSelectionMode(Style.SelectionMode.SINGLE);
        view.getSelectionModel().addListener(Events.SelectionChange,
                new Listener<SelectionChangedEvent<SeriousGame>>() {

                    @Override
                    public void handleEvent(SelectionChangedEvent<SeriousGame> be) {
                        entrypoint.mask(true);
                        SeriousGame selection = be.getSelection().get(0);
                        entrypoint.getSeriousGame(selection.getId(), selection.getId(), asyncGetSG);


                    }
                });
        add(searchPanel, new AnchorData("90%"));
        add(view);

    }
    final AsyncCallback<SeriousGame> asyncGetSG = new AsyncCallback<SeriousGame>() {

        @Override
        public void onSuccess(SeriousGame result) {
            if (result != null) {
                SeriousGameForm form = new SeriousGameForm(entrypoint);
                form.setHeading("Preview " + Constants.Strings.sg().toLowerCase());
                form.setRecord(result);
                form.show();
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
    final AsyncCallback<List<SeriousGame>> asyncSearchGetSG = new AsyncCallback<List<SeriousGame>>() {

        @Override
        public void onSuccess(List<SeriousGame> result) {
            if (result != null) {
                view.getStore().removeAll();
                view.getStore().add(result);
                layout();
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
    public void debug()
    {
        System.out.println("debug");
    }


    private native String getTemplate() /*-{ 
     return ['<tpl for=".">',  
       '<div class="search-card">',  
       '<div class="lastSG-title">{Title}</div>',  
       '<div><p><img class="search-image" src="{path}"/>{FreeDescription}</p></div>',
       '</div>',  
       '</tpl>',  
       ''].join("");
      
     }-*/;  
}
