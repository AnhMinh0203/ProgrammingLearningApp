package com.example.programinglearningapp.db.lesson;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.programinglearningapp.db.DatabaseHelper;

public class lessonHelper {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;  // Add this

    public lessonHelper(Context context) {
        dbHelper = new DatabaseHelper(context);  // Initialize dbHelper here
        database = dbHelper.getWritableDatabase();  // Optional: Use writable database
    }

    public Cursor getLessonsByCourseId(String courseId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();  // Use dbHelper to get readable database
        String query = "SELECT * FROM lessons WHERE course_id = ?";
        return db.rawQuery(query, new String[]{courseId});
    }
}
