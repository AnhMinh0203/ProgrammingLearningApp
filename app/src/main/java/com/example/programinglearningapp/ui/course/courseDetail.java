package com.example.programinglearningapp.ui.course;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.lesson.lessonHelper;
import com.example.programinglearningapp.model.Course;
import com.example.programinglearningapp.ui.lesson.lessonDetail;
import com.example.programinglearningapp.ui.lessonManagement.LessonManagement_Create;

public class courseDetail extends AppCompatActivity {
    private TextView courseTitle, courseDescription;
    private Course course;
    private LinearLayout createLessonElement;
    public static String courseId;
    public lessonHelper lessonHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        lessonHelper = new lessonHelper(this);

        // Initialize views
        courseTitle = findViewById(R.id.courseTitle);
        courseDescription = findViewById(R.id.courseDescription);
        createLessonElement = findViewById(R.id.createLessonElement);
        LinearLayout lessonContainer = findViewById(R.id.lessonContainerView); // LinearLayout chứa các view bài học

        Intent intent = getIntent();
        if (intent != null) {
            courseId = intent.getStringExtra("id");
            String title = intent.getStringExtra("courseTitle");
            String description = intent.getStringExtra("courseDescription");
//            String imageUrl = intent.getStringExtra("courseImage");
            // Update the UI with the passed data
            courseTitle.setText(title);
            courseDescription.setText(description);

            Cursor lessonsCursor = lessonHelper.getLessonsByCourseId(courseId);
            if (lessonsCursor != null && lessonsCursor.moveToFirst()) {
                int lessonCount = lessonsCursor.getCount(); // Get the number of lessons
                Log.d("CourseDetail", "Number of lessons: " + lessonCount);

                do {
                    // Create a new LinearLayout for each lesson
                    LinearLayout lessonLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_lesson, lessonContainer, false);

                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lessonLayout.getLayoutParams();
                    params.setMargins(0, 5, 0, 5); // Left, Top, Right, Bottom
                    lessonLayout.setLayoutParams(params);

                    // Retrieve lesson title from cursor
                    int titleIndex = lessonsCursor.getColumnIndex("title");
                    String lessonTitle = lessonsCursor.getString(titleIndex);

                    int idIndex = lessonsCursor.getColumnIndex("id"); // Assuming you have an 'id' column
                    int lessonId = lessonsCursor.getInt(idIndex); // Get the lesson ID

                    // Find and set the title for the lesson
                    TextView lessonTitleView = lessonLayout.findViewById(R.id.lessonTitle);
                    lessonTitleView.setText(lessonTitle);

                    lessonLayout.setOnClickListener(v -> {
                        Intent detailLesson = new Intent(this, lessonDetail.class);
                        detailLesson.putExtra("LESSON_ID", lessonId); // Pass the lesson ID
                        detailLesson.putExtra("COURSE_ID", courseId); // Pass the course ID if you have it
                        startActivity(detailLesson);
                    });

                    // Add the dynamically created lesson layout to the parent container
                    lessonContainer.addView(lessonLayout);
                } while (lessonsCursor.moveToNext());
                lessonsCursor.close();
            } else {
                Log.d("CourseDetail", "No lessons found");
            }

        }


        createLessonElement.setOnClickListener(v -> {
            // Open a new activity or view for creating a lesson
            Intent createLessonIntent = new Intent(courseDetail.this, LessonManagement_Create.class);
            startActivity(createLessonIntent);
        });
    }

}