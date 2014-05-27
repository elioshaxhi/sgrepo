/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sg.client.forms;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.Utilities;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import org.sg.client.MainEntryPoint;
import org.sg.client.grids.ContributorMessageGrid;

/**
 *
 * @author nertil
 */
public class ContributorMessageForm extends Window{

    private MainEntryPoint entrypoint = null;
    
    private ContributorMessageGrid grid;
    

    public ContributorMessageForm(final MainEntryPoint entrypoint) {
        this.entrypoint = entrypoint;
        setWidth(Utilities.formWidth);
        this.setHeight((int)((double)com.google.gwt.user.client.Window.getClientHeight()*Utilities.pageFactor));
        setHeading(Constants.Strings.contributorMessageInfo());
        setMinWidth(Utilities.smallformWidth);
        setLayout(new FitLayout());
        setStyleName("form-font");
        setModal(true);
        setClosable(true);
        
        grid = new ContributorMessageGrid(entrypoint);
        grid.init();
        
        add(grid, new FitData());        
    }
    
}
