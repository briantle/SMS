package com.sms.activities;


import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.os.Bundle;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import database.DatabaseManagement;
import database.FirebaseHelper;
import inputvalidation.InputErrorChecking;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private final AppCompatActivity activity = LoginActivity.this;
    private DatabaseManagement myDb;
    private FirebaseHelper fbHelper;
    private InputErrorChecking iE;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPassword;
    private EditText InputUserId;
    private EditText InputPassword;
    private AppCompatButton appCompatButtonLogin;
    private AppCompatTextView textViewLinkRegister;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        getViewReference();
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://smssoftware-5c2d1.firebaseio.com/users");

        // Set up database
        myDb = new DatabaseManagement(activity);
        fbHelper = new FirebaseHelper(activity);
        iE = new InputErrorChecking(activity);
    }
    private void getViewReference(){
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
    private void checkDatabase()
    {
        // If the user left any of the text boxes blank
        if (!iE.isTextBoxFilled(textInputLayoutUsername, InputUserId, getString(R.string.error_empty_input)))
            return;
        if (!iE.isTextBoxFilled(textInputLayoutPassword, InputPassword, getString(R.string.error_empty_input)))
            return;

        final String username = InputUserId.getText().toString().trim();
        final String password = InputPassword.getText().toString().trim();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                // Found the user
                if (snapshot.child("users").hasChild(username) && snapshot.child("users").child(username).child("password").getValue().equals(password)) {
                    // Switches the view to the main menu activity view
                    Intent intentMainMenu = new Intent(getApplicationContext(), MainMenuActivity.class);
                    startActivity(intentMainMenu);
                }
                // Found username but password is wrong
                else if (snapshot.child("users").hasChild(username) && !snapshot.child("users").child(username).child("password").getValue().equals(password)){
                    Snackbar.make(nestedScrollView, getString(R.string.error_wrong_password), Snackbar.LENGTH_LONG).show();
                }

                // The user doesn't exist in database
                else{
                    Snackbar.make(nestedScrollView, getString(R.string.error_user_doesnt_exist), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

