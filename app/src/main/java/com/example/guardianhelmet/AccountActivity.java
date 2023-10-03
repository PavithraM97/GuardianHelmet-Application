package com.example.guardianhelmet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccountActivity extends AppCompatActivity {
    private Button btnSignInAdmin;
    private Button btnSignInUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialize buttons
        btnSignInAdmin = findViewById(R.id.btnSignInAdmin);
        btnSignInUser = findViewById(R.id.btnSignInUser);

        // Set click listeners for the buttons
        btnSignInAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the AdminLoginActivity when "Sign In as Admin" button is clicked
                Intent intent = new Intent(AccountActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignInUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the LoginActivity when "Sign In as User" button is clicked
                Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}