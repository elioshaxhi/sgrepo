/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elios.seriousgames;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Nertil
 */
public class Database {

	public static Connection getConnectionNDb() {
		Connection connection = null;
		try {

			InitialContext initialContext = new InitialContext();
			Context context = (Context) initialContext.lookup("java:comp/env");
			//The JDBC Data source that we just created
			DataSource ds = (DataSource) context.lookup("jdbc/new_db");
			connection = ds.getConnection();

			if (connection == null) {
				throw new SQLException("Error establishing connection!");
			}
		} catch (NamingException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}
		return connection;
	}

	public static Connection getConnectionSGS() {
		Connection connection = null;
		try {

			InitialContext initialContext = new InitialContext();
			Context context = (Context) initialContext.lookup("java:comp/env");
			//The JDBC Data source that we just created
			DataSource ds = (DataSource) context.lookup("jdbc/sgs_users_service");
			connection = ds.getConnection();

			if (connection == null) {
				throw new SQLException("Error establishing connection!");
			}
		} catch (NamingException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}
		return connection;
	}

	/**
	 * se ottengo una righa dalla tabela sg_application ,allora lo verifico
	 * usanto la cifratura MD5 e se la cirfature e valida allora ,proseguo
	 * ricavanto una riga dalla tabela sg_application_session se lo
	 * ottengo(la righa) allora ritorno la key se invece ho una riga nulla
	 * relativa la id della tab sg_application allora lo creo e lo ritorno
	 * questa key_session servira per il metodo
	 * chekUser(key_sessio,unsername)
	 *
	 * se invece non ottengho la righa dalla tabella sg_application allora
	 * ritorna key_session=null
	 *
	 *
	 *
	 *
	 * @param appname
	 * @param password
	 * @return Stringa
	 */
	public static String getAccess(String appname, String password) {
		String key_session = null;
		String query = QueryWs.getApp(appname);
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		try {
			connection = Database.getConnectionSGS();
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			int app_id = 0;
			if (result.next()) {
				app_id = result.getInt("id");
				String pw = result.getString("password");
				if (!Joomla15PasswordHash.check(password, pw)) {
					return null;// pasword errato
				}
			} else {
				return null;
			}
			statement.close();
			result.close();

			query = QueryWs.getSessionId(app_id);
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			while (result.next()) {
				key_session = result.getString("key_session");
				break;
			}
			statement.close();
			result.close();
			if (key_session == null) {
				statement = connection.createStatement();
				key_session = UUID.randomUUID().toString();
				query = QueryWs.setSessionId(app_id, key_session);
				statement.executeUpdate(query);
				statement.close();
			}

		} catch (Exception e) {
			System.out.println("query error: " + e.getMessage());
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
		return key_session;
	}

	/**
	 * questo metodo viene utilizato al inreno del metodo checkUser esegue
	 * una query sulla tabela sg_application_session se la righa non e
	 * null(per righa si intende il valore del key_session preso da tale
	 * righa) ritorna un true ,ne altro caso ritorna false
	 *
	 * @param key_session
	 * @return boolean
	 */
	public static boolean verifyKey(String key_session) {
		String retval = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		String query = QueryWs.getSessionKey(key_session);

		try {
			connection = Database.getConnectionSGS();
			statement = connection.createStatement();
			result = statement.executeQuery(query);

			while (result.next()) {
				retval = result.getString("key_session");
				break;
			}
			statement.close();
			result.close();
			if (retval == null) {
				return false;
			}

		} catch (Exception e) {
			System.out.println("query error: " + e.getMessage());
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
		return true;
	}

	/**
	 * allora prima si verifica la key_session se torna true proseggua verso
	 * la verifica del utente cifro la passuor con MD4 e lo verifico se il
	 * return e true allora ritorno un boolean true do il via libera al
	 * utente di utilizare l'app di riferimento
	 *
	 * se la verifica mi torna un false allora nego l'accesso al utente per
	 * l'app richiesta idem anche per la verifica del utente se tale
	 * verifica non e soddisfata allore torno un false e negho l'accesso
	 * ,stesso ragionamento anche per il caso del utente non presente nella
	 * tabella jos_users
	 *
	 * @param key_session
	 * @param username
	 * @param password
	 * @return
	 */
	public static Boolean checkUser(String key_session, String username, String password) {
		String retPass = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		Boolean ok = null;


		if (verifyKey(key_session)) {


			try {
				String query = QueryWs.getUser(username);
				connection = Database.getConnectionNDb();
				statement = connection.createStatement();
				result = statement.executeQuery(query);

				while (result.next()) {
					retPass = result.getString("password");
					if (!Joomla15PasswordHash.check(password, retPass)) {
						ok = false;// pasword errato
					} else {
						ok = true;
					}
					break;
				}
				statement.close();
				result.close();
				if (retPass == null) {
					ok = false;
				}

			} catch (Exception e) {
				System.out.println("query error: " + e.getMessage());
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

		} else {
			ok = false;
		}
		return ok;
	}

	/**
	 * interrogo la tb sg_application_session se la chiave e prezente allora
	 * interrogo la tb jos_user tirando tutte le informazione del username
	 * uso la classe XmlParser trasformo la stringha ottenuta
	 *
	 * come avviene il parsing xml: allora per ogni righa ottenuto dalla tb
	 * jos_user creo un ogetto User e lo aggiungho nella lista "l". Fuori
	 * loop gli passo alla classe XmlPrser tale lista che ritornera una
	 * stringha in formato xml.
	 *
	 * se invece la verifyKey mi torna un false allra torno una stringha xml
	 * pero con zero nodi(User)
	 *
	 * @param key_session
	 * @param username
	 * @return
	 */
	public static String getUserInfo(String key_session, String username) {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		Boolean ok = null;
		User user = null;
		String parser = "";
			String lastVisitDateString;
			String lastRegisterDateString;
		List<User> l = new LinkedList<User>();


		if (verifyKey(key_session)) {


			try {
				String query = QueryWs.getUser(username);
				connection = Database.getConnectionNDb();
			
				statement = connection.createStatement();
				result = statement.executeQuery(query);
				Date register = null;
				while (result.next()) {

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy

					Timestamp lastVisitTime = result.getTimestamp("lastvisitDate");
					Timestamp lastVisitTimeRegister = result.getTimestamp("registerDate");
					java.util.Date lastVisitDate = new java.util.Date();
					java.util.Date lastRegisterDate = new java.util.Date();

	//				lastVisitDate.setTime(lastVisitTime.getTime());
	//				lastRegisterDate.setTime(lastVisitTimeRegister.getTime());



					if(lastVisitTime==null)
						lastVisitDateString="0000-00-00 00:00:00";
					else{
						lastVisitDate.setTime(lastVisitTime.getTime());
						lastVisitDateString = sdf.format(lastVisitDate);
					}
						
					if(lastVisitTimeRegister==null)
						lastRegisterDateString="0000-00-00 00:00:00";
					else{	
						lastRegisterDate.setTime(lastVisitTimeRegister.getTime());
						lastRegisterDateString = sdf.format(lastRegisterDate);
					}








					user = new User(result.getInt("id"), result.getString("name"), result.getString("username"), result.getString("email"), result.getString("password"), result.getString("usertype"), result.getInt("block"), result.getInt("sendEmail"), result.getInt("gid"), lastRegisterDateString, lastVisitDateString, result.getString("activation"), result.getString("params"));
					l.add(user);
					XmlParser xml = new XmlParser();
					parser = xml.getXML(l);

					break;
				}
				statement.close();
				result.close();

			} catch (Exception e) {
				System.out.println("query error: " + e.getMessage());
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

		}
		return parser;
	}

	/**
	 * interrogo la tb sg_application_session se la chiave e prezente allora
	 * interrogo la tb jos_user tirando tutte le informazione del username
	 * uso la classe XmlParser trasformo la stringha ottenuta
	 *
	 * come avviene il parsing xml: allora per ogni righa(use una query che
	 * ottiene gli utenti recenti) ottenuto dalla tb jos_user creo un ogetto
	 * User e lo aggiungho nella lista "l". Fuori loop gli passo alla classe
	 * XmlPrser tale lista che ritornera una stringha in formato xml.
	 *
	 * se invece la verifyKey mi torna un false allra torno una stringha xml
	 * pero con zero nodi(User)
	 *
	 * @param key_session
	 * @param lastUpdateTime
	 * @return
	 */
	public static String getLastVisitUser(String key_session, String lastUpdateTime) {
		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		Boolean ok = null;
		String parser = "";
		List<User> l = new LinkedList<User>();
		String lastVisitDateString;
		String lastRegisterDateString;
		if (verifyKey(key_session)) {


			try {
				String query = QueryWs.getLastUser(lastUpdateTime);
				connection = Database.getConnectionNDb();
				statement = connection.createStatement();
				result = statement.executeQuery(query);

				XmlParser xml = new XmlParser();
				String time = null;
				while (result.next()) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy

					Timestamp lastVisitTime = result.getTimestamp("lastvisitDate");
					Timestamp lastVisitTimeRegister = result.getTimestamp("registerDate");
					java.util.Date lastVisitDate = new java.util.Date();
					java.util.Date lastRegisterDate = new java.util.Date();


					if (lastVisitTime == null) {
						lastVisitDateString = "0000-00-00 00:00:00";
					} else {
						lastVisitDate.setTime(lastVisitTime.getTime());
						lastVisitDateString = sdf.format(lastVisitDate);
					}

					if (lastVisitTimeRegister == null) {
						lastRegisterDateString = "0000-00-00 00:00:00";
					} else {
						lastRegisterDate.setTime(lastVisitTimeRegister.getTime());
						lastRegisterDateString = sdf.format(lastRegisterDate);
					}



					User user = new User(result.getInt("id"), result.getString("name"), result.getString("username"), result.getString("email"), result.getString("password"), result.getString("usertype"), result.getInt("block"), result.getInt("sendEmail"), result.getInt("gid"), lastRegisterDateString, lastVisitDateString, result.getString("activation"), result.getString("params"));
					l.add(user);

				}
				if (!l.isEmpty()) {
					parser = xml.getXML(l);
				}
				statement.close();
				result.close();

			} catch (Exception e) {
				System.out.println("query error: " + e.getMessage());
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

		}
		return parser;

	}
}
