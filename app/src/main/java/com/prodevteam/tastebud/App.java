package com.prodevteam.tastebud;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Belton on 1/21/2016.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "22oGHmMQOZvDuumizSeMuG09QsTaL6WFsWFkc1mG", "caGmrg8tpFpRCoBfD2pJAqW4vHeDgz16jjkjpxOG");
    }
}
