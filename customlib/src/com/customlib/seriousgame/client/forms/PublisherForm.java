/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.forms;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.Queries;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.interfaces.ClosureFormInterface;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.ModelTag;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author nertil
 */
public class PublisherForm extends Window implements ClosureFormInterface {

    private MainEntryPointInterface entrypoint = null;
    
    private FormPanel form;
    private TextField<String> name;
    private TextArea description;
    private TextField<String> link;
    private boolean okClose = false;
    

    public PublisherForm(final MainEntryPointInterface entrypoint) {
        this.entrypoint = entrypoint;
        setWidth(Utilities.smallformWidth);
        setMinWidth(Utilities.formMinWidth);
        setLayout(new FitLayout());
        setStyleName("form-font");
        setModal(true);
        setButtonAlign(Style.HorizontalAlignment.CENTER);
        setHeading(Constants.Strings.b_new() + " " + Constants.Strings.sg_publisher().toLowerCase());
               
        Button ok_button = new Button(Constants.Strings.b_ok(), new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                if(!form.isValid())
                    return;
                ((Button)ce.getSource()).setEnabled(false);
                GenericModel model = new GenericModel(-1, name.getValue(), description.getValue());
                model.set(ModelTag.link.toString(), link.getValue());
                entrypoint.exequteQuery(Queries.insertPublisher(model), asyncExequteQuery);
            }
        });
        Button cancel_button = new Button(Constants.Strings.b_cancel(), new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                hide();
            }
        });
        addButton(ok_button);
        addButton(cancel_button);
        
        form = new FormPanel();
        form.setEncoding(FormPanel.Encoding.MULTIPART);
        form.setMethod(FormPanel.Method.POST);
        form.setAction("ServletUpload");
        form.setHeaderVisible(false);
        FormLayout layout = new FormLayout(FormPanel.LabelAlign.LEFT);
        layout.setLabelWidth(Utilities.formLabelWidth);
        form.setLayout(layout);
        form.setWidth(Utilities.smallformWidth);
        form.setBodyBorder(false);
        
        FormData formData = new FormData("-20"); 
        
        name = new TextField<String>();  
        name.setFieldLabel(Constants.Strings.name());  
        name.setAllowBlank(false);  
        name.getFocusSupport().setPreviousId(getButtonBar().getId());  
        name.setMaxLength(Utilities.varCharLimitShort);
        form.add(name, formData); 
        
        description = new TextArea();  
        description.setPreventScrollbars(true);   
        description.setFieldLabel(Constants.Strings.description());          
        description.setMaxLength(Utilities.textLimitNormal);
        form.add(description, formData); 
        
        link = new TextField<String>();  
        link.setFieldLabel(Constants.Strings.link()); 
        link.setMaxLength(Utilities.varCharLimitShort);
        link.setRegex(Utilities.urlRegExp);
        link.getMessages().setRegexText(Constants.Strings.urlNotValid());
        form.add(link, formData);
        
        add(form);
        
    }
    final AsyncCallback<Integer> asyncExequteQuery = new AsyncCallback<Integer>() {

        @Override
        public void onSuccess(Integer result) {
            if(result > 0)
            {
                okClose = true;
                hide();
            }
        }
        @Override
        public void onFailure(Throwable caught) {
            MessageBox.info("Exceprion", "Failed to insert data.", null);
        }
    };

    /**
     * @return the okClose
     */
    @Override
    public boolean isOkClose() {
        return okClose;
    }
}
