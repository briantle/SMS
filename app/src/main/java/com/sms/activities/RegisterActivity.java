package com.sms.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import database.FirebaseHelper;
import inputvalidation.InputErrorChecking;
import users.User;

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
    private FirebaseAuth firebaseAuth;
    private FirebaseHelper firebaseHelper;
    private InputErrorChecking iE;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://smssoftware-5c2d1.firebaseio.com/users");
        firebaseAuth = FirebaseAuth.getInstance();

        InitActivity();
    }
    private void InitActivity(){
        createView();
        createListeners();
        initObjects();
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
    private void initObjects(){
        progressDialog = new ProgressDialog(activity);
        iE = new InputErrorChecking(activity);
        firebaseHelper = new FirebaseHelper(activity);
    }
    private void getUserInfo()
    {
        String[] errMsgArray = {getString(R.string.error_password_length), getString(R.string.error_password_must_contain_numeric)};
        // Make sure the user fills the text fields in
        if (!iE.isTextBoxFilled(InputLayoutUsername, input_username, getString(R.string.error_empty_input))
         || !iE.isTextBoxFilled(InputLayoutEmail, input_email, getString(R.string.error_empty_input))
         || !iE.isTextBoxFilled(InputLayoutPw, input_password, getString(R.string.error_empty_input))
         || !iE.isTextBoxFilled(InputLayoutConfirmPw, input_passwordConfirm, getString(R.string.error_empty_input)))
            return;
        // Check for valid email
        if (!iE.isEmailValid(input_email.getText().toString().trim())) {
            InputLayoutEmail.setError(getString(R.string.error_invalid_email));
            return;
        }
        // Check to see if password is valid (at least 6 characters, has at least 1 numeric value)
        if (!iE.isPasswordValid(InputLayoutPw, input_password, errMsgArray))
            return;
        // Check if the password and password confirmation match
        if (!iE.doesConfirmationMatch(InputLayoutPw, input_password, input_passwordConfirm, getString(R.string.error_pwConfirmation_nonmatch)))
            return;
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                final String username = input_username.getText().toString().trim().toLowerCase();
                String email = input_email.getText().toString().trim();
                final String password = input_password.getText().toString().trim();

                // The username has already been taken
                if (firebaseHelper.doesUserNameExist(username, snapshot))
                    InputLayoutUsername.setError(getString(R.string.error_username_exists));
                // A user has already registered with that email
                else if (firebaseHelper.doesEmailExist(email, snapshot))
                    InputLayoutEmail.setError(getString(R.string.error_email_exists));
                // Otherwise, the user doesn't exist in the database so create that user
                else {
                    progressDialog.setMessage("Registering user....");
                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                firebaseHelper.addUser(firebaseUser.getUid(), username, firebaseUser.getEmail(), password);
                                // Snack Bar to show success message that record saved successfully
                                Snackbar.make(linearLayout, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
                                resetText();
                                progressDialog.hide();
                            } else {
                                // If sign in fails, display a message to the user.
                                Snackbar.make(linearLayout, "Failed to sign in user: " + username, Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
    @Override
    public void onClick(View view)
    {
        // Hides the virtual keyboard from view after user clicks on a button
       // InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
       // inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
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
