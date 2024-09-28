package com.example.programinglearningapp.ui.my_courses;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.Course.CourseAdapter;
import com.example.programinglearningapp.db.Course.CourseHelper;
import com.example.programinglearningapp.db.DatabaseHelper;
import com.example.programinglearningapp.db.MyCourses;
import com.example.programinglearningapp.model.Course;
import com.example.programinglearningapp.ui.auth.Authentication;


import java.util.ArrayList;
import java.util.List;

public class My_courses extends AppCompatActivity {
    private LinearLayout courseRegistered;
    private DatabaseHelper dbHelper;
    private CourseHelper courseHelper;
    private List<Course> courseList;
    private CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_my_courses);

        dbHelper = new DatabaseHelper(this);
        courseHelper = new CourseHelper(this);  // Khởi tạo CourseHelper

        courseRegistered = findViewById(R.id.courseRegistered);

        loadCourses();

//         Ghi thử dữ liệu vào bảng "courses"
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("user_id", 1);
//        values.put("course_id", 3);
//        long newRowId = db.insert("user_courses", null, values);
//        db.close();

//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String whereClause = "user_id = ? AND course_id = ?";
//        String[] whereArgs = new String[] { "-1", "2" };
//        int rowsDeleted = db.delete("user_courses", whereClause, whereArgs);
//        db.close();
    }

    private void loadCourses() {
        courseList = courseHelper.getCoursesRegisterForUser(Integer.parseInt(Authentication.id));

        if (courseList != null) {
            courseList.clear();
            courseList.addAll(courseList);
            courseAdapter.notifyDataSetChanged();
        } else {
            Log.e("MyCourses", "Failed to load courses: newCourseList is null");
        }
    }

}