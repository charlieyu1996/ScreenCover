package com.example.screencover;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class ScreenCoverService extends Service {
    private WindowManager windowManager;
    private ImageView chatHead;

    @Override
    public IBinder onBind(Intent intent){
        // not used
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null && intent.getAction().equals("on")) {
            Log.d("Debug", "It is on now");

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
            Log.d("Debug", "It is off now");
            if (chatHead != null) windowManager.removeView(chatHead);
        }



        return START_STICKY; // or whatever floats your boat
    }


    @Override public void onCreate() {
        super.onCreate();

        Log.d("Debug", "Cover Created");

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

