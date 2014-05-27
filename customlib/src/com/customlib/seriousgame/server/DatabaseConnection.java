
package com.customlib.seriousgame.server;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
//import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class DatabaseConnection {
//    private static String URL="jdbc:mysql://localhost/gala_site?user=root&amp;password=";

	private static String URL = "jdbc:mysql://localhost/vre?user=root&amp;password=";
	private static DataSource datasource = null;
	//   private static PoolProperties poolProperties = null;

//    public static Connection getConnection(String realPath) throws SQLException
//    {
//        if(DatabaseConnection.URL.length() == 0)
//            System.out.println("Unable to load driver");
//        Connection connection = null;
//        byte tryCount = 0;
//        while (connection == null)
//        {
//            try
//            {
//                //Class.forName("com.inet.tds.TdsDriver");
// //               Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//
//                Class.forName ("com.mysql.jdbc.Driver").newInstance ();
//            }
//            catch (Exception e)
//            {
//                System.out.println("Unable to load driver");
//            }
//
//            try
//            {
//		//connection = DriverManager.getConnection(URL,"zte_newuser","zte_newuser");
//                connection = DriverManager.getConnection(URL);
//
//            }
//            catch (SQLException ex)
//            {
//                if (++tryCount > 5)
//                {
//                    throw ex;
//                }
//                else
//                {
//                    connection = null;
//                    ex.printStackTrace();
//                    System.out.println("Unable to connect to database. Retrying in 3 seconds...");
//                    try
//                    {
//                        Thread.sleep(3000);
//                    }
//                    catch (InterruptedException iex)
//                    {
//                    }
//                }
//            }
//        }
//
//        return connection;
//    }
        
        //e questo metodo che viene chiamato pper primo poi tramite rpc call viene chiamato i metodo
        // init di serverImpl su tutte e due si Fe Be 
	public static Connection getConnection() throws SQLException {



		if (datasource == null) {
			try {
				//Class.forName("com.mysql.jdbc.Driver");
				Context initCtx = new InitialContext();
				Context envCtx = (Context) initCtx.lookup("java:comp/env");
				datasource = (javax.sql.DataSource) envCtx.lookup("jdbc/GalaDB");

			} catch (	NamingException ex) {

			}
		}



		if (datasource == null) {
			PoolProperties poolProperties = new PoolProperties();
			poolProperties.setUrl("jdbc:mysql://localhost:3306/vre");
			poolProperties.setDriverClassName("com.mysql.jdbc.Driver");
			poolProperties.setUsername("root");
			poolProperties.setPassword("");
			poolProperties.setJmxEnabled(true);
			poolProperties.setTestWhileIdle(false);
			poolProperties.setTestOnBorrow(true);
			poolProperties.setValidationQuery("SELECT 1");
			poolProperties.setTestOnReturn(false);
			poolProperties.setValidationInterval(30000);
			poolProperties.setTimeBetweenEvictionRunsMillis(30000);
			poolProperties.setMaxActive(100);
			poolProperties.setInitialSize(10);
			poolProperties.setMaxWait(10000);
			poolProperties.setRemoveAbandonedTimeout(60);
			poolProperties.setMinEvictableIdleTimeMillis(30000);
			poolProperties.setMinIdle(10);
			poolProperties.setLogAbandoned(true);
			poolProperties.setRemoveAbandoned(true);
			poolProperties.setJdbcInterceptors(
				"org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
				+ "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
			datasource = new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
		}

		Connection connection = null;
		try {
//            DataSource datasource = new DataSource(poolProperties);
			connection = datasource.getConnection();
		} catch (SQLException ex) {
			System.out.println(ex);
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		return connection;
	}
}
