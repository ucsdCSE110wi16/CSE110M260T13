package com.prodevteam.tastebud;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Drawable[] backgrounds;
    private int imageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView bgimage = (ImageView) findViewById(R.id.bgimage);
        backgrounds = new Drawable[2];
        imageIndex = 4;
        bgimage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.login_4));

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

        Button employeeButton = (Button) findViewById(R.id.emp_signin_button);
        employeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmployeeSignInClick();
            }
        });
    }

    private void onEmployeeSignInClick() {
        // Retrieving the email address and password fields
        EditText emailBar = (EditText) findViewById(R.id.email_field);
        EditText passwordBar = (EditText) findViewById((R.id.pass_field));
        String password = passwordBar.getText().toString();
        String userEmail = emailBar.getText().toString();

        // Login to the app
        new AsyncTask<String, Void, String[]>() {
            @Override
            protected String[] doInBackground(String...params) {
                return App.sqlConnection.attemptEmployeeLogin(params[0], params[1]);
            }

            @Override
            protected void onPostExecute(String[] result) {
                if(result == null)
                    Toast.makeText(LoginActivity.this, "Error signing in, invalid email address or password.", Toast.LENGTH_SHORT).show();
                else startActivity(new Intent(LoginActivity.this, EmployeeActivity.class).putExtra("restaurantName", result[1]).putExtra("email", result[0]));
            }
        }.execute(userEmail, password);
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

        ImageButton closeDialogButton = (ImageButton) findViewById(R.id.close_dialog_button);
        closeDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCloseDialogClicked(v.getParent(), v.getParent().getParent());
            }
        });
        // Create a new account on the database
    }

    private void onCloseDialogClicked(ViewParent parent, ViewParent gParent) {
        LinearLayout layout = (LinearLayout) parent;
        ((RelativeLayout) gParent).removeView(layout);
    }

    private void onContinueClick() {
        EditText name_field = (EditText) findViewById(R.id.name_field);
        EditText email_field = (EditText) findViewById(R.id.new_email_field);
        EditText pass_field = (EditText) findViewById(R.id.pass_field);

        final String name = name_field.getText().toString();
        final String email = email_field.getText().toString();
        final String pass = pass_field.getText().toString();

        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                return App.sqlConnection.checkForDuplicate(params[0]);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if(result) {
                    insertAccount(email, pass, name);
                    App.currentUser = new App.UserInfo(name, email, pass, "", "");
                }
                else Toast.makeText(LoginActivity.this, "An account already exists with this email.", Toast.LENGTH_SHORT).show();
            }
        }.execute(email);
    }

    private void insertAccount(String email, String pass, String name) {
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                return App.sqlConnection.createNewAccount(params[0], params[1], params[2]);
            }

            @Override
            protected void onPostExecute(Boolean result) {
                Intent intent = new Intent(LoginActivity.this, AccountSettingsScreen.class);
                startActivity(intent);
            }
        }.execute(email, pass, name);
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
            protected void onPostExecute(final App.UserInfo result) {
                if(result == null)
                    Toast.makeText(LoginActivity.this, "Error signing in, invalid email address or password.", Toast.LENGTH_SHORT).show();
                else {
                    new AsyncTask<String, Void, String>() {

                        @Override
                        protected String doInBackground(String... params) {
                            return App.sqlConnection.getUserIngs(params[0]);
                        }

                        @Override
                        protected void onPostExecute(String ings) {
                            result.setPastIngredients(ings);
                            startActivity(intent);
                        }
                    }.execute(userEmail);
                }
            }
        }.execute(userEmail, password);
    }
}
