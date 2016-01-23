package com.prodevteam.tastebud;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MenuScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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

        // TODO: Change this code block to retrieve the menu from the server and populate the menu view
        // Get the menu wrapper that we will add the items too
        LinearLayout menuWrapper = (LinearLayout) findViewById(R.id.menu_wrapper);

        // Add 8 items
        for(int i = 0; i < 8; i++) {
            // Create the menu items
            MenuItem menuItem = new MenuItem(this);

            // Set the menu items' contents
            menuItem.setItemIcon(getResources().getDrawable(R.drawable.menu_item_1));
            menuItem.setItemName(getResources().getString(R.string.menu_item_1_name));
            menuItem.setItemPrice(getResources().getString(R.string.menu_item_1_price));
            menuItem.setItemIng(getResources().getString(R.string.menu_item_1_ing));

            // Add the item to the menu
            menuWrapper.addView(menuItem);
        }

    }

    private void onNoClick() {
        // TODO: Set the behavior of this button to create a new ingredient bubble that is grayed out
        // The bubble should contain whatever was entered into the ingredient field
        //light off set light on
    }

    private void onYesClick() {
        // TODO: Set the behavior of this button to create a new ingredient bubble that is not grayed out
        // The bubble should contain whatever was entered into the ingredient field
        //light on set light off
    }

    private class MenuItem extends RelativeLayout {

        private ImageView itemIcon;
        private TextView itemName;
        private TextView itemPrice;
        private TextView itemIng;

        public MenuItem(Context context) {
            super(context);

            View.inflate(context, R.layout.menu_item_layout, this);
            itemIcon = (ImageView) findViewById(R.id.item_icon);
            itemName = (TextView) findViewById(R.id.item_name);
            itemPrice = (TextView) findViewById(R.id.item_price);
            itemIng = (TextView) findViewById(R.id.item_ing);
        }

        public void setItemIcon(Drawable icon) {
            itemIcon.setImageDrawable(icon);
            itemIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        public void setItemName(String name) {
            itemName.setText(name.toCharArray(), 0, name.length());
        }

        public void setItemPrice(String price) {
            itemPrice.setText(price.toCharArray(), 0, price.length());
        }

        public void setItemIng(String ing) {
            itemIng.setText(ing.toCharArray(), 0, ing.length());
        }

        public ImageView getItemIcon() {
            return itemIcon;
        }

        public TextView getItemName() {
            return itemName;
        }

        public TextView getItemPrice() {
            return itemPrice;
        }

        public TextView getItemIng() {
            return itemIng;
        }
    }
}
