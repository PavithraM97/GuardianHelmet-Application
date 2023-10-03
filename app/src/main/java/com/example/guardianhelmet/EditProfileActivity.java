package com.example.guardianhelmet;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    EditText fullNameEditText;
    EditText emailEditText;
    EditText mobileEditText;
    Button saveChangesButton;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fullNameEditText = findViewById(R.id.editFullNameEditText);
        emailEditText = findViewById(R.id.editEmailEditText);
        mobileEditText = findViewById(R.id.editMobileEditText);
        saveChangesButton = findViewById(R.id.saveChangesButton);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            // Get the user's email address
            String userEmail = user.getEmail();

            // Check if the user's email is not null
            if (userEmail != null) {
                // Query Firestore to find the user document by email
                db.collection("userss")
                        .whereEqualTo("Email", userEmail)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    String fullName = document.getString("FullName");
                                    String email = document.getString("Email");
                                    String mobileNumber = document.getString("MobileNumber");

                                    // Set the retrieved data to the TextViews
                                    fullNameEditText.setText(fullName);
                                    emailEditText.setText(email);
                                    mobileEditText.setText(mobileNumber);
                                }
                            } else {
                                // Handle any errors that occur during the Firestore query
                                Toast.makeText(this, "Error fetching user data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            } else {
                // Handle the case when the user's email is null
                Toast.makeText(this, "User email is null", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle the case when the user is not authenticated
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
        saveChangesButton.setOnClickListener(view -> {
            // Get the edited values
            String editedFullName = fullNameEditText.getText().toString();
            String editedEmail = emailEditText.getText().toString(); // Get edited email
            String editedMobileNumber = mobileEditText.getText().toString();

            // Update the Firestore database with the edited values
            updateProfileData(editedFullName, editedEmail, editedMobileNumber);
        });
    }

    private void updateProfileData(String fullName, String email, String mobileNumber) {
        if (user != null) {
            // Update the user's profile data in Firestore
            db.collection("userss")
                    .document(user.getEmail())
                    .update("FullName", fullName, "Email", email, "MobileNumber", mobileNumber)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Close the editing activity after updating
                        } else {
                            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}