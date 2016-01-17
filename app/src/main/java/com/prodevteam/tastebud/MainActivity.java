package com.prodevteam.tastebud;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

public class MainActivity extends ActionBarActivity {

    private int imageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        imageIndex = 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    changeBackgroundImage();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        }).run();
    }

    private int changeBackgroundImage() {
        RelativeLayout loginScreen = (RelativeLayout) findViewById(R.id.login_screen);
        imageIndex = (imageIndex % 4) + 1;
        switch(imageIndex) {
            case 1:
                loginScreen.setBackground(getResources().getDrawable(R.drawable.login_1));
                break;
            case 2:
                loginScreen.setBackground(getResources().getDrawable(R.drawable.login_2));
                break;
            case 3:
                loginScreen.setBackground(getResources().getDrawable(R.drawable.login_3));
                break;
            case 4:
                loginScreen.setBackground(getResources().getDrawable(R.drawable.login_4));
        }
        return 1;
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
