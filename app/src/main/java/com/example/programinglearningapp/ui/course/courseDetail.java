package com.example.programinglearningapp.ui.course;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.Course.CourseHelper;
import com.example.programinglearningapp.db.lesson.lessonHelper;
import com.example.programinglearningapp.model.Course;
import com.example.programinglearningapp.ui.auth.Authentication;
import com.example.programinglearningapp.ui.courseManagement.CourseManager_Update;
import com.example.programinglearningapp.ui.lesson.lessonDetail;
import com.example.programinglearningapp.ui.lessonManagement.LessonManagement_Create;
import com.example.programinglearningapp.ui.lessonManagement.LessonManagement_Update;

public class courseDetail extends AppCompatActivity {
    private TextView courseTitle, courseDescription;
    private Course course;
    private LinearLayout createLessonElement;
    public static String courseId, title,description,imageUrl ;
    public lessonHelper lessonHelper;
    public CourseHelper courseHelper;
    Button buttonUpdateCourse, buttonDeleteCourse, buttonRegisterCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        lessonHelper = new lessonHelper(this);
        courseHelper = new CourseHelper(this);

        buttonUpdateCourse = findViewById(R.id.buttonUpdateCourse);
        buttonDeleteCourse = findViewById(R.id.buttonDeleteCourse);
        buttonRegisterCourse = findViewById(R.id.button_signUp_course);
        // Initialize views
        courseTitle = findViewById(R.id.courseTitle);
        courseDescription = findViewById(R.id.courseDescription);
        createLessonElement = findViewById(R.id.createLessonElement);
        LinearLayout lessonContainer = findViewById(R.id.lessonContainerView); // LinearLayout chứa các view bài học

        Intent intent = getIntent();
        if (intent != null) {
            courseId = intent.getStringExtra("id");
            title = intent.getStringExtra("courseTitle");
            description = intent.getStringExtra("courseDescription");
            imageUrl = intent.getStringExtra("courseImage");
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
                        startActivityForResult(detailLesson,1);
                    });

                    // Add the dynamically created lesson layout to the parent container
                    lessonContainer.addView(lessonLayout);
                } while (lessonsCursor.moveToNext());
                lessonsCursor.close();
            } else {
                Log.d("CourseDetail", "No lessons found");
            }

        }

        String userRole = Authentication.role;

        if(userRole.equals("0")) {
            buttonUpdateCourse.setVisibility(View.GONE);
            buttonDeleteCourse.setVisibility(View.GONE);
            buttonRegisterCourse.setVisibility(View.GONE);
            createLessonElement.setVisibility(View.GONE);
        }

        if(userRole.equals("1")) {
            createLessonElement.setOnClickListener(v -> {
                // Open a new activity or view for creating a lesson
                Intent createLessonIntent = new Intent(courseDetail.this, LessonManagement_Create.class);
                startActivity(createLessonIntent);
            });
            buttonUpdateCourse.setOnClickListener(v->{
                Intent updateCourseIntent = new Intent (courseDetail.this, CourseManager_Update.class);
                updateCourseIntent.putExtra("idCourse",courseId);
                updateCourseIntent.putExtra("titleCourse",title);
                updateCourseIntent.putExtra("desCourse",description);
                updateCourseIntent.putExtra("imageUrl", imageUrl);
                startActivity(updateCourseIntent);
            });
            buttonDeleteCourse.setOnClickListener(v -> {
                // Create an alert dialog to confirm the deletion
                new AlertDialog.Builder(this)
                        .setTitle("Delete Course")
                        .setMessage("Are you sure you want to delete this course? This action cannot be undone.")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            // User confirmed to delete the course
                            boolean isDeleted = courseHelper.deleteCourse(courseId); // Call the delete method
                            if (isDeleted) {
                                Toast.makeText(this, "Course deleted successfully", Toast.LENGTH_SHORT).show();

                                // Set result to indicate that a course was deleted
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("courseDeleted", true); // Pass a flag to indicate the course was deleted
                                setResult(RESULT_OK, resultIntent); // Set the result for the calling activity

                                finish(); // Close the activity and go back to the previous one
                            } else {
                                Toast.makeText(this, "Error deleting course", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, (dialog, which) -> {
                            // User canceled the dialog, do nothing
                            dialog.dismiss();
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            });
        }

    }

    private void deleteCourse(String courseId) {
        // Call a method in your lessonHelper to delete the course
        if (courseHelper.deleteCourse(courseId)) {
            Toast.makeText(this, "Course deleted successfully", Toast.LENGTH_SHORT).show();
            finish(); // Optionally finish this activity
        } else {
            Toast.makeText(this, "Failed to delete course", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            boolean isLessonDeleted = data.getBooleanExtra("IS_LESSON_DELETED", false);
            if (isLessonDeleted) {
                // Làm mới danh sách các bài học
                refreshLessonList();
            }
        }
    }
    private void refreshLessonList() {
        LinearLayout lessonContainer = findViewById(R.id.lessonContainerView);
        lessonContainer.removeAllViews(); // Xóa các view hiện tại

        Cursor lessonsCursor = lessonHelper.getLessonsByCourseId(courseId);
        if (lessonsCursor != null && lessonsCursor.moveToFirst()) {
            do {
                // Tạo một LinearLayout mới cho mỗi bài học
                LinearLayout lessonLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_lesson, lessonContainer, false);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lessonLayout.getLayoutParams();
                params.setMargins(0, 5, 0, 5); // Trái, trên, phải, dưới
                lessonLayout.setLayoutParams(params);

                // Lấy tiêu đề bài học từ con trỏ
                int titleIndex = lessonsCursor.getColumnIndex("title");
                String lessonTitle = lessonsCursor.getString(titleIndex);

                int idIndex = lessonsCursor.getColumnIndex("id");
                int lessonId = lessonsCursor.getInt(idIndex);

                // Gán tiêu đề cho bài học
                TextView lessonTitleView = lessonLayout.findViewById(R.id.lessonTitle);
                lessonTitleView.setText(lessonTitle);

                // Xử lý khi click vào bài học
                lessonLayout.setOnClickListener(v -> {
                    Intent detailLesson = new Intent(this, lessonDetail.class);
                    detailLesson.putExtra("LESSON_ID", lessonId);
                    detailLesson.putExtra("COURSE_ID", courseId);
                    startActivityForResult(detailLesson, 1); // Mở lessonDetail và lắng nghe kết quả
                });

                // Thêm layout bài học vào container
                lessonContainer.addView(lessonLayout);
            } while (lessonsCursor.moveToNext());
            lessonsCursor.close();
        } else {
            Log.d("CourseDetail", "No lessons found");
        }
    }



}