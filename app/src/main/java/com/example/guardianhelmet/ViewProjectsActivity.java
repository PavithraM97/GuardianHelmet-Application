package com.example.guardianhelmet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewProjectsActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference projectsRef = db.collection("projects");
    private TableLayout tableLayout;
    private TextView textViewProjectName, textViewPhase, textViewLocation, textViewSupervisor, textViewDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_projects);

        // Initialize TextViews
        textViewProjectName = findViewById(R.id.textViewProjectName);
        textViewPhase = findViewById(R.id.textViewPhase);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewSupervisor = findViewById(R.id.textViewSupervisor);
        textViewDuration = findViewById(R.id.textViewDuration);

        // Retrieve project data (replace this with your data retrieval logic)
        retrieveProjectData();
    }

    private void retrieveProjectData() {
        // Assuming you have a method to retrieve project data from Firestore
        // Replace this with your actual data retrieval logic
        projectsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    // For simplicity, just take the first project (you can loop through all)
                    QueryDocumentSnapshot documentSnapshot = (QueryDocumentSnapshot) querySnapshot.getDocuments().get(0);

                    // Retrieve project details
                    String projectName = documentSnapshot.getString("projectName");
                    String phase = documentSnapshot.getString("phase");
                    String location = documentSnapshot.getString("location");
                    String supervisor = documentSnapshot.getString("supervisorName");
                    long duration = documentSnapshot.getLong("duration");

                    // Set the retrieved data to the TextViews
                    textViewProjectName.setText(projectName);
                    textViewPhase.setText(phase);
                    textViewLocation.setText(location);
                    textViewSupervisor.setText(supervisor);
                    textViewDuration.setText(String.valueOf(duration));
                } else {
                    // Handle case when there are no projects found
                }
            } else {
                // Handle error in data retrieval
            }
        });
    }
}