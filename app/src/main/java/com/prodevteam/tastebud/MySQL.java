package com.prodevteam.tastebud;

import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

    /* variable declarations */
    public static Connection connection;
    public static Statement statement;
    public static int user_key;

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
        ResultSet result;
        String query = "SELECT * FROM Customer_Info where Email = '" + email + "' and Password = '" + password + "' LIMIT 1";
        try {
            result = statement.executeQuery(query);
            if(result.next() == false)
                return null;
            //user_key = result.getInt("ID");
            return result.getString("Name");

        } catch (SQLException e) {
            Log.e("MySQL", "Error:", e);
        }
        return null;
    }

    public boolean checkForDuplicate(String email) throws SQLException {

        String query = "SELECT * FROM Customer_Info where email = '" + email + "'";
        try {
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                return false;
            }
            return true;

        }
        catch (SQLException e) {
            Log.e("MySQL", "Error:", e);
            return false;
        }
    }

    /* body of getMenu method - IN PROGRESS */
    public void getMenu (MenuItem menu) {

        String query = "SELECT * From Menu";
        try {
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Populate menu items
            }
        } catch (SQLException e) {
            Log.e("MySQL", "Error:", e);
        }
    }



    public Boolean createNewAccount(String email, String password, String name) {

        String query = "INSERT INTO Customer_Info (email, password, name) VALUES('" + email + "','" + password + "', '" + name + "')";
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
