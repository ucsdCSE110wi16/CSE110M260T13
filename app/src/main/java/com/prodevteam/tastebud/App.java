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

    @Override
    public void onCreate() {
        super.onCreate();

        if(myApp == null) myApp = this;

        sqlConnection = new MySQL();
        sqlConnection.initializeConnection();
    }

    // Immutable userinfo class
    public static class UserInfo {
        private String firstName;
        private String lastName;
        private String emailAddress;
        private String password;

        public UserInfo(String firstName, String lastName, String emailAddress, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.emailAddress = emailAddress;
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public String getLastName() {
            return lastName;
        }

        public String getPassword() {
            return password;
        }
    }
}
