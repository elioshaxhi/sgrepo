/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analytics.games.client.form;

import com.sencha.gxt.chart.client.draw.Gradient;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.Stop;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Elion Haxhi
 */
public class Colori {

	public static Gradient[] getColor() {

		Gradient [] retval=new Gradient[11];

		Gradient slice1 = new Gradient("slice1", 45);
		slice1.addStop(new Stop(0, new RGB(148, 174, 10)));
		slice1.addStop(new Stop(100, new RGB(107, 126, 7)));
		retval[0]=slice1;

		Gradient slice2 = new Gradient("slice2", 45);
		slice2.addStop(new Stop(0, new RGB(17, 95, 166)));
		slice2.addStop(new Stop(100, new RGB(12, 69, 120)));
		retval[1]=slice2;

		Gradient slice3 = new Gradient("slice3", 45);
		slice3.addStop(new Stop(0, new RGB(166, 17, 32)));
		slice3.addStop(new Stop(100, new RGB(120, 12, 23)));
		retval[2]=slice3;

		Gradient slice4 = new Gradient("slice4", 45);
		slice4.addStop(new Stop(0, new RGB(255, 136, 9)));
		slice4.addStop(new Stop(100, new RGB(213, 110, 0)));
		retval[3]=slice4;

		Gradient slice5 = new Gradient("slice5", 45);
		slice5.addStop(new Stop(0, new RGB(255, 209, 62)));
		slice5.addStop(new Stop(100, new RGB(255, 197, 11)));
		retval[4]=slice5;

		Gradient slice6 = new Gradient("slice6", 45);
		slice6.addStop(new Stop(0, new RGB(166, 17, 135)));
		slice6.addStop(new Stop(100, new RGB(120, 12, 97)));
		retval[5]=slice6;

		Gradient slice7 = new Gradient("slice7", 45);
		slice7.addStop(new Stop(0, new RGB(17, 95, 166)));
		slice7.addStop(new Stop(100, new RGB(12, 69, 120)));
		retval[6]=slice7;

		Gradient slice8 = new Gradient("slice8", 45);
		slice8.addStop(new Stop(0, new RGB(17, 95, 166)));
		slice8.addStop(new Stop(100, new RGB(12, 69, 120)));
		retval[7]=slice8;

		Gradient slice9 = new Gradient("slice9", 45);
		slice9.addStop(new Stop(0, new RGB(148, 174, 10)));
		slice9.addStop(new Stop(100, new RGB(107, 126, 7)));
		retval[8]=slice9;

		Gradient slice10 = new Gradient("slice10", 45);
		slice10.addStop(new Stop(0, new RGB(17, 95, 166)));
		slice10.addStop(new Stop(100, new RGB(12, 69, 120)));
		retval[9]=slice10;

		Gradient slice11 = new Gradient("slice11", 45);
		slice11.addStop(new Stop(0, new RGB(17, 95, 166)));
		slice11.addStop(new Stop(100, new RGB(12, 69, 120)));
		retval[10]=slice11;
		return retval;

	}

}
