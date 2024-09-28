package com.example.programinglearningapp.ui.lesson;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.lesson.QuizAdapter;
import com.example.programinglearningapp.db.lesson.lessonHelper;
import com.example.programinglearningapp.model.Quiz;
import com.example.programinglearningapp.ui.auth.Authentication;
import com.example.programinglearningapp.ui.course.courseDetail;
import com.example.programinglearningapp.ui.lessonManagement.LessonManagement_Update;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class lessionLearnUser extends AppCompatActivity {

    private TextView tvLessonTitle, tvLessonContent;
    private RecyclerView rvQuiz;
    private com.example.programinglearningapp.db.lesson.lessonHelper lessonHelper;
    private QuizAdapter quizAdapter;
    private Button btnNextLesson, btnPreviousLesson,btnUpdateLesson,btnDeleteLesson;

    private int currentLessonId;
    private String courseId;
    List<Quiz> quizList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lession_learn_user);
        lessonHelper = new lessonHelper(this);

        // Map the views
        tvLessonTitle = findViewById(R.id.tvLessonTitle);
        tvLessonContent = findViewById(R.id.tvLessonContent);
        rvQuiz = findViewById(R.id.rvQuiz);
        btnNextLesson = findViewById(R.id.btnNextLesson);
        btnPreviousLesson = findViewById(R.id.btnPreviousLesson);

        // Get data from Intent
        currentLessonId = getIntent().getIntExtra("LESSON_ID", -1); // Get the lesson ID
        courseId = courseDetail.courseId; // Get the course ID

        loadLesson(currentLessonId);

        // Handle the Next Lesson button click
        btnNextLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToNextLesson();
            }
        });

        // Handle the Previous Lesson button click
        btnPreviousLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToPreviousLesson();
            }
        });

        String userRole = Authentication.role;

        if(userRole.equals("0")) {
            btnUpdateLesson.setVisibility(View.GONE);
            btnDeleteLesson.setVisibility(View.GONE);
        }

        String hide = getIntent().getStringExtra("hide");

//        if(userRole.equals("1") && "hide".equals(hide)) {
//            btnUpdateLesson.setVisibility(View.GONE);
//            btnDeleteLesson.setVisibility(View.GONE);
//        }

    }

    // Function to load a lesson
    private void loadLesson(int lessonId) {
        Cursor lessonCursor = lessonHelper.getLessonDetailById(courseId, String.valueOf(lessonId));
        if (lessonCursor != null && lessonCursor.moveToFirst()) {
            int titleIndex = lessonCursor.getColumnIndex("title");
            int contentIndex = lessonCursor.getColumnIndex("description");
            String lessonTitle = lessonCursor.getString(titleIndex);
            String lessonContent = lessonCursor.getString(contentIndex);

            // Display lesson data
            displayLessonData(lessonTitle, lessonContent, String.valueOf(lessonId));
        } else {
            Toast.makeText(this, "No lesson found!", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to display the lesson data
    private void displayLessonData(String title, String content, String lessonId) {
        tvLessonTitle.setText(title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvLessonContent.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvLessonContent.setText(Html.fromHtml(content));
        }

        List<Quiz> quizList = fetchExamDataFromDatabase(lessonId);
        quizAdapter = new QuizAdapter(quizList);
        rvQuiz.setLayoutManager(new LinearLayoutManager(this));
        rvQuiz.setAdapter(quizAdapter);
    }

    // Logic to go to the next lesson
    private void navigateToNextLesson() {
        // Increment the lesson ID to go to the next lesson
        currentLessonId++;
        if (lessonHelper.isLessonExists(courseId, currentLessonId)) {
            loadLesson(currentLessonId);
        } else {
            Toast.makeText(this, "No next lesson available!", Toast.LENGTH_SHORT).show();
        }
    }

    // Logic to go to the previous lesson
    private void navigateToPreviousLesson() {
        if (currentLessonId > 0) {
            currentLessonId--;
            if (lessonHelper.isLessonExists(courseId, currentLessonId)) {
                loadLesson(currentLessonId);
            } else {
                Toast.makeText(this, "No previous lesson available!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to fetch exam data from the database
    private List<Quiz> fetchExamDataFromDatabase(String lessonId) {
        quizList = new ArrayList<>();
        Cursor quizCursor = lessonHelper.getExamsByLessonId(lessonId);

        if (quizCursor != null && quizCursor.moveToFirst()) {
            do {
                int idIndex = quizCursor.getColumnIndex("id");
                int questionIndex = quizCursor.getColumnIndex("question");
                int optionIndex = quizCursor.getColumnIndex("options");
                String question = quizCursor.getString(questionIndex);
                String optionsJson = quizCursor.getString(optionIndex);
                int id = quizCursor.getInt(idIndex);

                // Parse options JSON (assuming options are stored as a JSON string)
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<String>>() {
                }.getType();
                List<String> options = gson.fromJson(optionsJson, listType);

                // Create a Quiz object and add to the list
                Quiz quiz = new Quiz(id,question, options);
                quizList.add(quiz);
            } while (quizCursor.moveToNext());
        }

        return quizList;
    }
}