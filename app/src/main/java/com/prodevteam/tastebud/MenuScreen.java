package com.prodevteam.tastebud;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
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
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout parent = (RelativeLayout) inflater.inflate(R.layout.menu_item_layout, null);


    }

    private void onNoClick() {
        // TODO: Set the behavior of this button to create a new ingredient bubble that is grayed out
        // The bubble should contain whatever was entered into the ingredient field
    }

    private void onYesClick() {
        // TODO: Set the behavior of this button to create a new ingredient bubble that is not grayed out
        // The bubble should contain whatever was entered into the ingredient field
    }

    private class MenuItem extends RelativeLayout {

        private ImageView itemIcon;
        private TextView itemName;
        private TextView itemPrice;
        private TextView itemIng;

        public MenuItem(Context context) {
            super(context);
        }

        public void setItemIcon(ImageView icon) {
            itemIcon = icon;
        }

        public void setItemName(TextView name) {

        }

        public void setItemPrice()
    }
}
