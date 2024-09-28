package com.example.programinglearningapp.ui.lesson;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.RegisterCourse;

public class lessionContentUser extends AppCompatActivity {

    private Integer userId; // Thêm biến để lưu id của người dùng
    private Integer courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lession_content_user);

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }
//
//        // Lấy chi tiết khóa học từ Intent
//        courseId = getIntent().getIntExtra("courseId", -1);
//        userId = getIntent().getIntExtra("user_id", -1);
//        String courseTitle = getIntent().getStringExtra("courseTitle");
//        String courseDescription = getIntent().getStringExtra("courseDescription");
//
//        Log.d("LessionContentUser", "courseId: " + courseId);
//        Log.d("LessionContentUser", "userId1234: " + userId);
//        Log.d("LessionContentUser", "courseDescription: " + courseDescription);
//
//
//        if (courseId == -1) {
//            Log.e("LessionContentUser", "Không có ID khóa học được truyền");
//        }
//
//        if (userId == -1) {
//            Log.e("LessionContentUser", "Không có ID người dùng được truyền");
//        }
//
//        // Cập nhật các phần tử UI với chi tiết khóa học
//        TextView titleTextView = findViewById(R.id.courseTitle);
//        TextView descriptionTextView = findViewById(R.id.courseDescription);
//
//        titleTextView.setText(courseTitle);
//        descriptionTextView.setText(courseDescription);
//
//        Button buttonAddToMyCourses = findViewById(R.id.button_add_to_my_courses);
//        buttonAddToMyCourses.setOnClickListener(v -> {
//            registerCourse(userId, courseId);
//        });
//
//        // Áp dụng window insets cho ScrollView
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            // Đặt padding chỉ cho phần trên
//            findViewById(R.id.scrollView).setPadding(30, 30, 30, 0);
//            return insets;
//        });
//    }
//
//    private void registerCourse(Integer userId, Integer courseId) {
//        RegisterCourse registerCourse = new RegisterCourse(this);
//
//        // Gọi hàm registerCourse để thêm khóa học
//        boolean isRegistered = registerCourse.registerCourse(userId, courseId);
//
//        // Thêm khóa học vào database
//        if (isRegistered) {
//            Toast.makeText(this, "Khóa học đã được thêm vào khóa học của tôi!", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Khóa học đã được đăng ký trước đó!", Toast.LENGTH_SHORT).show();
//        }
    }

}