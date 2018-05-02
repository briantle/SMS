package com.sms.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import java.security.Key;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import inputvalidation.InputErrorChecking;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = MessageActivity.this;
    private Button sendMessage;
    private MultiAutoCompleteTextView messageBox;
    private CheckBox encryptionCheck;
    private EditText keyInput;
    private RelativeLayout relativeLayout;
    private InputErrorChecking iE;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        getSupportActionBar().hide();

        createViews();
        initListeners();
        iE = new InputErrorChecking(activity);
    }
    private void createViews(){
        relativeLayout = findViewById(R.id.relativeLayout);
        sendMessage = findViewById(R.id.sendMessage);
        encryptionCheck = findViewById(R.id.encryptionCheck);
        keyInput = findViewById(R.id.keyInput);
        messageBox = findViewById(R.id.messageBox);

    }
    private void initListeners(){/*
        sendMessage.setOnClickListener(this);
        encryptionCheck.setOnClickListener(this);
        keyInput.setOnClickListener(this);
        messageBox.setOnClickListener(this);*/
    }
    public void onClick(View view) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        switch (view.getId()) {
            case R.id.composeMessage:
                Intent intentMessage = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(intentMessage);
            case R.id.logOut:
                //FirebaseAuth.getInstance().signOut();
                finish();
            case R.id.settings:
                Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intentSettings);
        }
    }
}

