package ru.shapovalov.Class;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Created by igor on 01.04.15.
 */
public class DataBaseConection {
    public DataBaseConection() {
        Locale.setDefault(Locale.ENGLISH);

    }
    public static Connection dbconection() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String username = "violet";
        String password = "violet";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}