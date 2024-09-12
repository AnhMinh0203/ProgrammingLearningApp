package com.example.programinglearningapp.ui.register_course;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.DatabaseHelper;
import com.example.programinglearningapp.db.RegisterCourse;

public class Register_courses extends AppCompatActivity {

    private LinearLayout lessonContainer;
    private LinearLayout lessonContent;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_courses);
        dbHelper = new DatabaseHelper(this);
        lessonContainer = findViewById(R.id.lessonContainer);
        lessonContent = findViewById(R.id.lessonContent);
//         Ghi thử dữ liệu vào bảng "courses"
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("course_name", "NodeJS & ExpressJS");
//        values.put("description", "Học Back-end với Node & ExpressJS framework, hiểu các khái niệm khi làm Back-end và xây dựng RESTful API cho trang web.");
//        values.put("img", "nodejs");
//        long newRowId = db.insert("courses", null, values);
//        db.close();

//         Ghi thử dữ liệu vào bảng "lessons"
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("course_id", 2);
//        values.put("title", "Lời khuyên trước khóa học");
//        values.put("description", "test");
//        long newRowId = db.insert("lessons", null, values);
//        db.close();

        loadCourses();
    }

    private void loadCourses() {
        RegisterCourse registerCourse = new RegisterCourse(this);

        // Giả sử bạn muốn lấy các khóa học theo ID
        Cursor cursor = registerCourse.getCourseById(2);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String courseName = cursor.getString(cursor.getColumnIndexOrThrow("course_name"));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));

                    LinearLayout courseLayout = new LinearLayout(this);
                    courseLayout.setOrientation(LinearLayout.VERTICAL);
                    courseLayout.setPadding(16, 16, 16, 16);

                    // Tạo và cấu hình TextView cho tên khóa học
                    TextView textHeader = new TextView(this);
                    textHeader.setText(courseName);
                    textHeader.setTextSize(20);
                    textHeader.setTextColor(getResources().getColor(android.R.color.black));
                    textHeader.setPadding(0, 10, 0, 10);
                    textHeader.setTextSize(20);
                    textHeader.setTypeface(null, Typeface.BOLD);

                    // Tạo và cấu hình TextView cho mô tả khóa học
                    TextView textContentHeader = new TextView(this);
                    textContentHeader.setText(description);
                    textContentHeader.setTextSize(16);
                    textContentHeader.setTextColor(getResources().getColor(android.R.color.black));
                    textContentHeader.setPadding(0, 10, 0, 10);

                    // Thêm các TextView vào LinearLayout
                    courseLayout.addView(textHeader);
                    courseLayout.addView(textContentHeader);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0, -650, 0, 0); // Thay đổi giá trị này để điều chỉnh margin
                    courseLayout.setLayoutParams(params);

                    // Thêm LinearLayout vào lessonContainer
                    lessonContainer.addView(courseLayout);

                } while (cursor.moveToNext());

                // Đóng cursor sau khi xử lý xong
                cursor.close();
            }
        } else {
            Log.e("Register_courses", "Cursor is null");
        }

        Cursor cursor1 = registerCourse.getLessonByCourseId(2);
        if (cursor1 != null) {
            if (cursor1.moveToFirst()) {
                do {
//                    String courseName = cursor1.getString(cursor1.getColumnIndexOrThrow("course_name"));
                    String title = cursor1.getString(cursor1.getColumnIndexOrThrow("title"));

                    LinearLayout courseLayout = new LinearLayout(this);
                    courseLayout.setOrientation(LinearLayout.VERTICAL);
                    courseLayout.setPadding(16, 16, 16, 16);

                    // Tạo và cấu hình TextView cho mô tả khóa học
                    TextView textLesson  = new TextView(this);
                    textLesson.setText(title);
                    textLesson.setTextSize(16);
                    textLesson.setTextColor(getResources().getColor(android.R.color.black));
                    textLesson.setPadding(0, 5, 0, 10);

                    // Thêm các TextView vào LinearLayout
//                    courseLayout.addView(textHeader);
                    courseLayout.addView(textLesson);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    params.setMargins(0, 0, 0, 0); // Thay đổi giá trị này để điều chỉnh margin
                    courseLayout.setLayoutParams(params);

                    // Thêm LinearLayout vào lessonContent
                    lessonContent.addView(courseLayout);

                } while (cursor1.moveToNext());

                // Đóng cursor sau khi xử lý xong
                cursor1.close();
            }
        } else {
            Log.e("Register_courses", "Cursor is null");
        }

    }

}
