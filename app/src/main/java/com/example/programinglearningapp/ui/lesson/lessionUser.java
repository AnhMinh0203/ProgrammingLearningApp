package com.example.programinglearningapp.ui.lesson;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.RegisterCourse;
import com.example.programinglearningapp.db.lesson.lessonHelper;
import com.example.programinglearningapp.ui.auth.Authentication;

public class lessionUser extends AppCompatActivity {

    private Integer userId; // Thêm biến để lưu id của người dùng
    public static Integer courseId;
    public lessonHelper lessonHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lession_user);
        lessonHelper = new lessonHelper(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Lấy chi tiết khóa học từ Intent
        courseId = getIntent().getIntExtra("courseId", -1);
        userId = Integer.parseInt(Authentication.id);
        String courseTitle = getIntent().getStringExtra("courseTitle");
        String courseDescription = getIntent().getStringExtra("courseDescription");

        // Cập nhật các phần tử UI với chi tiết khóa học
        TextView titleTextView = findViewById(R.id.courseTitle);
        TextView descriptionTextView = findViewById(R.id.courseDescription);

        titleTextView.setText(courseTitle);
        descriptionTextView.setText(courseDescription);

        Button buttonAddToMyCourses = findViewById(R.id.button_add_to_my_courses);
        buttonAddToMyCourses.setOnClickListener(v -> {
            registerCourse(userId, courseId);
        });

        // Áp dụng window insets cho ScrollView
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Đặt padding chỉ cho phần trên
            findViewById(R.id.scrollView).setPadding(30, 30, 30, 0);
            return insets;
        });
    }

    private void registerCourse(Integer userId, Integer courseId) {
        RegisterCourse registerCourse = new RegisterCourse(this);

        // Gọi hàm registerCourse để thêm khóa học
        boolean isRegistered = registerCourse.registerCourse(userId, courseId);

        // Thêm khóa học vào database
        if (isRegistered) {
            Toast.makeText(this, "Khóa học đã được thêm vào khóa học của tôi!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Khóa học đã được đăng ký trước đó!", Toast.LENGTH_SHORT).show();
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

        Cursor lessonsCursor = lessonHelper.getLessonsByCourseId(String.valueOf(courseId));
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
                    startActivityForResult(detailLesson, 1);
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