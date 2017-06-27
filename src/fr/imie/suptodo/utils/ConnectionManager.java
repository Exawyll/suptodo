package fr.imie.suptodo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static final String URL = "jdbc:mysql://localhost/suptodo";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static Connection connection;

	public static Connection connectDB() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch(SQLException e) {

				throw new RuntimeException("Unable to create connection with database (url: " + URL + ", user: " + USER
						+ ", password: " + PASSWORD + ")", e);
			}
		}		
		return connection;
	}
}
