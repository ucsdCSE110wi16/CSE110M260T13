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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AccountSettingsScreen extends Activity {

    private TextView email_setting;
    private EditText pass_setting;
    private EditText name_setting;
    private EditText restrictions;
    private TextView ings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        email_setting = (TextView) findViewById(R.id.user_email);
        pass_setting = (EditText) findViewById(R.id.pass_field);
        name_setting = (EditText) findViewById(R.id.name_field);
        restrictions = (EditText) findViewById(R.id.restrictions_field);
        ings = (TextView) findViewById(R.id.ings_field);

        App.UserInfo user = App.currentUser;
        email_setting.setText(user.getEmailAddress().toCharArray(), 0, user.getEmailAddress().length());
        pass_setting.setText(user.getPassword().toCharArray(), 0, user.getPassword().length());
        name_setting.setText(user.getName().toCharArray(), 0, user.getName().length());
        restrictions.setText(user.getRestrictions().toCharArray(), 0, user.getRestrictions().length());
        ings.setText(user.getPastIngredients().toCharArray(), 0, user.getPastIngredients().length());

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
        App.currentUser.setPastIngredients(ings.getText().toString());
        new AsyncTask<App.UserInfo, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(App.UserInfo... params) {
                return App.sqlConnection.updateUserInfo(params[0]);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if(result) startActivity(new Intent(AccountSettingsScreen.this, PostLoginActivity.class));
                else Toast.makeText(AccountSettingsScreen.this, "Failed to save. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }.execute(App.currentUser);

    }
}
