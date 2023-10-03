package com.example.guardianhelmet;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import android.os.Bundle;

public class VideoPlaybackActivity extends AppCompatActivity {
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_playback);

        videoView = findViewById(R.id.videoView);
        String videoUrl = getIntent().getStringExtra("videoUrl");

        if (videoUrl != null) {
            playVideo(videoUrl);
        }
    }

    private void playVideo(String videoUrl) {
        videoView.setVideoURI(Uri.parse(videoUrl));
        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();
        videoView.start();
    }
}