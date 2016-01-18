package com.prodevteam.tastebud;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MenuScreen extends ActionBarActivity {

    private ViewGroup.LayoutParams iconParams;
    private ViewGroup.LayoutParams itemParams;
    private ViewGroup.LayoutParams priceParams;
    private ViewGroup.LayoutParams nameParams;
    private ViewGroup.LayoutParams ingParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);

        // TODO: Populate the menu with menu items, programmatically generate RelativeLayouts (menu items) and add then to the menu
    }
}
