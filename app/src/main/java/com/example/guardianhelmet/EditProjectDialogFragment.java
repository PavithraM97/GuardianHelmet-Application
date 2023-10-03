package com.example.guardianhelmet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class EditProjectDialogFragment extends DialogFragment {
    private ProjectModel project;
    private EditText projectNameEditText;
    // Other EditText fields for editing project details

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_project, null);

        projectNameEditText = view.findViewById(R.id.editProjectName);
        // Initialize other EditText fields

        builder.setView(view)
                .setTitle("Edit Project")
                .setPositiveButton("Save", (dialog, which) -> {
                    // Handle the save button click and update the project details
                    String updatedProjectName = projectNameEditText.getText().toString();
                    // Get values from other EditText fields
                    // Update the project details in Firestore or wherever you store the data
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Handle the cancel button click
                    dismiss(); // Close the dialog
                });

        return builder.create();
    }
}