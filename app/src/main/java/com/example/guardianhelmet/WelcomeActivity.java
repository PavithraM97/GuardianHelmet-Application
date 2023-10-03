package com.example.guardianhelmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //  Handler to delay transitioning to the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the main activity after the splash screen timeout
                Intent mainIntent = new Intent(WelcomeActivity.this, AccountActivity.class);
                startActivity(mainIntent);
                finish(); // Close the welcome activity
            }
        }, SPLASH_TIMEOUT);
    }
}