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

public class MenuScreen extends ActionBarActivity {

    protected static final String ITEMS_EXTRA_KEY = "com.prodevteam.tastebud.selectedItems";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Set up click listeners for the two buttons
        Button yesButton = (Button) findViewById(R.id.yes_button);
        Button noButton = (Button) findViewById(R.id.no_button);
        ImageButton orderButton = (ImageButton) findViewById(R.id.order_button);

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
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOrderButtonClick();
            }
        });

        // Get the menu wrapper that we will add the items too
        final LinearLayout menuWrapper = (LinearLayout) findViewById(R.id.menu_wrapper);
        final Context context = this;

        new AsyncTask<Void, Void, ArrayList<MenuData>>() {
            @Override
            protected ArrayList<MenuData> doInBackground(Void... params) {
                return App.sqlConnection.getMenu();
            }

            @Override
            protected void onPostExecute(ArrayList<MenuData> result) {
                for(MenuData m : result)
                    menuWrapper.addView(new MenuItem(context, m));
            }
        }.execute();
    }

    private void onOrderButtonClick() {
        LinearLayout menuWrapper = (LinearLayout) findViewById(R.id.menu_wrapper);
        ArrayList<MenuData> selectedItems = new ArrayList<>();
        for(int i = 0; i < menuWrapper.getChildCount(); i++) {
            MenuScreen.MenuItem item = (MenuScreen.MenuItem) menuWrapper.getChildAt(i);
            if(item.isChecked()) selectedItems.add(new MenuData(item));
        }

        String email = App.currentUser.getEmailAddress();
        String ings = "";
        for(MenuData m : selectedItems)
            ings += m.getIng() + ", ";
        ings = ings.substring(0, ings.lastIndexOf(','));
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                return App.sqlConnection.placeOrder(params[0], params[1]);
            }

            @Override
            protected void onPostExecute(Boolean result) {

            }
        }.execute(email, ings);

        Intent intent = new Intent(this, PostOrderScreen.class);
        intent.putExtra(ITEMS_EXTRA_KEY , selectedItems);
        startActivity(intent);
    }

    private void addIngredientLogic(IngredientItem ing, boolean val) {
        LinearLayout menuWrapper = (LinearLayout) findViewById(R.id.menu_wrapper);
        if(!val) {
            for (int i = 0; i < menuWrapper.getChildCount(); i++) {
                View v = menuWrapper.getChildAt(i);
                MenuItem item = (MenuItem) v;
                String[] ingredients_list = item.getIngredients().split(",");
                for (String s : ingredients_list)
                    if (s.trim().equalsIgnoreCase(ing.getName().trim()) || s.contains(ing.getName().trim()))
                        item.setVisibility(View.GONE);
            }
        } else {
            for (int i = 0; i < menuWrapper.getChildCount(); i++) {
                View v = menuWrapper.getChildAt(i);
                MenuItem item = (MenuItem) v;
                String[] ingredients_list = item.getIngredients().split(",");
                boolean containsIng = false;
                for (String s : ingredients_list)
                    if (s.trim().equalsIgnoreCase(ing.getName().trim()) || s.contains(ing.getName().trim())) containsIng = true;
                if(!containsIng) item.setVisibility(View.GONE);
            }
        }
    }

    private void updateIngredientLogic() {
        LinearLayout menuWrapper = (LinearLayout) findViewById(R.id.menu_wrapper);
        for(int i = 0; i < menuWrapper.getChildCount(); i++)
            menuWrapper.getChildAt(i).setVisibility(View.VISIBLE);
        LinearLayout ing_wrapper = (LinearLayout) findViewById(R.id.ing_wrapper);
        for (int i = 0; i < ing_wrapper.getChildCount(); i++) {
            View v = ing_wrapper.getChildAt(i);
            IngredientItem ing = (IngredientItem) v;
            addIngredientLogic(ing, ing.getButton().isChecked());
        }
    }

    private void onYesClick() {
        IngredientItem ing = addIngredient();
        if(ing == null) return;
        ing.getButton().setChecked(true);

        updateIngredientLogic();
    }

    private void onNoClick() {
        IngredientItem ing = addIngredient();
        if(ing == null) return;
        ing.getButton().setChecked(false);

        updateIngredientLogic();
    }

    private IngredientItem addIngredient() {
        // Get the ingredient name from the ingredient field
        EditText ing_field = (EditText) findViewById(R.id.ingredient_field);
        String ing_name = ing_field.getText().toString();
        if(ing_name.equals("")) return null;

        LinearLayout ing_wrapper = (LinearLayout) findViewById(R.id.ing_wrapper);
        for (int i = 0; i < ing_wrapper.getChildCount(); i++) {
            IngredientItem ing = (IngredientItem) ing_wrapper.getChildAt(i);
            if(ing_name.equals(ing.getName())) return null;
        }

        final IngredientItem ing = new IngredientItem(this);
        ing.setName(ing_name);

        // Add the ingredient to the ingredient list
        final LinearLayout ing_list = (LinearLayout) findViewById(R.id.ing_wrapper);

        // Set the X button to remove the ingredient from the wrapper
        ing.x_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ing_list.removeView(ing);
                updateIngredientLogic();
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
                    updateIngredientLogic();
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

        public String getName() {
            return name.getText().toString();
        }
    }

    public static class MenuItem extends RelativeLayout {

        private ImageView itemIcon;
        private TextView itemName;
        private TextView itemPrice;
        private TextView itemIng;
        private CheckBox itemBox;

        public MenuItem(Context context) {
            super(context);

            View.inflate(context, R.layout.menu_item_layout, this);
            itemIcon = (ImageView) findViewById(R.id.item_icon);
            itemName = (TextView) findViewById(R.id.item_name);
            itemPrice = (TextView) findViewById(R.id.item_price);
            itemIng = (TextView) findViewById(R.id.item_ing);
        }

        public MenuItem(Context context, MenuData m) {
            this(context);

            this.setItemName(m.getName());
            this.setItemPrice(m.getPrice());
            this.setItemIng(m.getIng());
            Drawable img = m.getImg();
            if(img == null) img = getResources().getDrawable(R.drawable.no_image);
            this.setItemIcon(img);
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

        public String getIngredients() {
            return itemIng.getText().toString();
        }

        public String getName() { return itemName.getText().toString();}

        public String getPrice() { return itemPrice.getText().toString();}

        public Drawable getImage() {
            return itemIcon.getDrawable();
        }

        public boolean isChecked() {
            return itemBox.isChecked();
        }
    }
}
