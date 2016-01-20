package com.prodevteam.tastebud;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class LoginActivity extends ActionBarActivity {

    protected static final String EMAIL_EXTRA_KEY = "com.prodevteam.tastebud.USER_EMAIL";
    private int imageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        imageIndex = 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // This timer will change the background image every 3 seconds
        // It runs for 21 seconds and is then restarted
        new CountDownTimer(21000, 3000) {
            public void onTick(long millisUntilFinished) {
                changeBackgroundImage();
            }

            public void onFinish() {
                this.start(); //Restart the timer when it finishes
            }
        }.start();

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
        //        onCreateAccountClick();
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
        login_screen.addView(dialog);

        // Create a new account on the database

        // Take the user to the settings screen
        // Intent intent = new Intent(this, AccountSettingsScreen.class);
        //startActivity(intent);
    }

    /**
     * Cycles the background image through the 4 different images
     */
    // TODO: Modify this method to crossfade the background images (i.e. login_1 fades into login_2, etc.)
    // TODO: Test the background image scaling on large screens (1080p)
    // Possibly helpful information: http://developer.android.com/training/animation/crossfade.html
    private void changeBackgroundImage() {
        ImageView bgimage = (ImageView) findViewById(R.id.bgimage);
        imageIndex++;
        switch(imageIndex) {
            case 5:
                imageIndex = 1;
            case 1:
                bgimage.setImageDrawable(getResources().getDrawable(R.drawable.login_1));
                break;
            case 2:
                bgimage.setImageDrawable(getResources().getDrawable(R.drawable.login_2));
                break;
            case 3:
                bgimage.setImageDrawable(getResources().getDrawable(R.drawable.login_3));
                break;
            case 4:
                bgimage.setImageDrawable(getResources().getDrawable(R.drawable.login_4));
                break;
        }
    }

    /**
     * This method is called when the sign in button is clicked.
     */
    private void onSignInClick() {
        // Creates an Intent that will open the post login activity
        Intent intent = new Intent(this, PostLoginActivity.class);

        // Retrieving the email address and password fields
        EditText emailBar = (EditText) findViewById(R.id.email_field);
        EditText passwordBar = (EditText) findViewById((R.id.pass_field));
        String password = passwordBar.getText().toString();
        String userEmail = emailBar.getText().toString();

        // WILL BE CHANGED: Pass the user's email address as an extra to the next intent
        // TODO: Change this to pass the user's first name (retrieved from SQL server)
        intent.putExtra(EMAIL_EXTRA_KEY, userEmail);
        // Switch to the post login activity
        startActivity(intent);
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
