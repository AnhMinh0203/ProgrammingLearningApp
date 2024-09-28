package com.example.programinglearningapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RegisterCourse {

    private DatabaseHelper dbHelper;

    public RegisterCourse(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Thêm khóa học mới
    public boolean addCourse(String courseName, String description, String img) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("course_name", courseName);
        values.put("description", description);
        values.put("img", img);

        long result = db.insert("courses", null, values);
        return result != -1; // Trả về true nếu thêm thành công
    }

    // Lấy danh sách khóa học
    public Cursor getAllCourses() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM courses", null);
    }

    // Lấy khóa học theo Id
    public Cursor getCourseById(Integer id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn để lấy khóa học theo ID
        String query = "SELECT * FROM courses WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        // Kiểm tra xem có kết quả nào không
        if (cursor != null && cursor.moveToFirst()) {
            String courseName = cursor.getString(cursor.getColumnIndexOrThrow("course_name"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            // Đảm bảo cursor chứa dữ liệu và trả về
            return cursor;
        }
        db.close();
        // Trả về cursor rỗng nếu không tìm thấy
        return null;
    }


    // Cập nhật thông tin khóa học
    public boolean updateCourse(int id, String courseName, String description, String img) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("course_name", courseName);
        values.put("description", description);
        values.put("img", img);

        int result = db.update("courses", values, "id = ?", new String[]{String.valueOf(id)});
        return result > 0; // Trả về true nếu cập nhật thành công
    }

    // Xóa khóa học
    public boolean deleteCourse(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete("courses", "id = ?", new String[]{String.valueOf(id)});
        return result > 0; // Trả về true nếu xóa thành công
    }

    public Cursor getLessonByCourseId(Integer id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn để lấy khóa học theo ID
        String query = "SELECT * FROM lessons WHERE course_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        // Kiểm tra xem có kết quả nào không
        if (cursor != null && cursor.moveToFirst()) {
            String course_id = cursor.getString(cursor.getColumnIndexOrThrow("course_id"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            // Đảm bảo cursor chứa dữ liệu và trả về
            return cursor;
        }
        db.close();
        // Trả về cursor rỗng nếu không tìm thấy
        return null;
    }

    public boolean registerCourse(int userId, int courseId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Kiểm tra xem khóa học đã được đăng ký chưa
        String query = "SELECT * FROM user_courses WHERE user_id = ? AND course_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(courseId)});

        if (cursor.getCount() > 0) {
            // Khóa học đã được đăng ký
            cursor.close();
            return false; // Đăng ký thất bại
        }

        cursor.close();

        // Nếu khóa học chưa được đăng ký, thêm mới
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("course_id", courseId);

        long result = db.insert("user_courses", null, values);
        return result != -1; // Trả về true nếu thêm thành công
    }
}
