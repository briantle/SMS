package com.sms.activities;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import inputvalidation.InputErrorChecking;
import users.User;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private final AppCompatActivity activity = LoginActivity.this;
    private InputErrorChecking iE;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPassword;
    private EditText InputUserId;
    private EditText InputPassword;
    private AppCompatButton appCompatButtonLogin;
    private AppCompatTextView textViewLinkRegister;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        getViewReference();
        initListeners();
        initObjects();
    }
    private void getViewReference(){
        nestedScrollView = findViewById(R.id.nestedScrollView);
        textInputLayoutUsername = findViewById(R.id.textInputLayoutUsername);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        InputUserId = findViewById(R.id.InputUserId);
        InputPassword = findViewById(R.id.InputPassword);
        appCompatButtonLogin = findViewById(R.id.appCompatButtonLogin);
        textViewLinkRegister = findViewById(R.id.textViewLinkRegister);
    }
    private void initListeners(){
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }
    private void initObjects(){
        progressBar = new ProgressBar(activity);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://smssoftware-5c2d1.firebaseio.com/users");
        firebaseAuth = FirebaseAuth.getInstance();
        iE = new InputErrorChecking(activity);
    }
    @Override
    public void onClick(View view)
    {
        // Hides the virtual keyboard from view after user clicks on a button
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        switch(view.getId())
        {
            // User clicked on sign in button
            case R.id.appCompatButtonLogin:
                checkDatabase();
                break;
            // User clicked on register button
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
        if (!iE.isTextBoxFilled(textInputLayoutUsername, InputUserId, getString(R.string.error_empty_input))
         || !iE.isTextBoxFilled(textInputLayoutPassword, InputPassword, getString(R.string.error_empty_input)))
            return;

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                String username = InputUserId.getText().toString().trim().toLowerCase();
                String password = InputPassword.getText().toString().trim();
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    User databaseUser = dataSnapshot.getValue(User.class);
                    if (databaseUser.getUsername().matches(username) && databaseUser.getPassword().matches(password)) {
                        // Found the user
                        resetText();
                        // Switches the view to the main menu activity view
                        Intent intentMainMenu = new Intent(getApplicationContext(), MainMenuActivity.class);
                        startActivity(intentMainMenu);
                    }
                    // Found username but password is wrong
                    else if (databaseUser.getUsername().matches(username) && !databaseUser.getPassword().matches(password))
                        Snackbar.make(nestedScrollView, getString(R.string.error_wrong_password), Snackbar.LENGTH_LONG).show();
                        // The user doesn't exist in database
                    else
                        Snackbar.make(nestedScrollView, getString(R.string.error_user_doesnt_exist), Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
    /**
     * This method is to empty all input edit text
     */
    private void resetText() {
        InputUserId.setText(null);
        InputPassword.setText(null);
    }
}

