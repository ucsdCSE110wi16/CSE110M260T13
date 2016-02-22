package com.prodevteam.tastebud;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Belton on 2/22/2016.
 */
public abstract class MenuAbstract extends ActionBarActivity {
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
        buildMenu();
    }

    public abstract void buildMenu();

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
            ings += m.getMajorIngs() + ", " + m.getMinorIngs();
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                return App.sqlConnection.placeOrder(params[0], params[1]);
            }
        }.execute(email, ings);

        Intent intent = new Intent(this, PostOrderScreen.class);
        PostOrderScreen.selectedItems = selectedItems;
        startActivity(intent);
    }

    private void addIngredientLogic(IngredientItem ing, boolean val) {
        LinearLayout menuWrapper = (LinearLayout) findViewById(R.id.menu_wrapper);
        for (int i = 0; i < menuWrapper.getChildCount(); i++) {
            MenuItem item = (MenuItem) menuWrapper.getChildAt(i);
            if(item.getAllIngredients().contains(ing.getName().trim()) != val) item.setVisibility(View.GONE);
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

        final LinearLayout ing_wrapper = (LinearLayout) findViewById(R.id.ing_wrapper);
        for (int i = 0; i < ing_wrapper.getChildCount(); i++) {
            IngredientItem ing = (IngredientItem) ing_wrapper.getChildAt(i);
            if(ing_name.equals(ing.getName())) return null;
        }

        final IngredientItem ing = new IngredientItem(this);
        ing.setName(ing_name);


        // Set the X button to remove the ingredient from the wrapper
        ing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ing_wrapper.removeView(ing);
                updateIngredientLogic();
            }
        });

        // Add the ingredient to the ingredient list
        ing_wrapper.addView(ing);

        // Reset the ingredient field so the user can enter another ingredient
        ing_field.setText("".toCharArray(), 0, 0);

        return ing;
    }
    public static class MenuItem extends RelativeLayout {

        private ImageView itemIcon;
        private TextView itemName;
        private TextView itemPrice;
        private LinearLayout itemIng;
        private CheckBox itemBox;

        private String majorIngs;
        private String minorIngs;

        public MenuItem(Context context) {
            super(context);

            View.inflate(context, R.layout.menu_item_layout, this);
            itemIcon = (ImageView) findViewById(R.id.item_icon);
            itemName = (TextView) findViewById(R.id.item_name);
            itemPrice = (TextView) findViewById(R.id.item_price);
            itemIng = (LinearLayout) findViewById(R.id.item_ing_wrapper);
            itemBox = (CheckBox) findViewById(R.id.item_checkbox);
        }

        public MenuItem(Context context, MenuData m) {
            this(context);

            this.setItemName(m.getName());
            this.setItemPrice(m.getPrice());
            this.setItemIng(m.getMajorIngs() + ", " + m.getMinorIngs());
            this.majorIngs = m.getMajorIngs();
            this.minorIngs = m.getMinorIngs();
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
            for(String s : ing.split(",")) {
                Ingredient ingred = new Ingredient(this);
                ingred.setName(s.trim());
                itemIng.addView(ingred);
            }
        }

        public String getAllIngredients() {
            return majorIngs + ", " + minorIngs;
        }

        public String getMajorIngs() {
            return majorIngs;
        }

        public String getMinorIngs() {
            return minorIngs;
        }

        public String getName() { return itemName.getText().toString();}

        public String getPrice() { return itemPrice.getText().toString();}

        public Drawable getImage() {
            return itemIcon.getDrawable();
        }

        public boolean isChecked() {
            return itemBox.isChecked();
        }

        private class Ingredient extends LinearLayout {
            private TextView name;

            public Ingredient(MenuItem context) {
                super(context.getContext());

                View.inflate(context.getContext(), R.layout.menu_item_ing_layout, this);
                name = (TextView) findViewById(R.id.ing_name1);
            }

            public void setName(String name) {
                this.name.setText(name.toCharArray(), 0, name.length());
            }

            public String getName() {
                return this.name.getText().toString();
            }

        }
    }

    public class IngredientItem extends LinearLayout {
        private CheckBox button;
        private TextView name;
        private ImageButton x_button;

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

        public void setOnClickListener(OnClickListener listener) {
            x_button.setOnClickListener(listener);
        }
    }
}
