package com.prodevteam.tastebud;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuScreen extends MenuAbstract {

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
                ArrayList<MenuData> menu = App.sqlConnection.getMenu(params[0]);
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
