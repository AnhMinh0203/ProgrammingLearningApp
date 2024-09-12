package com.example.programinglearningapp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.programinglearningapp.model.Course;

import java.util.ArrayList;

public class MyCourses {
    private DatabaseHelper dbHelper;
    public MyCourses(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public ArrayList<Course> getUserCourses(int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Course> coursesList = new ArrayList<>();
        String query = "SELECT courses.id, courses.course_name, courses.description, courses.img " +
                "FROM courses " +
                "INNER JOIN user_courses ON courses.id = user_courses.course_id " +
                "WHERE user_courses.user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            do {
//                int courseId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String courseName = cursor.getString(cursor.getColumnIndexOrThrow("course_name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String img = cursor.getString(cursor.getColumnIndexOrThrow("img"));

                Course course = new Course(courseName, description, img);
                coursesList.add(course);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return coursesList;
    }
}
