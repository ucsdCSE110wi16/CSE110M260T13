package com.prodevteam.tastebud;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PostLoginActivity extends ActionBarActivity implements SensorEventListener {

    private static final float BG_IMAGE_OFFSET_Y = 100.0F;
    private static final float BG_IMAGE_OFFSET_X = 400.0F;

    private SensorManager sensorManager;
    private Sensor gyroscope;
    private Matrix transformationMatrix;
    private ImageView bgImage;

    private static final float NS2S = 1.0f / 1000000000.0f;
    private final float[] deltaRotationVector = new float[4];

    @Override
    public void onSensorChanged(SensorEvent event) {
        float axisY = event.values[1];
        float axisZ = event.values[2];

        // TODO: Add movement to bgImage to create a parallax effect
        // When the device is tilted down, move image up, etc.
        /*
        if(axisY > 0) transformationMatrix.postTranslate(0.0F, -5.0F);
        bgImage.setImageMatrix(transformationMatrix);
        */
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onMenuButtonClicked() {
        Intent intent = new Intent(this, MenuScreen.class);
        intent.putExtra("a", "b");
        startActivity(intent);
    }

    public void signoutButtonClicked() {
        App.currentUser = null;
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void myAccountButtonClicked() {
        startActivity(new Intent(this, AccountSettingsScreen.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_post_login);

        // Set up the gyroscope and the background image
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_GAME);

        // Scale and center the background image
        // TODO: Test this image scaling on large displays (1080p)
        transformationMatrix = new Matrix();
        transformationMatrix.setScale(.90F, .90F);
        transformationMatrix.postTranslate(-BG_IMAGE_OFFSET_X,-BG_IMAGE_OFFSET_Y);
        bgImage = (ImageView) findViewById(R.id.post_login_bg);
        bgImage.setImageMatrix(transformationMatrix);

        // Get the time of day
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        TextView greeting_text = (TextView) findViewById(R.id.greetings_text);

        // Set the greeting text according to the time of day
        if(hour < 12) greeting_text.setText(R.string.greeting_morning);
        else if(hour < 17) greeting_text.setText(R.string.greeting_afternoon);
        else greeting_text.setText(R.string.greeting_evening);


        TextView name_text = (TextView) findViewById(R.id.user_fname);

        // Retrieve the user's first name
        String firstName = App.currentUser.getFirstName();

        name_text.setText(firstName.toCharArray(), 0, firstName.length());

        // Set up the Menu button
        Button menuButton = (Button) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onMenuButtonClicked();
            }
        });

        //set up the signout button
        Button signoutButton = (Button) findViewById(R.id.signout_button);
        signoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signoutButtonClicked();
            }
        });

        // set up the my account button
        Button myAccountButton = (Button) findViewById(R.id.my_acc_button);
        myAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAccountButtonClicked();
            }
        });
    }
}