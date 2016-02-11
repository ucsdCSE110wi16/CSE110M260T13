package com.prodevteam.tastebud;

import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

    // TODO: MenuData class to hold info from the db and pass to MenuScreen

    public App.UserInfo attemptLogin(String email, String password) {
        ResultSet result;
        String query = "SELECT * FROM Customer_Info where Email = '" + email + "' and Password = '" + password + "' LIMIT 1";
        try {
            result = statement.executeQuery(query);
            if(!result.next())
                return null;
            //user_key = result.getInt("ID");
            String[] names = result.getString("Name").split(" ");
            String fname = names[0];
            String lname = "";

            App.UserInfo user = new App.UserInfo(fname, lname, email, password);
            return user;

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

    /*  - Using MenuData.java, getMenu() returns an arrayList of menu items in the format
        ["Item Name", "Item Ing", "Item_Price", "Item_Img"] for each item.

        - getMenu() should be called before or immediately after the user logs in.

        HOW TO USE:
        - Store getMenu()'s return into a local ArrayList<MenuData> variable, we will call it MenuItems.
        - To access elements stored in MenuItems (done inside of MenuScreen.java):
        for (int i = 0; i < MenuItems.size(); i++) {
           menuItem.setItemName(MenuItems.get(i).getName());
           menuItem.setItemIng(MenuItems.get(i).getIng());
           etc...
        }
    */
    public ArrayList<MenuData> getMenu () {

        String query = "SELECT * From Menu_Items";
        ArrayList<MenuData> listMenuItems = new ArrayList<>();

        try {
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {

                /* initialize and store values into item */
                String name = result.getString("Item_Name");
                String ing = result.getString("Item_Ing");
                String price = result.getString("Item_Price");
                String img = result.getString("Item_Img");
                MenuData item = new MenuData(name, price, ing, img);

                /* add item into ListMenuItem */
                listMenuItems.add(item);

            }
        } catch (SQLException e) {
            Log.e("MySQL", "Error:", e);
        }

        /* return list of menu item */
        return listMenuItems;
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
