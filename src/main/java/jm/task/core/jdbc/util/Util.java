package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/testbase";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "i@dijwfdqa2";
    private static Connection connection;
    private static Statement statement;

    public static Statement getStatement() {
        return statement;
    }

    public static void getMyConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            statement = connection.createStatement();
            if (!connection.isClosed()) {
                System.out.println("Correct connection to DB!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeMyConnection() {
        try {
            statement.close();
            connection.close();
            if (connection.isClosed()) {
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
