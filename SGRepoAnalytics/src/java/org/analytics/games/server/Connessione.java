
package org.analytics.games.server;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class Connessione {

	private static String URL = "jdbc:mysql://localhost/vre?user=root&amp;password=";
	private static DataSource datasource = null;

	public static Connection getConnection() throws SQLException {
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
			datasource = new DataSource(poolProperties);
		}

		Connection connection = null;
		try {
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
