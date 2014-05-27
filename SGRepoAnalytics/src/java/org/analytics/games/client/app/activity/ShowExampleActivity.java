/**
 * Sencha GXT 3.0.1 - Sencha for GWT
 * Copyright(c) 2007-2012, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */

package org.analytics.games.client.app.activity;
import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import org.analytics.games.client.app.place.ExamplePlace;
import org.analytics.games.client.app.ui.ExampleDetailView;
import org.analytics.games.client.model.Example;
import org.analytics.games.client.model.ExampleModel;
import org.analytics.games.client.models.Analytics;

public class ShowExampleActivity extends AbstractActivity {
  @Inject
  private ExampleDetailView detailView;

  @Inject
  private ExampleModel model;

  @Inject
  private PlaceController placeController;

  private String exampleId;


  public String getExampleId() {
    return exampleId;
  }

  public void setExampleId(String exampleId) {
    this.exampleId = exampleId;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    Example example = model.findExample(exampleId);
    if (example != null) {
      detailView.showExample(example);
    } else {
      //TODO this should be checked somewhere else instead?
      Scheduler.get().scheduleEntry(new ScheduledCommand() {
        @Override
        public void execute() {
          placeController.goTo(new ExamplePlace("overview"));
        }
      });
    }
  }



}
