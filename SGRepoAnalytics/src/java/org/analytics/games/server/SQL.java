/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.analytics.games.server;

/**
 *
 * @author Elion Haxhi
 */
public class SQL {

//	public static String getGenresAnalytics() {
//		return "SELECT count(seriousgameId), GenreId, vre_game_genres.Name FROM vre.vre_genres_sgs inner join vre.vre_game_genres on vre_game_genres.id = GenreID group by GenreId;";
////	}
	public static String getGenresAnalytics() {
		return "SELECT count(seriousgameId) as somma, GenreId as idTipo, vre_game_genres.Name as nameTipo FROM vre.vre_genres_sgs inner join vre.vre_game_genres on vre_game_genres.id = GenreID group by GenreId;";
	}

	public static String getMarketsAnalytics() {
		return "SELECT count(seriousgameId)  as somma, MarketID as idTipo, vre_markets.Name as nameTipo FROM vre.vre_markets_sgs inner join vre.vre_markets on vre_markets.id = MarketID group by MarketID;";
	}

	public static String getAgesAnalytics() {
		return "SELECT count(seriousgameId)  as somma, AgeID as idTipo, vre_ages.Name as nameTipo FROM vre.vre_ages_sgs inner join vre.vre_ages on vre_ages.id = AgeID group by AgeID;";
	}
//	public static String  getAvailabilityAnalytics(){
//	return "select count(vre_temp.id)  as somma, AvailabilityID as idTipo, vre_availability.Name as nameTipo from vre_temp inner join vre.vre_availability on vre.vre_availability.id = AvailabilityID group by AvailabilityID;";
//
//	}

	public static String getAvailabilityAnalytics() {
		return "select count(vre_serious_games.id) as somma, vre.vre_availability.id as idTipo,vre.vre_availability.name as nameTipo from vre_serious_games inner join vre.vre_availability on vre.vre_availability.id = vre_serious_games.AvailabilityID group by vre_serious_games.AvailabilityID;";

	}

	public static String getStatusAnalytics() {
		return "select count(vre_serious_games.id) as somma, vre.vre_status.id as idTipo, vre.vre_status.name as nameTipo from vre_serious_games inner join vre.vre_status ON vre.vre_status.id = vre_serious_games.StatusID group by vre_serious_games.StatusID;";
	}

}
