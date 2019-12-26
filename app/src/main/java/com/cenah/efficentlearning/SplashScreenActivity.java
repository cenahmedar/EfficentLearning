package com.cenah.efficentlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.cenah.efficentlearning.helpers.ApplicationPreferenceManager;
import com.cenah.efficentlearning.helpers.AuthMainPageIntent;

public class SplashScreenActivity extends AppCompatActivity {

    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        rootView = getWindow().getDecorView().getRootView();

        if (new ApplicationPreferenceManager(getApplicationContext()).getSharedInfo() != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new AuthMainPageIntent(new ApplicationPreferenceManager(SplashScreenActivity.this).getSharedInfo().getUserRole(),SplashScreenActivity.this).Page();
                }
            }, 2000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                }
            }, 2000);


        }
    }
}
