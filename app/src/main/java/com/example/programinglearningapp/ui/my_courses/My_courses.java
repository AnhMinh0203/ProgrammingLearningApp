package com.example.programinglearningapp.ui.my_courses;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.DatabaseHelper;
import com.example.programinglearningapp.db.MyCourses;
import com.example.programinglearningapp.model.Course;


import java.util.ArrayList;

public class My_courses extends AppCompatActivity {
    private LinearLayout courseRegistered;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_courses);
        dbHelper = new DatabaseHelper(this);
        courseRegistered = findViewById(R.id.courseRegistered);

//         Ghi thử dữ liệu vào bảng "courses"
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("user_id", 1);
//        values.put("course_id", 2);
//        long newRowId = db.insert("user_courses", null, values);
//        db.close();

        loadCourses();
    }

    private void loadCourses() {
        MyCourses my_Course = new MyCourses(this);
        ArrayList<Course> coursesList = my_Course.getUserCourses(1);

        // Duyệt qua danh sách khóa học và thêm thông tin vào giao diện
        for (Course course : coursesList) {
            // Tạo một LinearLayout mới cho từng khóa học
            LinearLayout courseLayout = new LinearLayout(this);
            courseLayout.setOrientation(LinearLayout.VERTICAL);

            // Tạo một TextView mới cho tên khóa học
            TextView courseName = new TextView(this);
            courseName.setText(course.getTitle());
            courseName.setTextSize(13);
            courseName.setMaxLines(3);
            courseName.setPadding(14, -8, 0, 26);
            courseName.setTextColor(Color.BLACK);

            // Tạo một ImageView mới cho hình ảnh khóa học
            ImageView courseImg = new ImageView(this);
            // Sử dụng phương thức load ảnh (ví dụ với Glide)
            // Glide.with(this).load(course.getImgUrl()).into(courseImg);

            // Thêm TextView và ImageView vào LinearLayout
            courseLayout.addView(courseImg);
            courseLayout.addView(courseName);

            // Thêm LinearLayout chứa khóa học vào `courseRegistered`
            courseRegistered.addView(courseLayout);
        }
    }
}