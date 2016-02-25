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
        String email = App.currentUser.getEmailAddress();
        String ings = "";
        for(MenuData m : selectedItems)
            ings += m.getMajorIngs() + ", " + m.getMinorIngs() + ", ";
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                return App.sqlConnection.placeOrder(params[0], params[1]);
            }
        }.execute(email, ings);
        App.currentUser.addPastIngredient(ings);
        startActivity(new Intent(this, PostLoginActivity.class));
    }

    private void onUpdateOrderClick() {
        LinearLayout wrapper = (LinearLayout) findViewById(R.id.order_wrapper);
        ArrayList<MenuData> list = new ArrayList<>();
        for(int i = 0; i < wrapper.getChildCount(); i++)
            if(((MenuAbstract.MenuItem) wrapper.getChildAt(i)).isChecked()) list.add(selectedItems.remove(i));
        for(MenuData d : list) {
            for(int i = 0; i < wrapper.getChildCount(); i++) {
                MenuAbstract.MenuItem item = (MenuAbstract.MenuItem) wrapper.getChildAt(i);
                if (item.getName().equals(d.getName())) {
                    wrapper.removeView(item);
                    break;
                }
            }
        }

    }
}
