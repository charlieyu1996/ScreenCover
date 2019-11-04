package com.example.screencover;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CoverBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getStringExtra("action");
        if(action.equals("on")){
            startScreen(true, context);
        }
        else {
            startScreen(false, context);

        }
    }

    public void startScreen(boolean state, Context context){
        if (state){
            Intent i = new Intent(context, ScreenCoverService.class);
            context.startService(i);
        }


    }
}
