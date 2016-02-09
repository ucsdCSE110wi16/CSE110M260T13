package com.prodevteam.tastebud;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.sql.Connection;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends ActionBarActivity {

    private Drawable[] backgrounds;
    private int imageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView bgimage = (ImageView) findViewById(R.id.bgimage);
        backgrounds = new Drawable[2];
        imageIndex = 4;
        bgimage.setImageDrawable(getResources().getDrawable(R.drawable.login_4));

        // This timer will change the background image every 3 seconds
        // It runs for 21 seconds and is then restarted
        /*
        new CountDownTimer(21000, 3000) {
            public void onTick(long millisUntilFinished) {
                changeBackgroundImage();
                imageIndex++;
            }

            public void onFinish() {
                this.start(); //Restart the timer when it finishes
            }
        }.start();
        */

        // This sets the behavior of the sign in button, it calls onSignInClick
        Button signInButton = (Button) findViewById(R.id.signin_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSignInClick();
            }
        });

        // This sets the behavior of the create account button, it calls onCreateAccountClick
        Button createAccountButton = (Button) findViewById(R.id.create_acc_button);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onCreateAccountClick();
            }
        });
    }

    /**
     * Handles the 'Create Account' button being clicked
     */
    private void onCreateAccountClick() {

        // Bring up create account dialog
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout dialog = (LinearLayout) inflater.inflate(R.layout.create_account_dialog_layout, null);

        RelativeLayout login_screen = (RelativeLayout) findViewById(R.id.login_screen);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        login_screen.addView(dialog, params);
        findViewById(R.id.signin_button).setVisibility(View.INVISIBLE);

        Button continueButton = (Button) findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContinueClick();
            }
        });
        // Create a new account on the database

    }

    private void onContinueClick() {
        // Take the user to the settings screen

        Intent intent = new Intent(this, AccountSettingsScreen.class);
        EditText fname_field = (EditText) findViewById(R.id.first_name_field);
        EditText lname_field = (EditText) findViewById(R.id.last_name_field);
        EditText email_field = (EditText) findViewById(R.id.new_email_field);
        EditText pass_field = (EditText) findViewById(R.id.pass_field);

        String fname = fname_field.getText().toString();
        String lname = lname_field.getText().toString();
        String email = email_field.getText().toString();
        String pass = pass_field.getText().toString();

        App.currentUser = new App.UserInfo(fname, lname, email, pass);
        startActivity(intent);
    }

    /**
     * Changes the background image through the 4 different images
     */
    private void changeBackgroundImage() {
        switch(imageIndex) {
            case 5:
                imageIndex = 1;
            case 1:
                backgrounds[0] = getResources().getDrawable(R.drawable.login_1);
                backgrounds[1] = getResources().getDrawable(R.drawable.login_2);
                break;
            case 2:
                backgrounds[0] = getResources().getDrawable(R.drawable.login_2);
                backgrounds[1] = getResources().getDrawable(R.drawable.login_3);
                break;
            case 3:
                backgrounds[0] = getResources().getDrawable(R.drawable.login_3);
                backgrounds[1] = getResources().getDrawable(R.drawable.login_4);
                break;
            case 4:
                backgrounds[0] = getResources().getDrawable(R.drawable.login_4);
                backgrounds[1] = getResources().getDrawable(R.drawable.login_1);
                break;
        }
        TransitionDrawable crossfade = new TransitionDrawable(backgrounds);
        ((ImageView) findViewById(R.id.bgimage)).setImageDrawable(crossfade);
        crossfade.startTransition(400);
    }

    /**
     * This method is called when the sign in button is clicked.
     */
    private void onSignInClick() {
        // Retrieving the email address and password fields
        EditText emailBar = (EditText) findViewById(R.id.email_field);
        EditText passwordBar = (EditText) findViewById((R.id.pass_field));
        String password = passwordBar.getText().toString();
        final String userEmail = emailBar.getText().toString();
        final Intent intent = new Intent(this, PostLoginActivity.class);

        // Login to the app
        new AsyncTask<String, Void, App.UserInfo>() {
            @Override
            protected App.UserInfo doInBackground(String...params) {
                return App.currentUser = App.sqlConnection.attemptLogin(params[0], params[1]);
            }

            @Override
            protected void onPostExecute(App.UserInfo result) {
                if(result == null)
                    Toast.makeText(LoginActivity.this, "Error signing in, invalid email address or password.", Toast.LENGTH_SHORT).show();
                else {
                    startActivity(intent);
                }
            }
        }.execute(userEmail, password);
    }
}
