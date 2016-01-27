package com.prodevteam.tastebud;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class App extends Application {

    static MySQL sqlConnection;

    @Override
    public void onCreate() {
        super.onCreate();

        sqlConnection = new MySQL();
        sqlConnection.initializeConnection();
    }
}
