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

    protected static final String NAME_EXTRA_KEY = "com.prodevteam.tastebud.USER_NAME";
    private Drawable[] backgrounds;
    private int imageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView bgimage = (ImageView) findViewById(R.id.bgimage);
        backgrounds = new Drawable[2];
        imageIndex = 2;
        bgimage.setImageDrawable(getResources().getDrawable(R.drawable.login_2));

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



        // Create a new account on the database

        // Take the user to the settings screen
        // Intent intent = new Intent(this, AccountSettingsScreen.class);
        //startActivity(intent);
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
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String...params) {
                return App.sqlConnection.attemptLogin(params[0], params[1]);
            }

            @Override
            protected void onPostExecute(String result) {
                if(result == null)
                    Toast.makeText(LoginActivity.this, "Error signing in, invalid email address or password.", Toast.LENGTH_SHORT).show();
                else {
                    intent.putExtra(NAME_EXTRA_KEY, result);
                    startActivity(intent);
                }
            }
        }.execute(userEmail, password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
