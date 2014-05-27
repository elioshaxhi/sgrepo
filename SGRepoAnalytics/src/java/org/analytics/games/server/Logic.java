/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.analytics.games.server;
import org.analytics.games.client.models.UnicModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import org.analytics.games.client.models.Analytics;

/**
 *
 * @author Elion Haxhi
 */
public class Logic {

	private static String servletContext = "";
	private static int MaxRow = 1000;

	public static int getMaxRow() {
		return MaxRow;
	}

	public static void setMaxRow(int MaxRow) {
		Logic.MaxRow = MaxRow;
	}

	public static int executeSql(String query) {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		int retval = 0;
		try {
			connection = Connessione.getConnection();

			statement = connection.createStatement();
			retval = statement.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("VRE DB query error: " + e.getMessage());
			System.out.println(query);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}
		return retval;
	}

	/**
	 * @param servletContext the servletContext to set
	 */
	public static void setServletContext(String servletContext) {
		Logic.servletContext = servletContext;
		setMaxRow(100);
	}

	/**
	 * adding code for ordering items arriving from db, i got this item in
	 * order and then, insert the Other item at the end of the arraylist.
	 */

	public static List<UnicModel> getAllLists(String query) {

		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		List<UnicModel> retval = new LinkedList<UnicModel>();
			try {
				connection = Connessione.getConnection();
				statement = connection.createStatement();
					result = statement.executeQuery(query);
				//Logic goes here
				int limit = 0;
				while (result.next() && ++limit < getMaxRow()) {
					int num = result.getInt("somma");
					int id = result.getInt("idTipo");
					String name = result.getString("nameTipo");
						retval.add(new UnicModel(num, id, name,String.valueOf(num) ));


				}
			} catch (Exception e) {
				System.out.println("VRE DB query error: " + e.getMessage());
				//System.out.println(query);
			} finally {
				if (result != null) {
					try {
						result.close();
					} catch (Exception ex) {
						System.out.println(ex);
					}
				}
				if (statement != null) {
					try {
						statement.close();
					} catch (Exception ex) {
						System.out.println(ex);
					}
				}
				if (connection != null) {
					try {
						connection.close();
					} catch (Exception ex) {
						System.out.println(ex);
					}
				}
			}
		return retval;
	}



	public static Analytics getSGSAnalytics() {
		Analytics analytics=null;
		List<UnicModel> genres=getAllLists(SQL.getGenresAnalytics());
		List<UnicModel> markets=getAllLists(SQL.getMarketsAnalytics());
		List<UnicModel> ages=getAllLists(SQL.getAgesAnalytics());
		List<UnicModel> avaiabilities=getAllLists(SQL.getAvailabilityAnalytics());
		List<UnicModel> status=getAllLists(SQL.getStatusAnalytics());

		analytics = new Analytics(genres,markets,ages,avaiabilities,status);
		return analytics;
	}


}

