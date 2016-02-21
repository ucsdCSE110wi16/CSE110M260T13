package com.prodevteam.tastebud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Belton on 2/11/2016.
 */
public class PostOrderScreen extends ActionBarActivity {
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
        /*
        LinearLayout wrapper = (LinearLayout) findViewById(R.id.order_wrapper);
        Serializable serializableExtra = getIntent().getSerializableExtra(MenuScreen.ITEMS_EXTRA_KEY);
        ArrayList<MenuData> selectedItems = (ArrayList) serializableExtra;
        for(MenuData m : selectedItems)
            wrapper.addView(new MenuScreen.MenuItem(PostOrderScreen.this, m));
        */
    }

    private void onContinueClicked() {
        startActivity(new Intent(this, PostLoginActivity.class));
    }
}
