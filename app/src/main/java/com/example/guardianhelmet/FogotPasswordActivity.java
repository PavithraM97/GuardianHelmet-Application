package com.example.guardianhelmet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class FogotPasswordActivity extends AppCompatActivity {
    EditText resetEmail;
    Button resetButton;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogot_password);

        resetEmail = findViewById(R.id.reset_email);
        resetButton = findViewById(R.id.reset_button);
        auth = FirebaseAuth.getInstance();

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = resetEmail.getText().toString().trim();

                if (email.isEmpty()) {
                    resetEmail.setError("Email is required");
                    return;
                }

                // Send password reset email using Firebase Authentication
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(FogotPasswordActivity.this, "Password reset email sent to " + email, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(FogotPasswordActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // This flag clears the activity stack
                            startActivity(intent);
                        } else {
                            Toast.makeText(FogotPasswordActivity.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}