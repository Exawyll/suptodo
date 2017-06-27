package fr.imie.suptodo.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class JdbcDao {
	
	private Connection connection;

    protected JdbcDao(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to close the connection", e);
        }
    }
}
