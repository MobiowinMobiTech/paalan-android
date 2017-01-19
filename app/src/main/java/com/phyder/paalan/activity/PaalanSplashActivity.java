package com.phyder.paalan.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.phyder.paalan.R;
import com.phyder.paalan.db.Attributes;
import com.phyder.paalan.db.DBAdapter;

public class PaalanSplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    private DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paalan_splash);

        dbAdapter = new DBAdapter(PaalanSplashActivity.this);
        dbAdapter.open();
        if(dbAdapter.getMasterTableCount()<1){
            dbAdapter.insertTimeSpan("0","0","0");
        }
        dbAdapter.close();

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
