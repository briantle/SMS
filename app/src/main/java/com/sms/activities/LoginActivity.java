package com.sms.activities;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import database.DatabaseManagement;
import inputvalidation.InputErrorChecking;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private final AppCompatActivity activity = LoginActivity.this;
    private DatabaseManagement myDb;
    private InputErrorChecking iE;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPassword;
    private EditText InputUserId;
    private EditText InputPassword;
    private AppCompatButton appCompatButtonLogin;
    private AppCompatTextView textViewLinkRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        createViews();
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
        // Set up database
        myDb = new DatabaseManagement(activity);
        iE = new InputErrorChecking(activity);
    }
    private void createViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        textInputLayoutUsername = (TextInputLayout) findViewById(R.id.textInputLayoutUsername);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        InputUserId = (EditText) findViewById(R.id.InputUserId);
        InputPassword = (EditText) findViewById(R.id.InputPassword);
        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);
        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.appCompatButtonLogin:
                checkDatabase();
                break;
            case R.id.textViewLinkRegister:
                // Switches the view to the register activity view
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }
    private void checkDatabase(){
        if (!iE.isTextBoxFilled(textInputLayoutUsername, InputUserId, getString(R.string.error_empty_input)))
            return;
        if (!iE.isTextBoxFilled(textInputLayoutPassword, InputPassword, getString(R.string.error_empty_input)))
            return;

        String username = InputUserId.getText().toString().trim();
        String password = InputPassword.getText().toString().trim();
        // If the username and password was found in the database
        if (myDb.findUser(nestedScrollView, username, password, getString(R.string.error_invalid_login_info)))
        {
            // Switches the view to the main menu activity view
            Intent intentMainMenu = new Intent(getApplicationContext(), MainMenuActivity.class);
            startActivity(intentMainMenu);
        }
    }
}

