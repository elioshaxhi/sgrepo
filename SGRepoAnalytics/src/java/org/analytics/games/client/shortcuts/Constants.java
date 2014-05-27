/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analytics.games.client.shortcuts;

import org.analytics.games.client.sql.Nomi;
import com.google.gwt.core.client.GWT;

/**
 *
 * @author NERTIL
 */
public class Constants {

	public static double scalePageFactor = 0.90;
	public static double scaleFormFactor = 0.85;
	public static int smallFormWidth = 500;
	public static int smallFormMinWidth = 200;
	public static int varCharLimitShort = 50;
	public static int fullScreenWidth = 1920;
	public static Nomi Strings = GWT.create(Nomi.class);
}
