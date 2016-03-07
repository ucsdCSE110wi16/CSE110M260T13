package com.prodevteam.tastebud;

import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;

public class App extends Application {
    protected static MySQL sqlConnection;
    protected static UserInfo currentUser;
    protected static App myApp;
    static {
        currentUser = new UserInfo("", "", "", "", "");
    }
    protected static final String TOKEN = "";
    protected static final String PUSH_NOTIFICATION_SERVER_URL = "";

    @Override
    public void onCreate() {
        super.onCreate();

        if(myApp == null)
            myApp = this;
        sqlConnection = new MySQL();
        sqlConnection.initializeConnection();
    }

    public static class UserInfo {
        private String name;
        private String emailAddress;
        private String password;
        private String restrictions;
        private String pastIngredients;
        private String token;

        public UserInfo(String name, String emailAddress, String password, String restrictions, String token) {
            this.name = name;
            this.emailAddress = emailAddress;
            this.password = password;
            this.restrictions = restrictions;
            this.token = "";
        }

        public String getPastIngredients() {
            if (pastIngredients.startsWith(", ")){
                pastIngredients = pastIngredients.substring(2);
            }
            return pastIngredients;
        }

        public String addPastIngredient(String newIngredient) {
            pastIngredients += ", " + newIngredient;
            if (pastIngredients.startsWith(", ")){
                pastIngredients = pastIngredients.substring(2);
            }
            return pastIngredients;
        }

        public void setPastIngredients(String pastIngredients) {
            if (pastIngredients.startsWith(", ")){
                pastIngredients = pastIngredients.substring(2);
            }
            this.pastIngredients = pastIngredients;
        }

        public String getToken() {
            return token;
        }

        public String getName() {
            return name;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public String getPassword() {
            return password;
        }

        public String getRestrictions() {
            return restrictions;
        }
    }
}
