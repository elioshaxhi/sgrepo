/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.customlib.seriousgame.client.forms;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.interfaces.MainEntryPointInterface;
import com.customlib.seriousgame.client.models.Attachment;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.*;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;

/**
 *
 * @author nertil
 */
public class AttachmentForm extends Window{

    private MainEntryPointInterface entrypoint = null;
    
    private FormPanel form;
    private com.customlib.seriousgame.client.forms.FileBrowser file;
    private RadioGroup fileType;
    private Attachment current = null;
    

    public AttachmentForm(final MainEntryPointInterface entrypoint, int seriousGameID) {
        this.entrypoint = entrypoint;
        setWidth(Utilities.smallformWidth);
        setHeading(Constants.Strings.b_new() + " " + Constants.Strings.sg_availability().toLowerCase());
        setMinWidth(Utilities.smallformWidth);
        setLayout(new FitLayout());
        setStyleName("form-font");
        setModal(true);
        setButtonAlign(Style.HorizontalAlignment.CENTER);
        setClosable(true);
        
        Button ok_button = new Button(Constants.Strings.b_ok(), new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                if(!form.isValid())
                    return;
                file.generateImageFileName();
                mask(true);
                form.submit();
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
        form.addListener(Events.Submit, new Listener<FormEvent>() {
            @Override
            public void handleEvent(FormEvent arg0) {
                String fileInfo = arg0.getResultHtml().replaceAll("\\<.*?>","");
                current = new Attachment(fileInfo);
                mask(false);
                hide();
            }
        });
        
        FormData formData = new FormData("-20"); 
        
        Radio rb_sgdescriptionfile = new Radio();
        rb_sgdescriptionfile.setBoxLabel(Constants.Strings.attachment_sgdescription());
        rb_sgdescriptionfile.setToolTip(new ToolTipConfig(Constants.Strings.tt_sgDescriptionFile()));
        rb_sgdescriptionfile.setValue(true);

        Radio rb_otherfile = new Radio();        
        rb_otherfile.setBoxLabel(Constants.Strings.attachment_other());
        rb_otherfile.setToolTip(new ToolTipConfig(Constants.Strings.tt_otherFiles()));
        
        fileType = new RadioGroup();  
        fileType.setFieldLabel(Constants.Strings.attachment_type());  
        fileType.add(rb_sgdescriptionfile);  
        fileType.add(rb_otherfile);  
        
        file = new com.customlib.seriousgame.client.forms.FileBrowser(entrypoint.getDatabase().getCurrentUser().getId(), "file" + Utilities.fileNameseparator + seriousGameID);  
        file.setAllowBlank(false);
        file.setFieldLabel(Constants.Strings.file());
        
             
        form.add(fileType, formData); 
        form.add(file, formData);   
                
        add(form);
        
    }
    public Attachment getCurrent()
    {
        return current;
    }
    private void mask(boolean mask)
    {
        if(mask)
            mask();
        else
            unmask();
    }
}
