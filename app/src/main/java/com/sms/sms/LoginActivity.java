package com.sms.sms;


import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private final AppCompatActivity activity = LoginActivity.this;
    private DatabaseManagement myDb;
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
                break;
            case R.id.textViewLinkRegister:
                break;
        }
    }
}

