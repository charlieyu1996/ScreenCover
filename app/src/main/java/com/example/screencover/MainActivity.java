package com.example.screencover;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class MainActivity extends Activity {
    NotificationManagerCompat notificationManager;
    Button showChatHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // when pressed the button
        showChatHead = findViewById(R.id.buttonNoti);

        showChatHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "This is a message displayed in a Toast",
                        Toast.LENGTH_SHORT);

                toast.show();
                Intent i = new Intent(getApplicationContext(), ScreenCoverService.class);
                startService(i);
            }
        });


        notificationManager = NotificationManagerCompat.from(this);


//        Intent intent = getIntent();

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        // this is set to low so it will not do a heads up
        int importance = NotificationManager.IMPORTANCE_LOW;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }


        Intent screenIntent = new Intent(this, ScreenCoverService.class).setAction("called");

        screenIntent.putExtra("called", true);

        PendingIntent screenPendingIntent = PendingIntent.getService(this, 0, screenIntent,0);




        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_fiber_smart_record_black_24dp)
                .setContentTitle("hi")
                .setContentText("lol")
                .setOngoing(true)
                .addAction(R.drawable.ic_fiber_smart_record_black_24dp, "Start", screenPendingIntent);
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addNextIntent(intent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
//                0,
//                PendingIntent.FLAG_UPDATE_CURRENT
//        );
//        mBuilder.setContentIntent(resultPendingIntent);



        notificationManager.notify(notificationId, mBuilder.build());


//        startService(new Intent(this, ScreenCoverService.class));


    }
}
