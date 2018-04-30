package com.sms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "";
    private LinearLayout linearLayout;
    private TabLayout tabLayout;
    private TabItem chatTab;
    private TabItem contactsTab;
    private MenuItem logOut;
    private MenuItem settings;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        getSupportActionBar().hide();

        createViews();
    }
    private void createViews(){
        linearLayout = findViewById(R.id.linearLayout);
        settings = findViewById(R.id.settings);
        logOut = findViewById(R.id.logOut);
    }

    @Override
    public void onClick(View view) {

    }
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
                Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intentSettings);
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
    }
}
}
