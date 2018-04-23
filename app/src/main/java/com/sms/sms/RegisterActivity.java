package com.sms.sms;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        InitActivity();
    }
    private void InitActivity(){
        createView();
        createListeners();
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
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_signup:
                break;
            case R.id.link_login:
                finish();
        }
    }
}
