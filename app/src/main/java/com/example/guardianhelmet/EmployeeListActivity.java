package com.example.guardianhelmet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListActivity extends AppCompatActivity {
    private RecyclerView employeeRecyclerView;
    private EmployeeAdapter employeeAdapter;
    private List<Employee> employeeList = new ArrayList<>();

    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        employeeRecyclerView = findViewById(R.id.employeeRecyclerView);
        employeeAdapter = new EmployeeAdapter(employeeList);
        employeeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        employeeRecyclerView.setAdapter(employeeAdapter);

        db = FirebaseFirestore.getInstance();

        // Retrieve employee data from Firestore
        db.collection("workData")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Employee employee = document.toObject(Employee.class);
                            employeeList.add(employee);
                        }
                        employeeAdapter.notifyDataSetChanged();
                    } else {
                        // Handle the error
                    }
                });
    }
}