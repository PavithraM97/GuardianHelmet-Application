package com.example.guardianhelmet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.inappmessaging.FirebaseInAppMessaging;

public class InappMessagingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inapp_messaging);

        // Initialize Firebase In-App Messaging
        FirebaseInAppMessaging.getInstance().setAutomaticDataCollectionEnabled(true);

        // Trigger a test message (for testing purposes)
        FirebaseInAppMessaging.getInstance().triggerEvent("GuardianHelmetCampaign");
    }
}