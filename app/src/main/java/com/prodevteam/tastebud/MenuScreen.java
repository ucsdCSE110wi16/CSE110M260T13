package com.prodevteam.tastebud;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        // Set up click listeners for the two buttons
        Button yesButton = (Button) findViewById(R.id.yes_button);
        Button noButton = (Button) findViewById(R.id.no_button);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onYesClick();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNoClick();
            }
        });

        // TODO: Populate the menu with menu items, programmatically generate RelativeLayouts (menu items) and add then to the menu


    }

    private void onNoClick() {
        // TODO: Set the behavior of this button to create a new ingredient bubble that is grayed out
        // The bubble should contain whatever was entered into the ingredient field
    }

    private void onYesClick() {
        // TODO: Set the behavior of this button to create a new ingredient bubble that is not grayed out
        // The bubble should contain whatever was entered into the ingredient field
    }
}
