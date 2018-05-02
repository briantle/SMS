package com.sms.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Set;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "";
    private RelativeLayout relativeLayout;
    private Button composeMessage;
    private AppCompatButton logOut;
    private AppCompatButton settings;
    private Button viewMessage;
    private int MY_PERMISSIONS_REQUEST_SMS_RECEIVE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_mainmenu);
        getSupportActionBar().hide();
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS},
                MY_PERMISSIONS_REQUEST_SMS_RECEIVE);

        createViews();
        initListeners();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SMS_RECEIVE) {
            Log.i("TAG", "MY_PERMISSIONS_REQUEST_SMS_RECEIVE --> YES");
        }
    }
    private void createViews(){
        relativeLayout = findViewById(R.id.relativeLayout);
        settings = findViewById(R.id.settings);
        logOut = findViewById(R.id.logOut);
        composeMessage = findViewById(R.id.composeMessage);
        viewMessage = findViewById(R.id.viewMessage);
    }
    private void initListeners(){
        composeMessage.setOnClickListener(this);
        logOut.setOnClickListener(this);
        settings.setOnClickListener(this);
        viewMessage.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.settings:
                Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intentSettings);
                break;
            case R.id.viewMessage:
                Intent intentViewMsgs = new Intent(getApplicationContext(), ReceiveMessage.class);
                startActivity(intentViewMsgs);
                break;
            case R.id.composeMessage:
                Intent intentMessage = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(intentMessage);
                break;
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
        }
    }/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.composeMessage:
                Intent intentMessage = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(intentMessage);
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            case R.id.settings:
                Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intentSettings);
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
