package com.hodo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


import com.hodo.R;

import org.androidannotations.annotations.EActivity;


@EActivity(R.layout.splash_screen_view)
public class SplashActivity extends Activity {

    private final int TIME_UP = 1;
    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            if(msg.what == TIME_UP)
            {
                Intent intent = new Intent();
                //intent.setClass(SplashActivity.this, LoginActivity_.class);
                intent.setClass(SplashActivity.this, TabActivity.class);
                startActivity(intent);
//				overridePendingTransition(R.anim.splash_screen_fade, R.anim.splash_screen_hold);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                SplashActivity.this.finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        new Thread()
        {
            public void run()
            {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                Message msg = new Message();
                msg.what = TIME_UP;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
