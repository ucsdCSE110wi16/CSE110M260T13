package com.prodevteam.tastebud;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "22oGHmMQOZvDuumizSeMuG09QsTaL6WFsWFkc1mG", "caGmrg8tpFpRCoBfD2pJAqW4vHeDgz16jjkjpxOG");

        MySQL sql = new MySQL();
        sql.connect("jdbc:sqlserver://sql3.freesqldatabase.com:3306/sql3104137", "sql3104137", "EdL4hLKf6S");

    }
}
