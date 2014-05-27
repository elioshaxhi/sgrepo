/**
 * Sencha GXT 3.0.1 - Sencha for GWT
 * Copyright(c) 2007-2012, Sencha, Inc.
 * licensing@sencha.com
 *
 * http://www.sencha.com/products/gxt/license/
 */
package org.analytics.games.client.thumbs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface ExampleThumbs extends ClientBundle {

  public static ExampleThumbs THUMBS = GWT.create(ExampleThumbs.class);
ImageResource areachart();

  ImageResource barchart();

  ImageResource barrendererchart();

  ImageResource columnchart();

  ImageResource piechart();

  ImageResource pierendererchart();

  ImageResource stackedbarchart();

  ImageResource tooltipchart();
  ImageResource overview();



}
