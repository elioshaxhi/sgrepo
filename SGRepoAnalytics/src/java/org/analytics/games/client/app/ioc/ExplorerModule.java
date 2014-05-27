/**
 * Sencha GXT 3.0.1 - Sencha for GWT
 * Copyright(c) 2007-2012, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */

package org.analytics.games.client.app.ioc;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.analytics.games.client.app.mvp.ExplorerActivityMapper;
import org.analytics.games.client.app.ui.ExampleDetailView;
import org.analytics.games.client.app.ui.ExampleDetailViewImpl;
import org.analytics.games.client.app.ui.ExampleListView;
import org.analytics.games.client.app.ui.ExampleListViewImpl;
import org.analytics.games.client.model.ExampleModel;
import org.analytics.games.shared.ExplorerRequestFactory;


public class ExplorerModule extends AbstractGinModule  {

  @Override
  protected void configure() {
    bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

    bind(ActivityMapper.class).to(ExplorerActivityMapper.class);

    // bind up some data
    bind(ExampleModel.class).in(Singleton.class);

    // View implementation binding - if we build a mobile explorer, this would
    // be broken out into another module so it could be rebound
    bind(ExampleListView.class).to(ExampleListViewImpl.class).in(Singleton.class);
    bind(ExampleDetailView.class).to(ExampleDetailViewImpl.class).in(Singleton.class);
  }

  @SuppressWarnings("deprecation")
  @Provides
  @Singleton
  PlaceController providePlaceController(EventBus eventBus) {
    return new PlaceController(eventBus);
  }

  @Provides
  @Singleton
  ExplorerRequestFactory provideRequestFactory(EventBus eventBus) {
    ExplorerRequestFactory rf = GWT.create(ExplorerRequestFactory.class);
    rf.initialize(eventBus);
    return rf;
  }


}
