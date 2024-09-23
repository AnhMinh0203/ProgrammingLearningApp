package com.example.programinglearningapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ProgramingLearning.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE," +
                "password TEXT," +
                "email TEXT," +
                "dob TEXT," +
                "role INTEGER DEFAULT 0)");

        // Create courses table
        db.execSQL("CREATE TABLE courses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "course_name TEXT," +
                "description TEXT," +
                "img TEXT)");

        // Create lessons table
        db.execSQL("CREATE TABLE lessons (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "course_id INTEGER," +
                "title TEXT," +
                "description TEXT," +
                "content TEXT," +
                "FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE)");

        // Create exams table
        db.execSQL("CREATE TABLE exams (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "lesson_id INTEGER," +
                "question TEXT," +
                "options TEXT," + // Stored as a JSON string
                "correct_answer TEXT," +
                "FOREIGN KEY (lesson_id) REFERENCES lessons(id) ON DELETE CASCADE)");

        // Create user_courses table
        db.execSQL("CREATE TABLE user_courses (" +
                "user_id INTEGER," +
                "course_id INTEGER," +
                "FOREIGN KEY (user_id) REFERENCES users(id)," +
                "FOREIGN KEY (course_id) REFERENCES courses(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS courses");
        db.execSQL("DROP TABLE IF EXISTS lessons");
        db.execSQL("DROP TABLE IF EXISTS exams");
        db.execSQL("DROP TABLE IF EXISTS user_courses");
        onCreate(db);
    }

    // CRUD cho các phương thức khác

    public boolean registerUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Kiểm tra xem người dùng đã tồn tại chưa
        String query = "SELECT * FROM users WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor.getCount() > 0) {
            // Người dùng đã tồn tại
            cursor.close();
            return false; // Đăng ký thất bại
        }

        cursor.close();

        // Nếu người dùng chưa tồn tại, thêm người dùng mới
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("email", email);

        long result = db.insert("users", null, values);
        return result != -1; // Trả về true nếu thêm thành công
    }

    public Cursor loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Truy vấn kiểm tra tên người dùng và mật khẩu
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        return cursor;
    }
}
