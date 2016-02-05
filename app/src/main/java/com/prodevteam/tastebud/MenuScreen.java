package com.prodevteam.tastebud;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
        addIngredient().getButton().setChecked(false);
    }

    private void onYesClick() {
        addIngredient().getButton().setChecked(true);
    }

    private IngredientItem addIngredient() {
        // Get the ingredient name from the ingredient field
        EditText ing_field = (EditText) findViewById(R.id.ingredient_field);
        String ing_name = ing_field.getText().toString();
        final IngredientItem ing = new IngredientItem(this);
        ing.setName(ing_name);

        // Add the ingredient to the ingredient list
        final LinearLayout ing_list = (LinearLayout) findViewById(R.id.ing_wrapper);

        // Set the X button to remove the ingredient from the wrapper
        ing.x_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ing_list.removeView(ing);

                // TODO: This should also remove the filtering rule corresponding to item
            }
        });
        ing_list.addView(ing);

        // Reset the ingredient field so the user can enter another ingredient
        ing_field.setText("".toCharArray(), 0, 0);

        return ing;
    }

    private class IngredientItem extends LinearLayout {
        private CheckBox button;
        private TextView name;
        public ImageButton x_button;

        public IngredientItem(Context context) {
            super(context);

            View.inflate(context, R.layout.ing_item_layout, this);

            // button is initially off
            button = (CheckBox) findViewById(R.id.ing_button);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: Change the filtering logic for the ingredient that is the parent of v
                }
            });
            name = (TextView) findViewById(R.id.ing_name);
            x_button = (ImageButton) findViewById(R.id.ing_x_button);
        }

        public CheckBox getButton() {
            return button;
        }

        public void setName(String ing_name) {
            name.setText(ing_name.toCharArray(), 0, ing_name.length());
        }
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
    }
}
