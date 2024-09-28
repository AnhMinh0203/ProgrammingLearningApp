package com.example.programinglearningapp.db.Course;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    public boolean addCourse (Course course){
        ContentValues contentValues = new ContentValues();
        contentValues.put("course_name",course.getTitle());
        contentValues.put("description",course.getDescription());
        contentValues.put("img",course.getImageUrl());
        long result = database.insert("courses",null,contentValues);

    /*    database.execSQL("PRAGMA wal_checkpoint;");*/

        return result != -1;
    }

    public List<Course> getAllCourses (){
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses";
        Cursor cursor = database.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do{
                Course course = new Course();
               /* course.setId(cursor.getInt(cursor.getColumnIndex("id")));*/
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

    public List<Course> getCoursesRegisterForUser (int user_id){
        List<Course> courses = new ArrayList<>();
        String query = "SELECT courses.id, courses.course_name, courses.description, courses.img " +
                "FROM user_courses " +
                "JOIN courses ON user_courses.course_id = courses.id " +
                "WHERE user_courses.user_id = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(user_id)});

        if (cursor.moveToFirst()){
            do{
                Course course = new Course();
                /* course.setId(cursor.getInt(cursor.getColumnIndex("id")));*/
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
        List<Course> test = courses;

        cursor.close();
        return courses;
    }
}
