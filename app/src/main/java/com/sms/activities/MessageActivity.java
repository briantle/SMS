package com.sms.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.telephony.SmsManager;
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

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import database.FirebaseHelper;
import inputvalidation.InputErrorChecking;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = MessageActivity.this;
    private Button sendMessage;
    private MultiAutoCompleteTextView messageBox;
    private CheckBox encryptionCheck;
    private EditText keyInput;
    private EditText recipientBox;
    private RelativeLayout relativeLayout;
    private InputErrorChecking iE;
    private FirebaseHelper firebaseHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        getSupportActionBar().hide();

        createViews();
        iE = new InputErrorChecking(activity);
        firebaseHelper = new FirebaseHelper(activity);
    }

    private void createViews() {
        relativeLayout = findViewById(R.id.relativeLayout);
        sendMessage = findViewById(R.id.sendMessage);
        keyInput = findViewById(R.id.keyInput);
        messageBox = findViewById(R.id.messageBox);
        recipientBox = findViewById(R.id.recipientBox);
    }

    public void onClick(View view)
    {
        // Hides the virtual keyboard from view after user clicks on a button
        //InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        switch(view.getId())
        {
            case R.id.messageBox:
                //sendMessage();
                break;
            case R.id.cancelButton:
                // This switches the view back to the login view
                Intent intentMainMenu = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(intentMainMenu);
                break;
        }
    }

    //sendMessage sends a message to a receiving number

    private void sendMessage(String recNumStr, String encryptedMsg){
        String message = messageBox.getText().toString().trim();
        String key = keyInput.getText().toString().trim();
        try {
            //Create smsManager client and send message via library function
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage(encryptedMsg);
            smsManager.sendMultipartTextMessage(recNumStr, null, parts, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

