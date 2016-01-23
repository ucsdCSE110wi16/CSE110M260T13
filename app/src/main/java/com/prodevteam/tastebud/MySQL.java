package com.prodevteam.tastebud;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Driver;

public class MySQL {

    /* variable declarations */
    public Connection connection;
    public Statement statement;
    public ResultSet result;

    public void connect(String JDBC, String dbUsername, String dbPassword) {


        try {
            /* connect to the database */
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(JDBC, dbUsername, dbPassword);
            statement = connection.createStatement();
            Log.d("SQL","Login successful");

        } catch (SQLException | NumberFormatException | InstantiationException| IllegalAccessException| ClassNotFoundException e) {
            Log.d("SQL", e.toString());

        }

    }

    public ResultSet executeQuery(String query) throws SQLException {
        return result = statement.executeQuery(query);
    }

    public void executeUpdate(String query) throws SQLException {
        statement.executeUpdate(query);
    }

    public boolean next() throws SQLException {
        return result.next();
    }

    public void close() throws SQLException{
        connection.close();
    }

}
