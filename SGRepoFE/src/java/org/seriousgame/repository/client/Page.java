/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seriousgame.repository.client;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;
import com.google.gwt.user.client.Element;


/**
 *
 * @author NERTIL
 */
public class Page extends LayoutContainer {

    private MainEntryPoint entrypoint;
    private HeaderPanel headerPanel;
    private FooterPanel footerPanel;
    private SeriousGamePanel lastSeriousGamePanel;
    private SearchPanel searchPanel;
//test
    public Page(MainEntryPoint entrypoint) {
        this.entrypoint = entrypoint;
        headerPanel = new HeaderPanel(this.entrypoint);
        lastSeriousGamePanel = new SeriousGamePanel(entrypoint);
        footerPanel = new FooterPanel(this.entrypoint);
        searchPanel = new SearchPanel(entrypoint);

        setStyleName("page");
    }

    @Override
    protected void onRender(Element parent, int index) {
        super.onRender(parent, index);

        VBoxLayout layout = new VBoxLayout();
        layout.setVBoxLayoutAlign(VBoxLayout.VBoxLayoutAlign.STRETCH);
        setLayout(layout);

        VBoxLayoutData flex = new VBoxLayoutData(new Margins(0));
        flex.setFlex(1);

        VBoxLayoutData noflex = new VBoxLayoutData(new Margins(0));
        noflex.setFlex(0);

        add(getHeaderPanel(), noflex);
//        add(getSearchPanel(), flex);
        add(getLastSeriousGamePanel(), flex);
        add(getFooterPanel(), noflex);
    }

    public HeaderPanel getHeaderPanel() {
        return headerPanel;
    }

    public FooterPanel getFooterPanel() {
        return footerPanel;
    }

    public SeriousGamePanel getLastSeriousGamePanel() {
        return lastSeriousGamePanel;
    }

    public SearchPanel getSearchPanel() {
        return searchPanel;
    }
}
