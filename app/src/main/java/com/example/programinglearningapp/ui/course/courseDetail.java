package com.example.programinglearningapp.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.model.Course;
import com.example.programinglearningapp.ui.lesson.LessonManagement_Create;

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
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("courseTitle");
            String description = intent.getStringExtra("courseDescription");
            String imageUrl = intent.getStringExtra("courseImage");

            // Update the UI with the passed data
            courseTitle.setText(title);
            courseDescription.setText(description);

            // If you want to display the image, you can use an ImageView and a library like Glide or Picasso to load the image from the URL.
            // Example:
            // Glide.with(this).load(imageUrl).into(courseImageView); // Assuming you have an ImageView for the course image
        }


        createLessonElement.setOnClickListener(v -> {
            // Open a new activity or view for creating a lesson
            int a  = 1;
            Intent createLessonIntent = new Intent(courseDetail.this, LessonManagement_Create.class);
            startActivity(createLessonIntent);
        });
    }
}