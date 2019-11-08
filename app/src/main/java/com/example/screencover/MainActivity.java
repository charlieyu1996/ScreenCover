package com.example.screencover;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class MainActivity extends Activity {
    NotificationManagerCompat notificationManager;
    Switch notiSwitch;
    int notificationId = 1;

    public void cancelNoti(){
        notificationManager.cancel(notificationId);
    }

    public void createNoti(){
        notificationManager = NotificationManagerCompat.from(this);
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);


        String channelId = "channel-01";
        String channelName = "Channel Name";
        // this is set to low so it will not do a heads up
        int importance = NotificationManager.IMPORTANCE_LOW;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }


        Intent screenIntent = new Intent(this, ScreenCoverService.class).setAction("on");
        PendingIntent screenPendingIntent = PendingIntent.getService(this, 0, screenIntent,0);

        Intent screenIntent2= new Intent(this, ScreenCoverService.class).setAction("off");
        PendingIntent screenPendingIntent2 = PendingIntent.getService(this, 0, screenIntent2,0);


        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent mainPendingIntent = PendingIntent.getActivity(this,0, mainIntent,0);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_fiber_smart_record_black_24dp)
                .setContentTitle("ScreenCover")
                .setOngoing(true)
                .setContentIntent(mainPendingIntent)
                .addAction(R.drawable.ic_fiber_smart_record_black_24dp, "Start", screenPendingIntent)
                .addAction(R.drawable.ic_fiber_smart_record_black_24dp, "Stop", screenPendingIntent2);

        notificationManager.notify(notificationId, mBuilder.build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Create a notification that controls on and off switches
        createNoti();



        // when the user switches the switch
        notiSwitch = findViewById(R.id.notiSwitch);
        notiSwitch.setChecked(true);

        notiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    createNoti();
                } else {
                    // The toggle is disabled
                    cancelNoti();
                }
            }
        });
    }
}
