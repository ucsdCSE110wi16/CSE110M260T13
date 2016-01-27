package com.prodevteam.tastebud;

import android.content.Context;
import android.database.CursorJoiner;
import android.graphics.Interpolator;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MySQL {

    /* variable declarations */
    public static Connection connection;
    public static Statement statement;

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

    public String getNameMatchingEmail(final String email) {
        String query = "select name from Customer_Info where EMAIL = '" + email + "'";
        ResultSet results;
        String name = "";
        try {
            results = statement.executeQuery(query);
            while(results.next())
                name = results.getString("name");
        } catch(SQLException ex) {
            Log.e("MySQL", "Error:", ex);
        }
        return name;
    }

    public boolean attemptLogin(String email, String password) {
        String query = "select password from Customer_Info where EMAIL = '" + email + "'";
        ResultSet results;
        try {
            results = statement.executeQuery(query);
            while(results.next())
                if(password.equals(results.getString("password"))) return true;
            return false;
        } catch (SQLException e) {
            Log.e("MySQL", "Error:", e);
        }
        return false;
    }

    public void executeUpdate(String query) throws SQLException {
        statement.executeUpdate(query);
    }
}
