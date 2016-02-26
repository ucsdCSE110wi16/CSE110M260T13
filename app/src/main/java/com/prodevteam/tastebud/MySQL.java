package com.prodevteam.tastebud;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQL {

    /* variable declarations */
    public static Connection connection;
    public static Statement statement;
    public static int user_key;

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

    public App.UserInfo attemptLogin(String email, String password) {
        ResultSet result;
        String query = "SELECT * FROM Customer_Info where Email = '" + email + "' and Password = '" + password + "' LIMIT 1";
        try {
            result = statement.executeQuery(query);
            if(!result.next())
                return null;
            //user_key = result.getInt("ID");
            String name = result.getString("Name");
            String restrictions = result.getString("Restrictions");

            App.UserInfo user = new App.UserInfo(name, email, password, restrictions);
            return user;

        } catch (SQLException e) {
            Log.e("MySQL", "Error:", e);
        }
        return null;
    }

    public String[] attemptEmployeeLogin(String email, String password) {
        ResultSet result;
        String query = "SELECT * FROM EmployeeRecords where EmployeeEmail = '" + email + "' and EmployeePassword = '" + password + "' LIMIT 1";
        String[] employeeInfo = new String[2];
        try {
            result = statement.executeQuery(query);
            if(!result.next())
                return null;
            String restaurantName = result.getString("EmployeeRestaurant");

            employeeInfo[0] = email;
            employeeInfo[1] = restaurantName;

            return employeeInfo;
        } catch (SQLException e) {
            Log.e("MySQL", "Error:", e);
        }
        return null;
    }

    public Boolean createNewAccount(String email, String password, String name) {

        String query = "INSERT INTO Customer_Info (email, password, name) VALUES('" + email + "','" + password + "', '" + name + "')";
        try {
            if (statement.execute(query)){
                return true;
            }
            return false;
        } catch (SQLException e) {
            Log.e("MySQL", "Error:", e);
            return false;
        }
    }

    public boolean checkForDuplicate(String email) {

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
    public ArrayList<MenuData> getMenu (String restaurantName) {

        String query = "SELECT * From " + restaurantName + "_menu";
        ArrayList<MenuData> listMenuItems = new ArrayList<>();

        try {
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {

                /* initialize and store values into item */
                String name = result.getString("Item_Name");
                String price = result.getString("Item_Price");
                String major_ings = result.getString("Major_Item_Ings");
                String minor_ings = result.getString("Minor_Item_Ings");
                String img = result.getString("Item_Img");

                listMenuItems.add(new MenuData(name, price, major_ings, minor_ings, img));
            }
        } catch (SQLException e) {
            Log.e("MySQL", "Error:", e);
        }

        /* return list of menu item */
        return listMenuItems;
    }

    /* method to get order ings for specified email */
    public String getUserIngs(String email) {

        String orderIngs = "";
        String query = "SELECT Ings_In_Order from Order_History where customer_email = '" + email + "'";
        try{
            ResultSet result = statement.executeQuery(query);
            while (result.next()){
                orderIngs += ", " + result.getString("Ings_In_Order");
            }
        }
        catch (SQLException e){
            Log.e("MySQL", "Error:", e);
            return "";
        }
        return orderIngs;
    }

    /* method to get user restrictions */
    public String getUserRestrictions(String email){
        String restrictions = "";
        String query = "SELECT Restrictions from Customer_Info where email = '" + email + "'";
        try{
            ResultSet result = statement.executeQuery(query);
            if (result.next()){
                restrictions =  result.toString();
            }
        }
        catch (SQLException e){
            Log.e("MySQL", "Error:", e);
            return "";
        }
        return restrictions;
    }

    /* method to update user information */
    /* information are updated based on the email address provided */
    /* error checking such as empty name/password field name must be handled in the IU */

    public boolean updateUserInfo (String email, String name, String password, String restrictions){
        String query = "UPDATE Customer_Info set " +
                "Name ='" + name + "', " +
                "Password = '" + password + "', " +
                "Restrictions = '"+ restrictions + "' " +
                "where Email = '" + email +"'";
        try {
            statement.execute(query);
        } catch (SQLException e) {
            Log.e("MySQL", "Error:", e);
            return false;
        }
        return true;
    }

    public boolean updateUserInfo(App.UserInfo user) {
        return updateUserInfo(user.getEmailAddress(), user.getName(), user.getPassword(), user.getRestrictions());
    }

    /* method to store orders into database */
    public boolean placeOrder (String restaurantName, ArrayList<MenuData> items){
        String ings = "";
        for(MenuData m : items)
            ings += m.getMajorIngs() + ", " + m.getMinorIngs() + ", ";
        App.currentUser.addPastIngredient(ings);

        String query = "INSERT INTO Order_History values ('"+ App.currentUser.getEmailAddress() +"', '" + ings + "')";
        try{
            if (statement.execute(query))
                return true;
            return false;
        }
        catch (SQLException e){
            Log.e("MySQL", "Error:", e);
            return false;
        }
    }

    public boolean addOrders(String restaurantName, ArrayList<MenuData> items) {
        String query = "INSERT INTO " + restaurantName + "_orders VALUES ";
        for(MenuData m : items)
            query += "('" + m.getName() + "', '" + m.getPrice() + "', '" + App.currentUser.getName() + "'), ";
        query = query.substring(0, query.length() - 2);
        try{
            if (statement.execute(query))
                return true;
            return false;
        }
        catch (SQLException e){
            Log.e("MySQL", "Error:", e);
            return false;
        }
    }

    public boolean removeOrders(String restaurantName, ArrayList<EmployeeActivity.OrderData> orders) {
        for(EmployeeActivity.OrderData item : orders) {
            String query = "DELETE FROM " + restaurantName + "_orders WHERE Item_Name = '" + item.getName() + "' AND Customer_Name = '" + item.getCustomerName() + "'";
            try {
                statement.execute(query);
            } catch (SQLException e) {
                Log.e("MySQL", "Error:", e);
                return false;
            }
        }
        return true;
    }

    public ArrayList<MenuData> makeRec(String restaurantName) {

        String[]userIngs = App.sqlConnection.getUserIngs(App.currentUser.getEmailAddress()).toString().split(",");
        ArrayList<MenuData> recMenu = getMenu(restaurantName);

        if (userIngs.length == 1 && userIngs[0] == "") {
            return recMenu;
        }

        Map<String,Integer> itemRank = new HashMap<>();
        ArrayList<MenuData> sortedRecMenu = new ArrayList<>();
        int score = 0;

        for (MenuData item : recMenu) {
            String[] majorIngs = (item.getMajorIngs()).split(",");
            String[] minorIngs = (item.getMinorIngs()).split(",");

            for (String ing : userIngs) {
                for (String MI : majorIngs) {
                    if (MI.trim().equalsIgnoreCase(ing.trim())) {
                        score = score + 2;
                    }
                }
                for (String MI : minorIngs) {
                    if (MI.trim().equalsIgnoreCase(ing.trim())) {
                        score = score + 1;
                    }
                }

            }
            itemRank.put(item.getName(), score);
            score = 0;
        }

        List list = sortByScore(itemRank);

        for (Object i : list) {
            for (MenuData item : recMenu) {
                if (i.toString().contains(item.getName())) {
                    sortedRecMenu.add(recMenu.get(recMenu.indexOf(item)));
                    break;
                }
            }
        }

        return sortedRecMenu;
    }

    static <K, V extends Comparable<? super V>>
    List<Map.Entry<K, V>> sortByScore(Map<K, V> map) {
        List<Map.Entry<K, V>> sortedEntries = new ArrayList<>(map.entrySet());
        Collections.sort(sortedEntries, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                return e2.getValue().compareTo(e1.getValue());
            }
        });
        return sortedEntries;
    }

    public ArrayList<EmployeeActivity.OrderData> getPendingOrders(String restaurantName) {
        ArrayList<EmployeeActivity.OrderData> list = new ArrayList<>();
        String query = "SELECT * From " + restaurantName + "_orders";

        try {
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {

                /* initialize and store values into item */
                String name = result.getString("Item_Name");
                String price = result.getString("Item_Price");
                String cust_name = result.getString("Customer_Name");

                list.add(new EmployeeActivity.OrderData(name, price, cust_name));
            }
        } catch (SQLException e) {
            Log.e("MySQL", "Error:", e);
        }
        return list;
    }


}