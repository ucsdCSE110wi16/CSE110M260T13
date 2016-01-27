package com.prodevteam.tastebud;

import android.app.Application;

public class App extends Application {

    static MySQL sqlConnection;

    @Override
    public void onCreate() {
        super.onCreate();

        sqlConnection = new MySQL();
        sqlConnection.initializeConnection();
    }
}
