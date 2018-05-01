package com.sms.activities;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class ReceiveMessage extends Activity{
    TextView senderNum;
    TextView encryptedMsg;
    TextView decryptedMsg;
    Button send;
    Button cancel;
    EditText secretKey;

    String originalNum = "";
    //Temporary string to hold the message
    String msgContent = "";
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(this.savedInstanceState);
        //setContentView(R.layout.onreceive);
        senderNum = (TextView) findViewById(R.id.senderNum);
        encryptedMsg = (TextView) findViewById(R.id.encryptedMsg);
        decryptedMsg = (TextView) findViewById(R.id.decryptedMsg);
        send = (Button) findViewById(R.id.send);
        cancel = (Button) findViewById(R.id.cancel);
        secretKey = (EditText) findViewById(R.id.secretKey);
        // Retrieve intent extras
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            //Retrieve sender phone number from extra
            originalNum = extras.getString("originalNum");
            //Retrieve encrypted message
            msgContent = extras.getstring("msgContent");
            //Set text fields in UI
            senderNum.setText(originalNum);
            encryptedMsg.setText(msgContent);
        }
        else{
            //Was unable to retrieve intent
            Toast.makeText(getBaseContext(),"Error occured.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}

