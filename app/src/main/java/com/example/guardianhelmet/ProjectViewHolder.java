package com.example.guardianhelmet;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProjectViewHolder extends RecyclerView.ViewHolder {
    private TextView projectNameTextView;
    private TextView phaseTextView;
    private TextView locationTextView;
    private TextView supervisorNameTextView;
    private TextView durationTextView;
    private Button editButton;
    private Button deleteButton;

    public ProjectViewHolder(View itemView) {
        super(itemView);
        projectNameTextView = itemView.findViewById(R.id.projectNameTextView);
        phaseTextView = itemView.findViewById(R.id.phaseTextView);
        locationTextView = itemView.findViewById(R.id.locationTextView);
        supervisorNameTextView = itemView.findViewById(R.id.supervisorNameTextView);
        durationTextView = itemView.findViewById(R.id.durationTextView);
        editButton = itemView.findViewById(R.id.editButton);
        deleteButton = itemView.findViewById(R.id.deleteButton);

    }

    public void bind(ProjectModel project, OnEditClickListener editClickListener, OnDeleteClickListener deleteClickListener) {
        projectNameTextView.setText(project.getProjectName());
        phaseTextView.setText("Phase: " + project.getPhase());
        locationTextView.setText("Location: " + project.getLocation());
        supervisorNameTextView.setText("Supervisor: " + project.getSupervisorName());
        durationTextView.setText("Duration: " + project.getDuration() + " months");

        // Handle edit button click
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editClickListener != null) {
                    editClickListener.onEditClick(getAdapterPosition());
                }
            }
        });

        // Handle delete button click
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteClickListener != null) {
                    deleteClickListener.onDeleteClick(getAdapterPosition());
                }
            }
        });
    }

    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }
}