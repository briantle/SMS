package com.sms.activities;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
public class SMSBroadcastReceiver extends android.content.BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        //Specify bundle based on SMS protocol
        assert bundle != null;
        Object[] objects = (Object[]) bundle.get("pdus");
        SmsMessage sms[] = new SmsMessage[0];
        if (objects != null) {
            sms = new SmsMessage[objects.length];
        }
        Intent newIntent = new Intent(context, ReceiveMessage.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        String message;
        String originNm = "";
        StringBuffer sb = new StringBuffer();
        assert objects != null;
        for(int i = 0; i < objects.length; i++){
            sms[i] = SmsMessage.createFromPdu((byte[]) objects[i]);
            message = sms[i].getDisplayMessageBody();
            originNm = sms[i].getDisplayOriginatingAddress();
            sb.append(message);
            abortBroadcast();
        }
        newIntent.putExtra("originNm", originNm);
        newIntent.putExtra("message", new String(sb));
        //Start the messageDisplay
        context.startActivity(newIntent);

    }
}
