package com.prodevteam.tastebud;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Belton on 2/22/2016.
 */
public class RecommendationScreen extends MenuAbstract {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void buildMenu() {
        // Get the menu wrapper that we will add the items too
        final LinearLayout menuWrapper = (LinearLayout) findViewById(R.id.menu_wrapper);
        final Context context = this;

        new AsyncTask<String, Void, ArrayList<MenuData>>() {
            @Override
            protected ArrayList<MenuData> doInBackground(String... params) {
                ArrayList<MenuData> menu = App.sqlConnection.makeRec(params[0]);
                for(MenuData d : menu)
                    d.setImage();
                return menu;
            }

            @Override
            protected void onPostExecute(ArrayList<MenuData> result) {
                for(MenuData m : result)
                    menuWrapper.addView(new MenuItem(context, m));
                filterRestrictions();
            }
        }.execute(PostLoginActivity.restaurantName);
    }
}
