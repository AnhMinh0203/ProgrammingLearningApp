package com.example.programinglearningapp.ui.register_course;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
//        values.put("course_name", "Responsive Với Grid System");
//        values.put("description", "Trong khóa này chúng ta sẽ học về cách xây dựng giao diện web responsive với Grid System, tương tự Bootstrap 4.");
//        values.put("img", "responsive");
//        long newRowId = db.insert("courses", null, values);
//        db.close();

//         Ghi thử dữ liệu vào bảng "lessons"
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("course_id", 2);
//        values.put("title", "Model");
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
                    params.setMargins(0, -420, 0, 100); // Thay đổi giá trị này để điều chỉnh margin
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
            int count = cursor1.getCount();
            // Kiểm tra nếu cursor có dữ liệu
            if (count > 0) {
                // Lặp qua tất cả các hàng trong cursor
                for (int i = 0; i < count; i++) {
                    cursor1.moveToPosition(i);
                    // Lấy dữ liệu tiêu đề từ cursor
                    String title = cursor1.getString(cursor1.getColumnIndexOrThrow("title"));

                    // Tạo một LinearLayout cho từng bài học
                    LinearLayout lessonLayout = new LinearLayout(this);
                    lessonLayout.setOrientation(LinearLayout.HORIZONTAL); // Horizontal để các thành phần nằm ngang
                    lessonLayout.setPadding(8, 8, 8, 8);
                    lessonLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.border_content_courses));

                    // Tạo ImageView cho biểu tượng bài học
                    ImageView iconLesson = new ImageView(this);
                    LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(24, 24);
                    iconLesson.setLayoutParams(iconParams);
                    iconLesson.setImageResource(R.drawable.icon_plus);
                    iconLesson.setContentDescription("Icon");
                    iconParams.setMargins(10, 20, 0, 20); // Đặt margin cho icon

                    // Tạo TextView cho tiêu đề bài học
                    TextView textLesson = new TextView(this);
                    LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    textParams.setMargins(20, 8, 0, 0); // Đặt margin cho TextView
                    textLesson.setLayoutParams(textParams);
                    textLesson.setText(title);
                    textLesson.setTextSize(16);
                    textLesson.setTextColor(ContextCompat.getColor(this, android.R.color.black));

                    // Thêm ImageView và TextView vào LinearLayout (lessonLayout)
                    lessonLayout.addView(iconLesson);
                    lessonLayout.addView(textLesson);

                    // Thêm lessonLayout vào container chính (lessonContainer hoặc lessonContent)
                    lessonContainer.addView(lessonLayout);

                    // Đặt margin cho mỗi LinearLayout để tạo khoảng cách giữa các bài học
                    LinearLayout.LayoutParams lessonLayoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    lessonLayoutParams.setMargins(0, 16, 0, 0); // Thêm khoảng cách giữa các layout
                    lessonLayout.setLayoutParams(lessonLayoutParams);
                }

                // Đóng cursor sau khi xử lý xong
                cursor1.close();
            }
        } else {
            Log.e("Register_courses", "Cursor is null");
        }


    }

}
