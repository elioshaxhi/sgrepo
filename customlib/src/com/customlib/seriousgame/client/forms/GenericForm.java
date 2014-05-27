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
public class GenericForm extends Window implements ClosureFormInterface{

    private MainEntryPointInterface entrypoint = null;
    
    private FormPanel form;
    private TextField<String> name;
    private TextArea description;
    private boolean okClose = false;
    

    public GenericForm(final MainEntryPointInterface entrypoint, String winTitle, final String tableName) {
        this.entrypoint = entrypoint;
        setWidth(Utilities.smallformWidth);
        setHeading(Constants.Strings.b_new() + " " + winTitle.toLowerCase());
        setMinWidth(Utilities.smallformWidth);
        setLayout(new FitLayout());
        setStyleName("form-font");
        setModal(true);
        setClosable(false);
        setButtonAlign(Style.HorizontalAlignment.CENTER);
               
        Button ok_button = new Button(Constants.Strings.b_ok(), new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                if(!form.isValid())
                    return;
                ((Button)ce.getSource()).setEnabled(false);
                if(tableName != null)
                    entrypoint.exequteQuery(Queries.insertGeneric(new GenericModel(-1, name.getValue(), description.getValue()), tableName), asyncExequteQuery);
                else
                {
                    okClose = true;
                    hide();
                }
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
        form.add(description, formData); 
        
        add(form);
        
    }
    public String getName()
    {
        return name.getValue();
    }
    public String getDescription()
    {
        return description.getValue();
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
    public void renameFields(String name, String description)
    {
        this.name.setFieldLabel(name);
        this.description.setFieldLabel(description);
    }

    /**
     * @return the okClose
     */
    @Override
    public boolean isOkClose() {
        return okClose;
    }
}
