package config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DatabaseConfig {
	
	private static DataSource dataSource;
	
	static {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/connection");
		} catch (Exception e) {
			throw new RuntimeException("Database configuration failed", e);
		}
	}
	
	public static DataSource getDataSource() {
		return dataSource;
	}
}
