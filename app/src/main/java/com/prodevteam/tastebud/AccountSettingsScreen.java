package com.prodevteam.tastebud;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
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

import java.util.List;

public class AccountSettingsScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        TextView email_setting = (TextView) findViewById(R.id.user_email);
        EditText pass_setting = (EditText) findViewById(R.id.pass_field);
        EditText fname_setting = (EditText) findViewById(R.id.fname_field);
        EditText lname_setting = (EditText) findViewById(R.id.lname_field);

        App.UserInfo user = App.currentUser;
        email_setting.setText(user.getEmailAddress().toCharArray(), 0, user.getEmailAddress().length());
        pass_setting.setText(user.getPassword().toCharArray(), 0, user.getPassword().length());
        fname_setting.setText(user.getFirstName().toCharArray(), 0, user.getFirstName().length());
        lname_setting.setText(user.getLastName().toCharArray(), 0, user.getLastName().length());

        Button save_button = (Button) findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveButtonClicked();
            }
        });
    }

    private void saveButtonClicked() {
        startActivity(new Intent(this, PostLoginActivity.class));
        // TODO: This method should also update the database with the newly entered info
    }
}
