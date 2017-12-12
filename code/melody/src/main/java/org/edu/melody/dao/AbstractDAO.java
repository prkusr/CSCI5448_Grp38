package org.edu.melody.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractDAO {
	protected static Connection conn = null;
	private static final Logger logger = LogManager.getLogger(AbstractDAO.class);
	protected static final String jdbc = "jdbc:postgresql://localhost:5432/melody";
	private static final String db = "postgres";
	private static final String pass = "ooad";

	public static Connection getConnection() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(jdbc, db, pass);
				Class.forName("org.postgresql.Driver");
			} catch (Exception e) {
				logger.error(e.getClass().getName() + ": " + e.getMessage());
			}
			logger.info("Opened database successfully");
		}
		return conn;
	}
	
	public boolean isStringEmpty(String s){
		return s == null || s.trim().isEmpty(); 
	}
}
