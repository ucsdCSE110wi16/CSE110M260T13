package com.prodevteam.tastebud;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PostLoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //protected static String[] restNames = {"Touch to begin", "ExampleRestaurant", "Pines", "64 Degrees", "Cafe Ventanas", "Canyon Vista", "Foodworx"};
    protected static String[] restNames = {"Touch to begin", "64 Degrees", "Cafe Ventanas"};
    protected static int selectedRestaurantIndex; // initialize to 0 (no restaurant)
    protected static String restaurantName;

    private static final float BG_IMAGE_OFFSET_Y = 100.0F;
    private static final float BG_IMAGE_OFFSET_X = 400.0F;

    private Matrix transformationMatrix;
    private ImageView bgImage;

    public void onMenuButtonClicked() {
        Intent intent = new Intent(this, MenuScreen.class);
        Spinner spinner = (Spinner) findViewById(R.id.rest_selector);
        startActivity(intent.putExtra("restaurantName", restNames[spinner.getSelectedItemPosition()]));
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

        // Scale and center the background image
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
        String name = App.currentUser.getName();
        String firstName = name.split(" ")[0];

        name_text.setText(firstName.toCharArray(), 0, firstName.length());

        // Set up the Menu button
        Button menuButton = (Button) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onMenuButtonClicked();
            }
        });

        // Set up the Recommendations button
        Button recButton = (Button) findViewById(R.id.rec_button);
        recButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecButtonClicked();
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

        Spinner spinner = (Spinner)findViewById(R.id.rest_selector);
        //ArrayAdapter<String>adapter = new ArrayAdapter<>(PostLoginActivity.this,
         //       android.R.layout.simple_spinner_item, restNames);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, restNames) {
            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                v.setAlpha(0.7f);
                ((TextView) v).setGravity(Gravity.CENTER);
                return v;
            }
            public View getDropDownView(int position, View convertView,ViewGroup parent) {
                View v = super.getDropDownView(position, convertView,parent);
                ((TextView) v).setGravity(Gravity.CENTER);
                v.setBackground(ContextCompat.getDrawable(PostLoginActivity.this, R.drawable.dropdown_item_border));
                v.setAlpha(0.7f);
                return v;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinner.setSelection(selectedRestaurantIndex);
    }

    private void onRecButtonClicked() {
        Spinner spinner = (Spinner) findViewById(R.id.rest_selector);
        startActivity(new Intent(this, RecommendationScreen.class).putExtra("restaurantName", restNames[spinner.getSelectedItemPosition()]));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        restaurantName = restNames[position];
        if((selectedRestaurantIndex = position) == 0) {
            Button menuButton = (Button) findViewById(R.id.menu_button);
            Button recButton = (Button) findViewById(R.id.rec_button);
            menuButton.setVisibility(View.INVISIBLE);
            recButton.setVisibility(View.INVISIBLE);
            return;
        }

        Button menuButton = (Button) findViewById(R.id.menu_button);
        Button recButton = (Button) findViewById(R.id.rec_button);

        // TODO: Make these buttons fade in
        menuButton.setVisibility(View.VISIBLE);
        recButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}