package com.prodevteam.tastebud;

import android.os.AsyncTask;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.transform.Result;


public class MySQL {

    /* variable declarations */
    public static Connection connection;
    public static Statement statement;
    public static ResultSet result;

    private String DB_USERNAME = "sql3104137";
    private String DB_PASSWORD = "EdL4hLKf6S";
    private String JDBC = "jdbc:mysql://sql3.freesqldatabase.com:3306/sql3104137";

    public void initializeConnection() {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... urls) {
                try {
                    Class.forName("com.mysql.jdbc.Driver").newInstance();
                    connection = DriverManager.getConnection(JDBC, DB_USERNAME, DB_PASSWORD);
                    statement = connection.createStatement();
                } catch (SQLException | NumberFormatException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {}
                return "";
            }}.execute();
    }

    public String attemptLogin(String email, String password) {
        String query = "SELECT * FROM Customer_Info where Email = '" + email + "' and Password = '" + password + "' LIMIT 1";
        try {
            result = executeQuery(query);
            if(next() == false) return null;
            return result.getString("Name");
        } catch (SQLException e) {
            Log.e("MySQL", "Error:", e);
        }
        return null;
    }

    public void executeUpdate(String query) throws SQLException {
        statement.executeUpdate(query);
    }

    public ResultSet executeQuery(String query) throws SQLException {
        return result = statement.executeQuery(query);
    }

    public boolean next() throws SQLException {
        return result.next();
    }


}
