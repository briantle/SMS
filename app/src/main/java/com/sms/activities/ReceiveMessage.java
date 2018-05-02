package com.sms.activities;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import static Encryption.encryptDecrypt.decrypt;


public class ReceiveMessage extends AppCompatActivity implements View.OnClickListener{
    TextView senderNum;
    TextView encryptedMsg;
    TextView decryptedMsg;
    Button submit;
    Button cancel;
    EditText secretKey;

    String originalNum = "";
    //Temporary string to hold the message
    String msgContent = "";
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_receivemessage);
        senderNum = (TextView) findViewById(R.id.senderNum);
        encryptedMsg = (TextView) findViewById(R.id.encryptedMsg);
        decryptedMsg = (TextView) findViewById(R.id.decryptedMsg);
        submit = (Button) findViewById(R.id.submit);
        cancel = (Button) findViewById(R.id.cancel);
        secretKey = (EditText) findViewById(R.id.secretKey);
        // Retrieve intent extras
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            //Retrieve sender phone number from extra
            originalNum = extras.getString("originalNum");
            //Retrieve encrypted message
            msgContent = extras.getString("message");
            //Set text fields in UI
            senderNum.setText(originalNum);
            encryptedMsg.setText(msgContent);
        }
        else{
            //Was unable to retrieve intent
            Toast.makeText(getBaseContext(),"Error occured.", Toast.LENGTH_SHORT).show();
            //finish();
        }
        //Make cancel exit
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });
        //When clicking submit, decrypt the message
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //Have user input the key
                String secretKeyStr = secretKey.getText().toString();
                //Key length should be 16 characters
                if(secretKeyStr.length() == 16){
                    try {
                        //Convert string to a byte array
                      //  byte[] message = hexStringToByteArray(new String(msgContent.getBytes()));
                        byte[] message = hex2byte(msgContent.getBytes());
                        //Decrypt
                        byte[] result = decrypt(secretKey.getText().toString(), message);
                        //byte[] result = decrypt(secretKeyStr, msgContent.getBytes());
                        decryptedMsg.setText(new String(result));

                    } catch (Exception e) {
                        //If corrupted key, should be an error
                        decryptedMsg.setText("Invalid key");
                    }
                }
                else{
                    Toast.makeText(getBaseContext(), "You must provide a 16 character key",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static byte[] hex2byte(byte[] b) {

        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("hello");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
    @Override
    public void onClick(View view) {

    }
}

