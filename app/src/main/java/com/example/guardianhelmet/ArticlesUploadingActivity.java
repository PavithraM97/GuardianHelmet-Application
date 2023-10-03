package com.example.guardianhelmet;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class ArticlesUploadingActivity extends AppCompatActivity {
    private static final int PICK_DOCUMENT_REQUEST = 1;

    private CardView titleCardView;
    private CardView contentCardView;
    private Button buttonUpload;
    private Button buttonCancel;
    private Button buttonView;
    private Button buttonSelectDocument; // Button to select a document
    private EditText editTextTitle;
    private EditText editTextContent;
    private WebView webView; // WebView to display the document

    private FirebaseFirestore firestore;
    private FirebaseStorage storage;
    private Uri documentUri; // Store the selected document's URI

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_uploading);

        titleCardView = findViewById(R.id.titleCardView);
        contentCardView = findViewById(R.id.contentCardView);
        buttonUpload = findViewById(R.id.buttonUpload);
        buttonCancel = findViewById(R.id.buttonCancel);
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextContent = findViewById(R.id.editTextContent);

        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        titleCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click on the title card
            }
        });

        contentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click on the content card
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check for permission to read external storage
                if (ContextCompat.checkSelfPermission(ArticlesUploadingActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // Permission is already granted, open file picker dialog
                    openFilePicker();
                } else {
                    // Permission is not granted, request it
                    ActivityCompat.requestPermissions(ArticlesUploadingActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PICK_DOCUMENT_REQUEST);
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle cancel button logic here, e.g., navigate back to the previous screen
                finish();
            }
        });
    }

    // Handle permission requests and open the file picker dialog
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PICK_DOCUMENT_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open file picker dialog
                openFilePicker();
            } else {
                // Permission denied, show a message or handle it accordingly
                Toast.makeText(this, "Permission denied. Cannot access external storage.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Open the file picker dialog
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*"); // Set the MIME type to specify the type of documents you want to allow (e.g., application/msword for Word documents)
        startActivityForResult(intent, PICK_DOCUMENT_REQUEST);
    }

    // Handle the selected document
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_DOCUMENT_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            documentUri = data.getData();
            // You can display the selected document's name or other information if needed
            // Now, you can proceed to upload the document to Firebase Firestore Storage
            uploadDocument();
        }
    }

    // Upload the selected document to Firebase Firestore Storage
    private void uploadDocument() {
        if (documentUri != null) {
            // Generate a unique filename for the document
            String documentName = "document_" + System.currentTimeMillis() + ".docx";

            // Create a reference to the Firebase Firestore Storage location
            StorageReference storageReference = storage.getReference().child("safetyguidelines").child(documentName);

            // Upload the document to Firebase Firestore Storage
            UploadTask uploadTask = storageReference.putFile(documentUri);

            uploadTask.addOnSuccessListener(taskSnapshot -> {
                // The document was successfully uploaded
                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String downloadUrl = uri.toString();

                    // Retrieve article title and content from EditText views
                    String articleTitle = editTextTitle.getText().toString();
                    String articleContent = editTextContent.getText().toString();

                    // Create a map to store article data
                    Map<String, Object> articleData = new HashMap<>();
                    articleData.put("title", articleTitle);
                    articleData.put("content", articleContent);
                    articleData.put("documentUrl", downloadUrl);

                    // Store article data in Firestore
                    firestore.collection("articles")
                            .add(articleData)
                            .addOnSuccessListener(documentReference -> {
                                // Article data stored in Firestore successfully
                                Toast.makeText(ArticlesUploadingActivity.this, "Article uploaded successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                // Handle Firestore storage failure
                                Toast.makeText(ArticlesUploadingActivity.this, "Failed to upload article.", Toast.LENGTH_SHORT).show();
                            });
                });
            }).addOnFailureListener(e -> {
                // Handle Firebase Storage failure
                Toast.makeText(ArticlesUploadingActivity.this, "Failed to upload document.", Toast.LENGTH_SHORT).show();
            });
        }
    }
}