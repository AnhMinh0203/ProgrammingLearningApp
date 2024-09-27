package com.example.programinglearningapp.db.lesson;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.programinglearningapp.db.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;

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

    public Cursor getLessonDetailById (String courseId, String lessonId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "select * from lessons where course_id = ? and id = ?";
        return db.rawQuery(query, new String[]{courseId,lessonId});
    }

    // Fetch quizzes/exams by lesson ID
    public Cursor getExamsByLessonId(String lessonId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM exams WHERE lesson_id = ?";
        return db.rawQuery(query, new String[]{lessonId});
    }

    public boolean isLessonExists(String courseId, int lessonId) {
        Cursor cursor = getLessonDetailById(courseId, String.valueOf(lessonId));
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        return false;
    }

    public String getContentLessonById(String lessonId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();  // Use dbHelper to get readable database
        String query = "SELECT description FROM lessons WHERE id = ?";
        Cursor cursor =  db.rawQuery(query, new String[]{lessonId});
        String lessonDescription = null;

        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve the content from the "description" column
            lessonDescription = cursor.getString(cursor.getColumnIndexOrThrow("description"));
        }
        if (cursor != null) {
            cursor.close();
        }

        // Return the retrieved description
        return lessonDescription;
    }
    public int getCorrectAnswerIndex(int examId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String correctAnswer = null;
        String optionsJson = null;
        int correctIndex = -1; // Mặc định -1, nghĩa là không tìm thấy đáp án

        // Truy vấn cơ sở dữ liệu để lấy correct_answer và options từ bảng exams
        String query = "SELECT correct_answer, options FROM exams WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(examId)});

        if (cursor.moveToFirst()) {
            // Lấy giá trị từ cột correct_answer và options
            correctAnswer = cursor.getString(cursor.getColumnIndexOrThrow("correct_answer"));
            optionsJson = cursor.getString(cursor.getColumnIndexOrThrow("options"));
        }

        cursor.close();
        db.close();

        if (correctAnswer != null && optionsJson != null) {
            try {
                // Chuyển đổi chuỗi JSON của options thành JSONArray
                JSONArray optionsArray = new JSONArray(optionsJson);

                // Lặp qua các đáp án và so sánh với correctAnswer
                for (int i = 0; i < optionsArray.length(); i++) {
                    String option = optionsArray.getString(i);
                    if (option.equals(correctAnswer)) {
                        correctIndex = i; // Đáp án đúng được tìm thấy, trả về vị trí
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return correctIndex; // Trả về vị trí đáp án đúng (hoặc -1 nếu không tìm thấy)
    }
}
