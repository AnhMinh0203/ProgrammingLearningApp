package com.example.programinglearningapp.db.Course;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.example.programinglearningapp.db.DatabaseHelper;
import com.example.programinglearningapp.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseHelper {
    private SQLiteDatabase database;

    public CourseHelper(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean addCourse (@NonNull Course course){
        ContentValues contentValues = new ContentValues();
        contentValues.put("course_name",course.getTitle());
        contentValues.put("description",course.getDescription());
        contentValues.put("img",course.getImageUrl());
        long result = database.insert("courses",null,contentValues);

        return result != -1;
    }

    public List<Course> getAllCourses (){
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses";
        Cursor cursor = database.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do{
                Course course = new Course();
                int idIndex = cursor.getColumnIndex("id");
                int nameIndex = cursor.getColumnIndex("course_name");
                int descIndex = cursor.getColumnIndex("description");
                int imgIndex = cursor.getColumnIndex("img");

                if (idIndex >= 0) {
                    course.setId(cursor.getInt(idIndex));
                }
                if (nameIndex >= 0) {
                    course.setTitle(cursor.getString(nameIndex));
                }
                if (descIndex >= 0) {
                    course.setDescription(cursor.getString(descIndex));
                }
                if (imgIndex >= 0) {
                    course.setImageUrl(cursor.getString(imgIndex));
                }
                courses.add(course);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return courses;
    }
}
