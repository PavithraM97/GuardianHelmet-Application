package com.example.guardianhelmet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        ImageView editProfileButton = findViewById(R.id.imageViewhome);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the ProfileActivity
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        ImageView weatherButton = findViewById(R.id.imageViewdashboard);
       weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the ProfileActivity
                Intent intent = new Intent(DashboardActivity.this, WeatherActivity.class);
                startActivity(intent);
            }
        });

        ImageView mapButton = findViewById(R.id.imageViewrosters);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the ProfileActivity
                Intent intent = new Intent(DashboardActivity.this,MapActivity.class);
                startActivity(intent);
            }
        });

        ImageView projectButton = findViewById(R.id.imageViewmeassaging);
        projectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the ProfileActivity
                Intent intent = new Intent(DashboardActivity.this,ViewProjectsActivity.class);
                startActivity(intent);
            }
        });

        ImageView galleryButton = findViewById(R.id.imageView4);
       galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the ProfileActivity
                Intent intent = new Intent(DashboardActivity.this,VideoGalleryActivity.class);
                startActivity(intent);
            }
        });


        ImageView workingHoursButton = findViewById(R.id.imageViewtrack);
        workingHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the WorkingHoursActivity
                Intent intent = new Intent(DashboardActivity.this, TimeTrackingActivity.class);
                startActivity(intent);
            }
        });

        // Find the ImageButton by its ID
        ImageButton logOutButton = findViewById(R.id.logOutB);

        // Set an OnClickListener for the ImageButton
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a confirmation dialog before exiting the app
                showExitConfirmationDialog();
            }
        });
    }

    // Function to show an exit confirmation dialog
    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Exit");
        builder.setMessage("Are you sure you want to exit the app?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform any necessary cleanup or logout actions here
                // Exit the app
                finishAffinity(); // Close all activities and exit the app
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog and continue with the app
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}