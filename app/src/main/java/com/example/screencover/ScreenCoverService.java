package com.example.screencover;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.core.app.NotificationCompat;

public class ScreenCoverService extends Service {
    private WindowManager windowManager;
    private ImageView chatHead;


    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";
        String channelName = "My Background Service";
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.drawable.ic_fiber_smart_record_black_24dp)
                    .setContentTitle("App is running in background")
                    .setPriority(NotificationManager.IMPORTANCE_NONE)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
            startForeground(2, notification);
        }
    }

    @Override
    public IBinder onBind(Intent intent){ return null; }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null && intent.getAction().equals("on")) {


            startMyOwnForeground();


            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_PHONE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);

                params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

                params.gravity = Gravity.TOP | Gravity.LEFT;
                params.x = 0;
                params.y = 100;

                params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

                windowManager.addView(chatHead, params);


            }else{
                WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);

                params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

                params.gravity = Gravity.TOP | Gravity.LEFT;
                params.x = 0;
                params.y = 0;

                params.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

                windowManager.addView(chatHead, params);
            }

        }else if (intent != null && intent.getAction() != null && intent.getAction().equals("off")) {
            if (chatHead != null) windowManager.removeView(chatHead);
        }

        return START_STICKY; // or whatever floats your boat
    }


    @Override public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatHead = new ImageView(this);

        chatHead.setImageResource(R.drawable.black);
        chatHead.setScaleType(ImageView.ScaleType.FIT_XY);

        chatHead.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatHead != null) windowManager.removeView(chatHead);
    }
}

