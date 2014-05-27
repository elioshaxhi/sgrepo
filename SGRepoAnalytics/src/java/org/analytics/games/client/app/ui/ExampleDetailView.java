/**
 * Sencha GXT 3.0.1 - Sencha for GWT
 * Copyright(c) 2007-2012, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package org.analytics.games.client.app.ui;

import com.google.gwt.user.client.ui.IsWidget;
import org.analytics.games.client.model.Example;
import org.analytics.games.client.models.Analytics;

public interface ExampleDetailView extends IsWidget {

  public interface Presenter {
    void selectExample(Example ex);
  }
  void setPresenter(Presenter listener);

  /**
   * Focuses on the given example
   *
   * @param example
   */
  void showExample(Example example);
}
