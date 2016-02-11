package com.prodevteam.tastebud;

import android.app.Application;
import android.content.res.Resources;

import org.apache.http.util.CharArrayBuffer;

public class App extends Application {

    static App myApp;

    static MySQL sqlConnection;
    static UserInfo currentUser;
    static {
        currentUser = new UserInfo("", "", "", "", "");
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
        private String restrictions;

        public UserInfo(String firstName, String lastName, String emailAddress, String password, String restrictions) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.emailAddress = emailAddress;
            this.password = password;
            this.restrictions = restrictions;
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

        public String getRestrictions() {
            return restrictions;
        }
    }
}
