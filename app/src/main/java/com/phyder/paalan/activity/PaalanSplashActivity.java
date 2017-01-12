package com.phyder.paalan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.phyder.paalan.R;

public class PaalanSplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paalan_splash);

        Thread timer = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(SPLASH_TIME_OUT);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(PaalanSplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };timer.start();

    }

    @Override
    public void onBackPressed() {
    }
}
