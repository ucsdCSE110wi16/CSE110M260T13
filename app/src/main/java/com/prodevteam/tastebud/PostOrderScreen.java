package com.prodevteam.tastebud;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Belton on 2/11/2016.
 */
public class PostOrderScreen extends AppCompatActivity {

    protected static ArrayList<MenuData> selectedItems = new ArrayList<MenuData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post_order);
        Button continueButton = (Button) findViewById(R.id.order_finished_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContinueClicked();
            }
        });

        LinearLayout wrapper = (LinearLayout) findViewById(R.id.order_wrapper);
        for(MenuData m : selectedItems)
            wrapper.addView(new MenuScreen.MenuItem(PostOrderScreen.this, m));

        Button removeButton = (Button) findViewById(R.id.remove_button);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateOrderClick();
            }
        });

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackButtonClick();
            }
        });
    }

    private void onBackButtonClick() {
        selectedItems.clear();
        startActivity(new Intent(this, PostLoginActivity.class));
    }

    private void onContinueClicked() {
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                return App.sqlConnection.placeOrder(params[0], selectedItems);
            }
        }.execute(PostLoginActivity.restaurantName);
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                return App.sqlConnection.addOrders(params[0], selectedItems);
            }
        }.execute(PostLoginActivity.restaurantName);
        Toast.makeText(PostOrderScreen.this, "Order completed!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, PostLoginActivity.class));
    }

    private void onUpdateOrderClick() {
        LinearLayout wrapper = (LinearLayout) findViewById(R.id.order_wrapper);
        ArrayList<MenuAbstract.MenuItem> list = new ArrayList<>();
        for(int i = 0; i < wrapper.getChildCount(); i++)
            if(((MenuAbstract.MenuItem) wrapper.getChildAt(i)).isChecked()) list.add((MenuAbstract.MenuItem) (wrapper.getChildAt(i)));
        for(MenuAbstract.MenuItem d : list)
            wrapper.removeView(d);
        selectedItems.clear();
        for(int i = 0; i < wrapper.getChildCount(); i++)
            selectedItems.add(new MenuData((MenuAbstract.MenuItem)(wrapper.getChildAt(i))));
    }
}
