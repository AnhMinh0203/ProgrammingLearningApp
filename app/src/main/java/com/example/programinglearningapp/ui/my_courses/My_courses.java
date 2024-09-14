package com.example.programinglearningapp.ui.my_courses;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
//        values.put("course_id", 3);
//        long newRowId = db.insert("user_courses", null, values);
//        db.close();

//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String whereClause = "user_id = ? AND course_id = ?";
//        String[] whereArgs = new String[] { "1", "3" };
//        int rowsDeleted = db.delete("user_courses", whereClause, whereArgs);
//        db.close();

        loadCourses();
    }

    private void loadCourses() {
        MyCourses my_Course = new MyCourses(this);
        ArrayList<Course> coursesList = my_Course.getUserCourses(1);
        LinearLayout courseRegistered = findViewById(R.id.courseRegistered);

        // Duyệt qua danh sách khóa học và thêm thông tin vào giao diện
        for (int i = 0; i < coursesList.size(); i++) {
            Course course = coursesList.get(i);

            // Tạo một LinearLayout mới cho từng khóa học
            LinearLayout courseLayout = new LinearLayout(this);
            courseLayout.setOrientation(LinearLayout.VERTICAL);

            // Sử dụng LayoutParams và setMargins để tạo khoảng cách giữa các layout
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(180, 0, 0, 50); // Margin 50px giữa các layout

//            if (i % 2 == 0) { // Vị trí chẵn
//                layoutParams.setMargins(0, 0, 0, 50); // Margin 50px giữa các layout
//            } else { // Vị trí lẻ
//                layoutParams.setMargins(350, 0, 0, 50); // Margin 50px giữa các layout và dịch sang phải
//            }

            courseLayout.setLayoutParams(layoutParams);

            courseLayout.setPadding(0, 0, 0, 20); // Điều chỉnh padding nếu cần
            courseLayout.setBackgroundResource(R.drawable.border_radius_courses);

            // Tạo một ImageView mới cho hình ảnh khóa học
            ImageView courseImg = new ImageView(this);
            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(
                    268, // width
                    200  // height
            );
            imgParams.setMargins(0, 0, 0, 20); // Điều chỉnh margins nếu cần
            courseImg.setLayoutParams(imgParams);
            courseImg.setImageResource(R.drawable.html_css); // Hình ảnh mặc định

            // Tạo một TextView mới cho tên khóa học
            TextView courseName = new TextView(this);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, // width
                    LinearLayout.LayoutParams.WRAP_CONTENT // height
            );
            textParams.setMargins(0, 20, 0, 20); // Điều chỉnh margins nếu cần
            courseName.setLayoutParams(textParams);
            courseName.setText(course.getTitle());
            courseName.setTextSize(13);
            courseName.setMaxLines(3);
            courseName.setPadding(0, 0, 0, 0);
            courseName.setTextColor(Color.BLACK);
            courseName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            // Thêm ImageView và TextView vào LinearLayout khóa học
            courseLayout.addView(courseImg);
            courseLayout.addView(courseName);

            // Thêm LinearLayout chứa khóa học vào courseRegistered (LinearLayout chính)
            courseRegistered.addView(courseLayout);
        }
    }

}