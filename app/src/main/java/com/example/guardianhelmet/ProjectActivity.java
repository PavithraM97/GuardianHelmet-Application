package com.example.guardianhelmet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class ProjectActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference projectsRef = db.collection("projects");
    private FirestoreRecyclerAdapter<ProjectModel, ProjectViewHolder> adapter;
    private RecyclerView recyclerView;
    private EditText projectNameEditText, phaseEditText, locationEditText, supervisorNameEditText, durationEditText;
    private Button createButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        // Initialize UI elements
        projectNameEditText = findViewById(R.id.etProjectName);
        phaseEditText = findViewById(R.id.etPhase);
        locationEditText = findViewById(R.id.etLocation);
        supervisorNameEditText = findViewById(R.id.etSupervisorName);
        durationEditText = findViewById(R.id.etDuration);
        createButton = findViewById(R.id.btnCreate);
        recyclerView = findViewById(R.id.recyclerView);


        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up FirestoreRecyclerOptions
        Query query = projectsRef.orderBy("projectName", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<ProjectModel> options = new FirestoreRecyclerOptions.Builder<ProjectModel>()
                .setQuery(query, ProjectModel.class)
                .build();

        // Create and set the adapter for the RecyclerView
        adapter = new FirestoreRecyclerAdapter<ProjectModel, ProjectViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProjectViewHolder holder, int position, @NonNull ProjectModel model) {
                holder.bind(model, new ProjectViewHolder.OnEditClickListener() {
                    @Override
                    public void onEditClick(int position) {

                        // Handle edit button click for the item at 'position'
                        // Implement your edit logic here
                        Toast.makeText(ProjectActivity.this, "Edit clicked for position " + position, Toast.LENGTH_SHORT).show();
                    }
                }, new ProjectViewHolder.OnDeleteClickListener() {
                    @Override
                    public void onDeleteClick(int position) {
                        // Handle delete button click for the item at 'position'
                        // Get the document snapshot for the item at 'position'
                        DocumentReference documentReference = adapter.getSnapshots().getSnapshot(position).getReference();

                        // Delete the document using the document reference
                        deleteProject(documentReference);
                    }
                });
            }

            @NonNull
            @Override
            public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
                return new ProjectViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);

        // Create button click listener
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProject();
            }
        });

        // Start listening to Firestore changes
        adapter.startListening();
    }

    private void createProject() {
        String projectName = projectNameEditText.getText().toString();
        String phase = phaseEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String supervisorName = supervisorNameEditText.getText().toString();
        int duration = Integer.parseInt(durationEditText.getText().toString());

        Map<String, Object> project = new HashMap<>();
        project.put("projectName", projectName);
        project.put("phase", phase);
        project.put("location", location);
        project.put("supervisorName", supervisorName);
        project.put("duration", duration);

        projectsRef.add(project)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ProjectActivity.this, "Project added successfully", Toast.LENGTH_SHORT).show();
                        clearFields();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProjectActivity.this, "Error adding project", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteProject(DocumentReference documentReference) {
        documentReference
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProjectActivity.this, "Project deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProjectActivity.this, "Error deleting project", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearFields() {
        projectNameEditText.setText("");
        phaseEditText.setText("");
        locationEditText.setText("");
        supervisorNameEditText.setText("");
        durationEditText.setText("");
    }
}