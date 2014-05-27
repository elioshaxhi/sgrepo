/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sg.client;

import com.customlib.seriousgame.client.Constants;
import com.customlib.seriousgame.client.Utilities;
import com.customlib.seriousgame.client.grids.SeriousGameGrid;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.layout.AnchorData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Window;

/**
 *
 * @author nertil
 */
public class CentralPanel extends TabPanel {

	private MainEntryPoint entrypoint = null;
	private int dimensioneForGrid = 0;

	private SeriousGameGrid seriousGameGrid = null;

	public CentralPanel(MainEntryPoint entrypoint) {
		this.entrypoint = entrypoint;
		seriousGameGrid = new SeriousGameGrid(entrypoint);
	}

	public void setHeightToRetrieve(int dimensioneForGrid) {
		this.dimensioneForGrid = dimensioneForGrid;
	}

	public void init() {

		this.setResizeTabs(true);
		this.setMinTabWidth(115);
		this.setTabWidth(180);
		this.setStyleName("central-panel");
		this.setWidth((int) (Window.getClientWidth() * Utilities.pageFactor));
		this.setHeight(Window.getClientHeight() - 100);

		seriousGameGrid.setDimension(this.dimensioneForGrid);

		getseriousGameGrid().init();
		TabItem gestioneSGTab = new TabItem(Constants.Strings.sg());
		gestioneSGTab.setId("manageSG");
		gestioneSGTab.setLayout(new FitLayout());
		gestioneSGTab.add(getseriousGameGrid(), new AnchorData("100% 100%"));

		this.add(gestioneSGTab);

	}

	/**
	 * @return the seriousGameGrid
	 */
	public SeriousGameGrid getseriousGameGrid() {
		return seriousGameGrid;
	}

}
