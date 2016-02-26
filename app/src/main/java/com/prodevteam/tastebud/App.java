package com.prodevteam.tastebud;

import android.app.Application;
import android.content.res.Resources;

public class App extends Application {

    static App myApp;

    static MySQL sqlConnection;
    static UserInfo currentUser;
    static {
        currentUser = new UserInfo("", "", "", "");
    }
    static String token;

    @Override
    public void onCreate() {
        super.onCreate();

        if(myApp == null) myApp = this;

        sqlConnection = new MySQL();
        sqlConnection.initializeConnection();
    }

    public static class UserInfo {
        private String name;
        private String emailAddress;
        private String password;
        private String restrictions;
        private String pastIngredients;

        public UserInfo(String name, String emailAddress, String password, String restrictions) {
            this.name = name;
            this.emailAddress = emailAddress;
            this.password = password;
            this.restrictions = restrictions;
        }

        public String getPastIngredients() {
            return pastIngredients;
        }

        public String addPastIngredient(String newIngredient) {
            pastIngredients += ", " + newIngredient;
            return pastIngredients;
        }

        public void setPastIngredients(String pastIngredients) {
            this.pastIngredients = pastIngredients;
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
