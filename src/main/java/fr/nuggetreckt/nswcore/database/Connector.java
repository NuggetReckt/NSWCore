package fr.nuggetreckt.nswcore.database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

    private Connection conn;

    private final String user;
    private final String password;

    public Connector() {
        Dotenv dotenv = Dotenv.configure()
                .directory("/env/")
                .filename(".env")
                .load();

        user = dotenv.get("DB_USER");
        password = dotenv.get("DB_PASSWORD");

        connect();
    }

    public void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/Core", user, password);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    public boolean isConnected() {
        try {
            return conn.isValid(1000);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    public Connection getConn() {
        return conn;
    }
}