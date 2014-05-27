/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sg.client.forms;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.models.GenericModel;
import com.customlib.seriousgame.client.models.User;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.*;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import org.sg.client.MainEntryPoint;
import org.xml.sax.SAXException;

/**
 *
 * @author nertil
 */
public class LoginForm extends Window {
    
    private MainEntryPoint entrypoint = null;
    
    protected TextField<String> userName;
    protected TextField<String> password;
    protected Html accessDenied;
    protected FormPanel formPanel;

    public LoginForm(MainEntryPoint entrypoint) {
        this.entrypoint = entrypoint;

        init();
    }

	private void init() {
        setHeading(Constants.Strings.login_title());
        setWidth(380);
        setMinWidth(300);
        setHeight(180);
        setLayout(new FitLayout());
        setStyleName("form-font");

        Button register_button = new Button(Constants.Strings.b_register(), new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                com.google.gwt.user.client.Window.open("http://www.galanoe.eu/index.php/component/comprofiler/registers", "_blank", null);
            }
        });
        Button login_button = new Button(Constants.Strings.b_login(), new SelectionListener<ButtonEvent>() {

            @Override
		public void componentSelected(ButtonEvent ce) {
			login();
		}
        });
        Button cancel_button = new Button(Constants.Strings.b_cancel(), new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                hide();
            }
        });
        addButton(register_button);
        addButton(login_button);        
        addButton(cancel_button);

        setResizable(false);
        setModal(true);
        setButtonAlign(HorizontalAlignment.CENTER);

        setClosable(false);
        
        formPanel = new FormPanel();
        FormLayout layout = new FormLayout(FormPanel.LabelAlign.LEFT);
        layout.setLabelWidth(60);
        formPanel.setLayout(layout);
        formPanel.setHeaderVisible(false);
        formPanel.setBodyBorder(false);
        formPanel.setWidth(250);
        
        KeyListener keyListener = new KeyListener() {

            @Override
            public void componentKeyUp(ComponentEvent event) {
                accessDenied.setVisible(false);
                if(event.getKeyCode() == 13)
		{
				
					login();

		}

            }
        };

        userName = new TextField<String>();
        userName.setMinLength(4);         
        userName.setAllowBlank(false);  
        userName.setFieldLabel(Constants.Strings.username());
        userName.addKeyListener(keyListener);
        formPanel.add(userName, new FormData("-20"));

        password = new TextField<String>();
        password.setMinLength(4);
        password.setPassword(true); 
        password.setAllowBlank(false);  
        password.setFieldLabel(Constants.Strings.password());
        password.addKeyListener(keyListener);
        formPanel.add(password, new FormData("-20"));  
        
        accessDenied = new Html(Constants.Strings.accesDeniedMessage());
        accessDenied.setStyleName("access-denied");
        accessDenied.setVisible(false);
        
        formPanel.add(accessDenied, new FormData("-20"));  
        

        HorizontalPanel content = new HorizontalPanel();
        content.setLayout(new FillLayout());
        content.setSpacing(10);
        content.add(new Image("images/login.png"));
        content.add(formPanel);
        content.setVerticalAlign(VerticalAlignment.MIDDLE);
        setBodyBorder(false);
        add(content);
    }
	private void login(){
        if(!formPanel.isValid())
            return;
        mask();
            entrypoint.getServer().getUser(userName.getValue(), password.getValue(), asyncGetUser);
//        entrypoint.getServer().getPublishers(asyncGetPublishers);
    }
    final AsyncCallback<User> asyncGetUser = new AsyncCallback<User>() {

        @Override
        public void onSuccess(User result) {
            unmask();
            if(result == null)
            {
                accessDenied.setVisible(true);
            }
            else
            {
                allowAccess(result);
                hide();
            }
        }
        @Override
        public void onFailure(Throwable caught) {
            unmask();
            MessageBox.info("Exceprion", "Failed to get user from database.<br>" + caught, null);
        }
    };
    public void checkUser()
    {
        String userInfo = Cookies.getCookie("gala_sg");
        if (userInfo != null) {
            User current = new User(userInfo);
            allowAccess(current);
        }
        else
        {
            show();
        }
    }
    private void allowAccess(User current)
    {
        entrypoint.getServer().getData(current, entrypoint.asyncDatabase);
//        entrypoint.getServer().getPublishers(asyncGetPublishers);

        Date now = new Date();
        long nowLong = now.getTime();
        nowLong = nowLong + (1000 * 60 * 60 * 4);//4 ore
        now.setTime(nowLong);
        Cookies.setCookie("gala_sg", current.toString(), now, null, "/", false);
    }
    final AsyncCallback<List<GenericModel>> asyncGetPublishers = new AsyncCallback<List<GenericModel>>() {

        @Override
        public void onSuccess(List<GenericModel> result) {
            unmask();
            MessageBox.info("Data", result.toString(), null);
        }
        @Override
        public void onFailure(Throwable caught) {
            unmask();
            MessageBox.info("Exceprion", "Failed to publischer from database.<br>" + caught, null);
        }
    };
}
