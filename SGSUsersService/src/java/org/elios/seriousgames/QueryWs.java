/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elios.seriousgames;

/**
 *
 * @author ElionHaxhi
 */
public class QueryWs {

	public static String getApp(String appname) {
		return "SELECT * FROM " + DbTables.app + " where " + AppTag.app_name + "='" + appname + "'";
	}

	public static String getSessionId(int app_id) {
		return "SELECT * FROM " + DbTables.app_session + " where " + AppSession.app_id + " = " + app_id;
	}

	public static String setSessionId(int app_id, String key_session) {
		return "INSERT INTO " + DbTables.app_session + " VALUES (NULL, " + app_id + ", '" + key_session + "')";
	}

	public static String getSessionKey(String key_session) {
		return "SELECT * FROM " + DbTables.app_session + " where " + AppSession.key_session + " ='" + key_session + "'";
	}

	public static String getUser(String username) {
	return "SELECT * FROM " + DbTables.user + " WHERE " + User.tagUsername + "='"+username+"';";
	}

	public static String getLastUser(String lastUpdateTime) {
		return "SELECT * FROM " + DbTables.user + " WHERE (" + User.tagLastvisitDate + ">'" + lastUpdateTime + "') or (" + User.tagLastvisitDate + "=" + "'0000-00-00 00:00:00')";

	}
}
