package com.sms.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import database.DatabaseManagement;
import database.FirebaseHelper;
import inputvalidation.InputErrorChecking;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = RegisterActivity.this;

    private LinearLayout linearLayout;
    private TextInputLayout InputLayoutUsername;
    private TextInputLayout InputLayoutEmail;
    private TextInputLayout InputLayoutPw;
    private TextInputLayout InputLayoutConfirmPw;
    private EditText input_username;
    private EditText input_email;
    private EditText input_password;
    private EditText input_passwordConfirm;
    private AppCompatButton btn_signup;
    private TextView link_login;

    // Used to push data into the database
    private DatabaseReference databaseReference;
    private FirebaseHelper firebaseHelper;
    private DatabaseManagement db;
    private InputErrorChecking iE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://smssoftware-5c2d1.firebaseio.com/users");

        InitActivity();
    }
    private void InitActivity(){
        createView();
        createListeners();
        db = new DatabaseManagement(activity);
        iE = new InputErrorChecking(activity);
        firebaseHelper = new FirebaseHelper(activity);
    }
    private void createView(){
        linearLayout = findViewById(R.id.linearLayout);
        InputLayoutUsername = findViewById(R.id.InputLayoutUsername);
        InputLayoutEmail = findViewById(R.id.InputLayoutEmail);
        InputLayoutPw = findViewById(R.id.InputLayoutPw);
        InputLayoutConfirmPw = findViewById(R.id.InputLayoutConfirmPw);
        input_username = findViewById(R.id.input_username);
        input_email = findViewById(R.id.input_email);
        input_password = findViewById(R.id.input_password);
        input_passwordConfirm = findViewById(R.id.input_passwordConfirm);
        btn_signup = findViewById(R.id.btn_signup);
        link_login = findViewById(R.id.link_login);
    }
    private void createListeners(){
        btn_signup.setOnClickListener(this);
        link_login.setOnClickListener(this);
    }
    private void getUserInfo(){
        String userName = input_username.getText().toString().trim();
        String password = input_password.getText().toString().trim();
        String passCombination = input_passwordConfirm.getText().toString().trim();
        String email = input_email.getText().toString().trim();

        // Make sure the user fills the text fields in
        if (!iE.isTextBoxFilled(InputLayoutUsername, input_username, getString(R.string.error_empty_input)))
            return;
        if (!iE.isTextBoxFilled(InputLayoutEmail, input_email, getString(R.string.error_empty_input)))
            return;
        if (!iE.isTextBoxFilled(InputLayoutPw, input_password, getString(R.string.error_empty_input)))
            return;
        if (!iE.isTextBoxFilled(InputLayoutConfirmPw, input_passwordConfirm, getString(R.string.error_empty_input)))
            return;

        // Check if the password and password confirmation match
        if (!iE.doesConfirmationMatch(InputLayoutPw, input_password, input_passwordConfirm, getString(R.string.error_pwConfirmation_nonmatch)))
            return;

        // Check if the username or email already exists in the database
        if (db.doesUserExist(userName)) {
            InputLayoutUsername.setError(getString(R.string.error_username_exists));
            return;
        }
        else if (db.doesEmailExist(email)) {
            InputLayoutEmail.setError(getString(R.string.error_email_exists));
            return;
        }
        else {
            // Otherwise, create the user and store data in database
            firebaseHelper.addUser(userName, email, password);
            // Snack Bar to show success message that record saved successfully
            Snackbar.make(linearLayout, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            resetText();
        }
    }
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btn_signup:
                getUserInfo();
                break;
            case R.id.link_login:
                // This switches the view back to the login view
                finish();
                break;
        }
    }
    /**
     * This method is to empty all input edit text
     */
    private void resetText() {
        input_username.setText(null);
        input_password.setText(null);
        input_email.setText(null);
        input_passwordConfirm.setText(null);
    }
}
