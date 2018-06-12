package com.unifi.federicoguerri.traineeship_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startMainActivity();
    }

    private void endActivity() {
        finish();
    }

    @Override
    public void onBackPressed() {
        endActivity();
    }


    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        endActivity();
        overridePendingTransition(R.anim.start_app_enter,R.anim.start_app_exit);
    }
}
