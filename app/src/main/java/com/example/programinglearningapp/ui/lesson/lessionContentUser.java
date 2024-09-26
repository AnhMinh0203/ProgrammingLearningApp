package com.example.programinglearningapp.ui.lesson;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.programinglearningapp.R;

public class lessionContentUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lession_content_user);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Get course details from the Intent
        String courseTitle = getIntent().getStringExtra("courseTitle");
        String courseDescription = getIntent().getStringExtra("courseDescription");

        // Update UI elements with course details
        TextView titleTextView = findViewById(R.id.courseTitle);
        TextView descriptionTextView = findViewById(R.id.courseDescription);

        titleTextView.setText(courseTitle);
        descriptionTextView.setText(courseDescription);

        // Apply window insets to ScrollView
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Set padding only for the top
            findViewById(R.id.scrollView).setPadding(30, 30, 30, 00);
            return insets;
        });
    }

}