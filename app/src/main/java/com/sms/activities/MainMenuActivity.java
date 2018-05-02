package com.sms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "";
    private RelativeLayout relativeLayout;
    private Button composeMessage;
    private AppCompatButton logOut;
    private AppCompatButton settings;
    private Button viewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        getSupportActionBar().hide();

        createViews();
        initListeners();
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
    }
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.composeMessage:
                Intent intentMessage = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(intentMessage);
                break;
            case R.id.logOut:
                //FirebaseAuth.getInstance().signOut();
                finish();
            case R.id.viewMessage:
                Intent intentSettings = new Intent(getApplicationContext(), ReceiveMessage.class);
                startActivity(intentSettings);
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
