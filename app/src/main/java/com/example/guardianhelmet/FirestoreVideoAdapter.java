package com.example.guardianhelmet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.example.guardianhelmet.R; // Replace with your package name
import com.example.guardianhelmet.FirestoreVideoModel; // Replace with your package name

public class FirestoreVideoAdapter extends FirestoreRecyclerAdapter<FirestoreVideoModel, FirestoreVideoAdapter.VideoViewHolder> {

    public FirestoreVideoAdapter(@NonNull FirestoreRecyclerOptions<FirestoreVideoModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull VideoViewHolder holder, int position, @NonNull FirestoreVideoModel model) {
        // Bind data to the ViewHolder
        holder.bind(model);
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item_layout, parent, false);
        return new VideoViewHolder(view);
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnailImageView;
        private TextView titleTextView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
        }

        public void bind(FirestoreVideoModel model) {
            // Set the title
            titleTextView.setText(model.getTitle());

            // Load and display the video thumbnail using Glide (you need to add Glide to your dependencies)
            Glide.with(itemView.getContext())
                    .load(model.getUrl()) // Assuming model.getUrl() contains the thumbnail URL
                    .into(thumbnailImageView);
        }
    }
}
