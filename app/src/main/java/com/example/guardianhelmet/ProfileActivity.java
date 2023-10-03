package com.example.guardianhelmet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfileActivity extends AppCompatActivity {
    TextView userFullNameTextView;
    TextView userEmailTextView;
    TextView userMobileTextView;
    ImageView userProfileImage;
    Button uploadImageButton;

    Button editProfileButton;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    private static final int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userFullNameTextView = findViewById(R.id.userFullNameTextView);
        userEmailTextView = findViewById(R.id.userEmailTextView);
        userMobileTextView = findViewById(R.id.userMobileTextView);
        userProfileImage = findViewById(R.id.userProfileImage);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        editProfileButton = findViewById(R.id.editProfileButton);


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
                                    userFullNameTextView.setText("Full Name: " + fullName);
                                    userEmailTextView.setText("Email: " + email);
                                    userMobileTextView.setText("Mobile Number: " + mobileNumber);
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

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the image picker when the button is clicked
                openImagePicker();
            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the EditProfileActivity when the "Edit Profile" button is clicked
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }




    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image URI
            Uri imageUri = data.getData();

            // Upload the selected image to Firebase Storage
            uploadImageToStorage(imageUri);

            // Display the selected image in the ImageView
            userProfileImage.setImageURI(imageUri);
        }
    }

    private void uploadImageToStorage(Uri imageUri) {
        if (user != null) {
            // Create a storage reference
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child("profile_images")
                    .child(user.getUid());

            // Upload the image
            storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get the download URL of the uploaded image
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Update the user's profile image URL in Firestore
                                    updateUserProfileImage(uri.toString());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                                    builder.setTitle("sucess");
                                    builder.setMessage("Sucessfully upload the image");
                                    builder.setPositiveButton("OK", null);
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void updateUserProfileImage(String imageUrl) {
        if (user != null) {
            // Update the user's profile image URL in Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("profile").document(user.getEmail())
                    .update("ProfileImageUrl", imageUrl)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ProfileActivity.this, "Profile image updated successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ProfileActivity.this, "Failed to update profile image", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



        }

    }
}