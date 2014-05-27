/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sg.client;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.models.User;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.sg.client.forms.ContributorMessageForm;

/**
 *
 * @author nertil
 */
public class TopPanel extends HorizontalPanel
{
    private MainEntryPoint entrypoint = null;
    private HTML user;
    private Button messages;

    public TopPanel(final MainEntryPoint entrypoint)
    {
        this.entrypoint = entrypoint;

        this.setStyleName("top-panel");
        this.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        
        Image logo = new Image("images/logo.png");
        logo.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Window.open("http://www.galanoe.eu/", "_blank", null);
            }
        });
        logo.setStyleName("top-panel-logo");
        
        HTML title = new HTML();
        title.setStyleName("top-panel-title-label");

        user = new HTML();
        user.setVisible(false);
        user.setStyleName("top-panel-user-label");
        
        messages = new Button(Constants.Strings.messages(), IconHelper.create("images/message_m.png",24,24), new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                ContributorMessageForm form = new ContributorMessageForm(entrypoint);
                form.show();
            }
        });
        messages.addStyleName("top-panel-messages-button");
        messages.setScale(Style.ButtonScale.MEDIUM);
        
        
        VerticalPanel userPanel = new VerticalPanel(); 
        userPanel.setStyleName("top-panel-info-panel");
        userPanel.setHorizontalAlign(Style.HorizontalAlignment.CENTER);
        
        userPanel.add(user);  
        userPanel.add(messages);

        add(logo);
        add(userPanel);
    }
    public void setUser()
    {
        if(entrypoint.getDatabase() == null || entrypoint.getDatabase().getCurrentUser() == null)
            return;
        user.setText(Constants.Strings.user() + ":  " + (String)entrypoint.getDatabase().getCurrentUser().get(User.TagName));
        user.setVisible(true);
        messages.setText(entrypoint.getDatabase().getContributersMessagesList().size() + " " + Constants.Strings.messages());
    }
    
}
