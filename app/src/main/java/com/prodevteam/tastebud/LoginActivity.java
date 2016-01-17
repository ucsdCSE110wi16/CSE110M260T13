package com.prodevteam.tastebud;

import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class LoginActivity extends ActionBarActivity {

    private int imageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        imageIndex = 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // This timer will change the background image every 3 seconds
        // It runs for 20 seconds and is then restarted
        new CountDownTimer(20000, 3000) {
            public void onTick(long millisUntilFinished) {
                changeBackgroundImage();
            }

            public void onFinish() {
                this.start(); //Restart the timer when it finishes
            }
        }.start();
    }

    /**
     * Cycles the background image through the 4 different images
     */
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
