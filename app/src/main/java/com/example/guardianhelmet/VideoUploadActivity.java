package com.example.guardianhelmet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class VideoUploadActivity extends AppCompatActivity {
    private static final int REQUEST_VIDEO_UPLOAD = 1;

    private EditText titleEditText;
    private Button selectVideoButton;
    private Button uploadButton;
    private VideoView videoView;

    private Uri videoUri;

    private FirebaseFirestore firestore;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_upload);

        titleEditText = findViewById(R.id.titleEditText);
        selectVideoButton = findViewById(R.id.selectVideoButton);
        uploadButton = findViewById(R.id.uploadButton);
        videoView = findViewById(R.id.videoView);

        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference().child("videos");

        selectVideoButton.setOnClickListener(view -> openVideoPicker());
        uploadButton.setOnClickListener(view -> uploadVideo());
    }

    private void openVideoPicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(intent, REQUEST_VIDEO_UPLOAD);
    }

    private void uploadVideo() {
        if (videoUri != null) {
            String videoTitle = titleEditText.getText().toString().trim();

            StorageReference videoRef = storageReference.child(videoTitle + ".mp4");

            UploadTask uploadTask = videoRef.putFile(videoUri);

            uploadTask.addOnSuccessListener(taskSnapshot -> {
                videoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String downloadUrl = uri.toString();

                    VideoItem video = new VideoItem(videoTitle, downloadUrl);

                    CollectionReference videosRef = firestore.collection("videos");
                    videosRef.add(video)
                            .addOnSuccessListener(documentReference -> {
                                Toast.makeText(this, "Video uploaded successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(this, "Failed to store video information.", Toast.LENGTH_SHORT).show();
                            });
                });
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Failed to upload video: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "Please select a video to upload.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VIDEO_UPLOAD && resultCode == RESULT_OK && data != null) {
            videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.start(); // Start playing the video
        }
    }
}