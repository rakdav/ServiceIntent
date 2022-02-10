package com.example.serviceintent;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;

public class SimpleIntentService extends IntentService {

    public  static volatile boolean shouldStop=false;
    public static final String ACTION_1="MY_ACTION_1";
    public static final String PARAM_PERCENT="percent";

    public SimpleIntentService() {
        super("SimpleIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent broadcastintent=new Intent();
        broadcastintent.setAction(SimpleIntentService.ACTION_1);
        for (int i=0;i<=100;i++)
        {
            broadcastintent.putExtra(PARAM_PERCENT,i);
            sendBroadcast(broadcastintent);
            SystemClock.sleep(100);
            if(shouldStop)
            {
                stopSelf();
                return;
            }
        }
    }
}