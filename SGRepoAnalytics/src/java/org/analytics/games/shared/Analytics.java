/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analytics.games.shared;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Elion Haxhi
 */
public class Analytics implements IsSerializable {

	private List<UnicModel> genresList = new LinkedList();
	private List<UnicModel> marketsList = null;
	private List<UnicModel> agesList = null;
	private List<UnicModel> availabilityList = null;
	private List<UnicModel> statusList = null;

	public Analytics() {
	}

	/**
	 * here i 'll add other list
	 */
	public Analytics(List<UnicModel> genresList,List<UnicModel> marketsList,List<UnicModel> agesList,List<UnicModel> availabilityList, List<UnicModel> statusList) {

		this.genresList = genresList;
		this.marketsList = marketsList;
		this.agesList = agesList;
		this.availabilityList = availabilityList;
		this.statusList = statusList;
	}

	/**
	 * @return the ageList
	 */
	public List<UnicModel> getGenresList() {
		return genresList;
	}
	public List<UnicModel> getMarketsList() {
		return marketsList;
	}
	public List<UnicModel> getAgesList() {
		return agesList;
	}
	public List<UnicModel> getAvailabilityList() {
		return availabilityList;
	}
	public List<UnicModel> getStatusList() {
		return statusList;
	}
}
