/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analytics.games.client.shortcuts;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author Elion Haxhi
 */
public enum NomiTabele implements IsSerializable {

	prova("prova");

	private String table;

	NomiTabele() {
	}

	NomiTabele(String table) {
		this.table = table;
	}

	@Override
	public String toString() {
		return table;
	}
}
