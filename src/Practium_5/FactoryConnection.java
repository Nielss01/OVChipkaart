package Practium_5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactoryConnection {
    private static Connection connection;

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/ovchip?user=postgres&password=Zu18Inig";
            connection = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException sqlE) {
            System.err.println("[SQLExpection] Couldnt connect with database" + sqlE.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException sqlE) {
            System.err.println("[SQLExpection] Couldnt close the database" + sqlE.getMessage());
        }
    }
}
