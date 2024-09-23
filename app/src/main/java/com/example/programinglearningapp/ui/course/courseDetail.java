package com.example.programinglearningapp.ui.course;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.model.Course;

public class courseDetail extends AppCompatActivity {
    private TextView courseTitle, courseDescription;
    private Course course;
    private LinearLayout createLessonElement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_detail);

        // Initialize views
        courseTitle = findViewById(R.id.courseTitle);
        courseDescription = findViewById(R.id.courseDescription);
        createLessonElement = findViewById(R.id.createLessonElement);
    }
}