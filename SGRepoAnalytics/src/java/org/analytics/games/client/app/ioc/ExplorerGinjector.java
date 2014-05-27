/**
 * Sencha GXT 3.0.1 - Sencha for GWT
 * Copyright(c) 2007-2012, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */

package org.analytics.games.client.app.ioc;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import org.analytics.games.client.ExplorerApp;

@GinModules(ExplorerModule.class)
public interface ExplorerGinjector extends Ginjector {

  ExplorerApp getApp();
}
