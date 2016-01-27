package com.prodevteam.tastebud;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

    /* variable declarations */
    public static Connection connection;
    public static Statement statement;

    private String DB_USERNAME = "sql3104137";
    private String DB_PASSWORD = "EdL4hLKf6S";
    private String JDBC = "jdbc:mysql://sql3.freesqldatabase.com:3306/sql3104137";

    private int customerCount;

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
        customerCount = 0;
    }

    public String attemptLogin(String email, String password) {
        String query = "select * from Customer_Info where EMAIL = '" + email + "' and password = '" + password + "' limit 1";
        ResultSet results;
        try {
            results = statement.executeQuery(query);
            if(results.next() == false) return null;
            return results.getString("name");
        } catch (SQLException e) {
            Log.e("MySQL", "Error:", e);
        }
        return null;
    }

    public Boolean createNewAccount(String email, String password, String name) {
        String query = "insert into Customer_Info (email, password, name) VALUES('" + email + "','" + password + "', '" + name + "')";
        int results = customerCount;
        try {
            results = statement.executeUpdate(query);
        } catch (SQLException e) {
            Log.e("MySQL", "Error:", e);
        }
        if(results == customerCount + 1) {
            customerCount++;
            return true;
        } else return false;
    }
}
