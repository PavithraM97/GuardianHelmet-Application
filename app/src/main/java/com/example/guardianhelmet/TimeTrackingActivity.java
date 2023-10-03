package com.example.guardianhelmet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TimeTrackingActivity extends AppCompatActivity {
    private EditText employeeNameEditText;
    private Button startStopButton, breakButton, endBreakButton;
    private TextView workingTimeTextView;

    private boolean isWorking = false;
    private boolean isOnBreak = false;
    private long startTime = 0;
    private long breakStartTime = 0;
    private long totalWorkTime = 0;
    private long totalBreakTime = 0;

    private FirebaseFirestore db;

    private Handler handler = new Handler();
    private Runnable updateTimerTask = new Runnable() {
        public void run() {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - startTime;
            updateTimerText(elapsedTime);
            handler.postDelayed(this, 1000); // Update every second
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_tracking);
        employeeNameEditText = findViewById(R.id.employeeNameEditText);
        startStopButton = findViewById(R.id.startStopButton);
        breakButton = findViewById(R.id.breakButton);
        endBreakButton = findViewById(R.id.endBreakButton);
        workingTimeTextView = findViewById(R.id.workingTimeTextView);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        db = FirebaseFirestore.getInstance();

        if (user == null) {
            // Handle the case where the user is not authenticated
            // You might want to add a login screen or authentication flow.
        }
    }

    public void toggleTimer(View view) {
        if (isWorking) {
            stopWorking();
        } else {
            startWorking();
        }
    }

    public void startBreak(View view) {
        if (isWorking && !isOnBreak) {
            startBreak();
        }
    }

    public void endBreak(View view) {
        if (isWorking && isOnBreak) {
            endBreak();
        }
    }

    private void startWorking() {
        isWorking = true;
        startTime = System.currentTimeMillis();
        startStopButton.setText("Stop");
        handler.post(updateTimerTask);
    }

    private void stopWorking() {
        isWorking = false;
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        totalWorkTime += elapsedTime;
        updateTimerText(totalWorkTime);
        handler.removeCallbacks(updateTimerTask);
        startStopButton.setText("Start");
        saveWorkData();
    }

    private void startBreak() {
        isOnBreak = true;
        breakStartTime = System.currentTimeMillis();
        breakButton.setEnabled(false);
        endBreakButton.setEnabled(true);
    }

    private void endBreak() {
        isOnBreak = false;
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - breakStartTime;
        totalBreakTime += elapsedTime;
        breakButton.setEnabled(true);
        endBreakButton.setEnabled(false);
        saveWorkData();
    }

    private void updateTimerText(long elapsedTime) {
        int seconds = (int) (elapsedTime / 1000) % 60;
        int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
        int hours = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
        String timeString = String.format("Working Time: %02d:%02d:%02d", hours, minutes, seconds);
        workingTimeTextView.setText(timeString);
    }

    private void saveWorkData() {
        String employeeName = employeeNameEditText.getText().toString().trim();
        if (!employeeName.isEmpty()) {
            Map<String, Object> data = new HashMap<>();
            data.put("employeeName", employeeName);
            data.put("totalWorkTime", totalWorkTime);
            data.put("totalBreakTime", totalBreakTime);

            // Use Firestore to save data
            db.collection("workData")
                    .add(data)
                    .addOnSuccessListener(documentReference -> {
                        // Data added successfully
                    })
                    .addOnFailureListener(e -> {
                        // Handle the error
                    });
        }
    }
}