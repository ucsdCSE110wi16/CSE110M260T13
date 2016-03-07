package com.prodevteam.tastebud;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AccountSettingsScreen extends Activity {

    private TextView email_setting;
    private EditText pass_setting;
    private EditText name_setting;
    private EditText restrictions;
    private LinearLayout past_orders;
    private Button back_button;
    private ImageButton back_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        email_setting = (TextView) findViewById(R.id.user_email);
        pass_setting = (EditText) findViewById(R.id.pass_field);
        name_setting = (EditText) findViewById(R.id.name_field);
        restrictions = (EditText) findViewById(R.id.restrictions_field);
        past_orders = (LinearLayout) findViewById(R.id.past_orders);
        back_button = (Button) findViewById(R.id.back_button);
        back_image = (ImageButton) findViewById(R.id.back_image_button);

        App.UserInfo user = App.currentUser;
        email_setting.setText(user.getEmailAddress().toCharArray(), 0, user.getEmailAddress().length());
        pass_setting.setText(user.getPassword().toCharArray(), 0, user.getPassword().length());
        name_setting.setText(user.getName().toCharArray(), 0, user.getName().length());
        restrictions.setText(user.getRestrictions().toCharArray(), 0, user.getRestrictions().length());


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackButtonClicked();
            }
        });
        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackButtonClicked();
            }
        });

        new AsyncTask<String, Void, ArrayList<PastOrders>>() {

            @Override
            protected ArrayList<PastOrders> doInBackground(String... params) {
                return App.sqlConnection.getOrderHistory(params[0]);
            }

            @Override
            protected void onPostExecute(ArrayList<PastOrders> pastOrders) {
                for(PastOrders p : pastOrders) {
                    PastOrder order = new PastOrder(AccountSettingsScreen.this);
                    order.setName(p.getName());
                    order.setRating(p.getRating());
                    past_orders.addView(order);
                }
            }
        }.execute(App.currentUser.getEmailAddress());

        Button save_button = (Button) findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButtonClicked();
            }
        });
    }

    private void saveButtonClicked() {
        String userToken = App.currentUser.getToken();
        App.currentUser = new App.UserInfo(name_setting.getText().toString(), email_setting.getText().toString(),
                                            pass_setting.getText().toString(), restrictions.getText().toString(), userToken);
        final ArrayList<PastOrders> pastOrders = new ArrayList<>();
        for(int i = 0; i < past_orders.getChildCount(); i++) {
            View v = past_orders.getChildAt(i);
            PastOrder order = (PastOrder) v;
            pastOrders.add(new PastOrders(order));
        }
        new AsyncTask<App.UserInfo, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(App.UserInfo... params) {
                App.sqlConnection.updateRatings(params[0].getEmailAddress(), pastOrders);
                return App.sqlConnection.updateUserInfo(params[0]);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if(result) {
                    Toast.makeText(AccountSettingsScreen.this, "Saved changes successfully.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AccountSettingsScreen.this, PostLoginActivity.class));
                }
                else Toast.makeText(AccountSettingsScreen.this, "Failed to save. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }.execute(App.currentUser);

    }

    public class PastOrder extends RelativeLayout {
        private TextView name;
        private RatingBar rating;

        public PastOrder(Context context) {
            super(context);
            View.inflate(context, R.layout.past_order_layout, this);
            this.name = (TextView) findViewById(R.id.name);
            this.rating = (RatingBar) findViewById(R.id.rating);
        }

        public PastOrder(Context context, String name, float rating) {
            this(context);

            setName(name);
            setRating(rating);
        }

        public TextView getName() {
            return name;
        }

        public RatingBar getRating() {
            return rating;
        }

        public void setName(String name) {
            this.name.setText(name.toCharArray(), 0, name.length());
        }

        public void setRating(float rating) {
            this.rating.setRating(rating);
        }
    }

    private void onBackButtonClicked() {
        startActivity(new Intent(this, PostLoginActivity.class));
    }

}
