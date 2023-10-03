package com.example.guardianhelmet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class VideoGalleryActivity extends AppCompatActivity {
    private RecyclerView videoRecyclerView;
    private VideoAdapter videoAdapter;

    private FirebaseFirestore firestore;
    private CollectionReference videosRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_gallery);

        videoRecyclerView = findViewById(R.id.videoRecyclerView);
        videoAdapter = new VideoAdapter();

        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoRecyclerView.setAdapter(videoAdapter);

        firestore = FirebaseFirestore.getInstance();
        videosRef = firestore.collection("videos");

        // Load video data from Firestore
        loadVideos();
    }

    private void loadVideos() {
        videosRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<VideoItem> videoItems = new ArrayList<>();

            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                String videoTitle = documentSnapshot.getString("title");
                String videoUrl = documentSnapshot.getString("videoUrl");

                if (videoTitle != null && videoUrl != null) {
                    videoItems.add(new VideoItem(videoTitle, videoUrl));
                }
            }

            videoAdapter.setVideoItems(videoItems);
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to load videos.", Toast.LENGTH_SHORT).show();
        });
    }
}