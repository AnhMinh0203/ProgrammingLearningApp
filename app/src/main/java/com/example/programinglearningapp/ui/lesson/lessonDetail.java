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
import com.example.programinglearningapp.ui.course.courseDetail;
import com.example.programinglearningapp.ui.lessonManagement.LessonManagement_Create;
import com.example.programinglearningapp.ui.lessonManagement.LessonManagement_Update;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class lessonDetail extends AppCompatActivity {

    private TextView tvLessonTitle, tvLessonContent;
    private RecyclerView rvQuiz;
    private lessonHelper lessonHelper;
    private QuizAdapter quizAdapter;
    private Button btnNextLesson, btnPreviousLesson,btnUpdateLesson,btnDeleteLesson;

    private int currentLessonId;
    private String courseId;
    List<Quiz> quizList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_detail);
        lessonHelper = new lessonHelper(this);

        // Map the views
        tvLessonTitle = findViewById(R.id.tvLessonTitle);
        tvLessonContent = findViewById(R.id.tvLessonContent);
        rvQuiz = findViewById(R.id.rvQuiz);
        btnNextLesson = findViewById(R.id.btnNextLesson);
        btnPreviousLesson = findViewById(R.id.btnPreviousLesson);
        btnUpdateLesson = findViewById(R.id.btnUpdateLesson);
        btnDeleteLesson = findViewById(R.id.btnDeleteLesson);

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

        btnUpdateLesson.setOnClickListener(v -> {
            Intent intent = new Intent(lessonDetail.this, LessonManagement_Update.class);

            // Fetch lesson content and quiz data
            String contentLesson = lessonHelper.getContentLessonById(String.valueOf(currentLessonId));
            List<Quiz> quizList = fetchExamDataFromDatabase(String.valueOf(currentLessonId));

            // Pass necessary data to the update activity
            intent.putExtra("lessonId", currentLessonId);
            intent.putExtra("status", "update");
            intent.putExtra("lessonTitle", tvLessonTitle.getText().toString());
            intent.putExtra("lessonContent", contentLesson);

            // Pass the quiz data as a Serializable object
            intent.putExtra("quizData", (Serializable) quizList);

            startActivity(intent);
        });


        btnDeleteLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo hộp thoại cảnh báo
                new AlertDialog.Builder(lessonDetail.this)
                        .setTitle("Cảnh báo")
                        .setMessage("Bạn có chắc muốn xóa bài học này")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Thực hiện hành động xóa lesson
                                boolean isDeleted = lessonHelper.deleteLessonById(String.valueOf(currentLessonId));

                                if (isDeleted) {
                                    Toast.makeText(lessonDetail.this, "Xóa bài học thành công!", Toast.LENGTH_SHORT).show();

                                    // Trả kết quả về cho activity trước đó (courseDetail)
                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("IS_LESSON_DELETED", true);
                                    setResult(RESULT_OK, resultIntent);

                                    finish(); // Đóng activity hiện tại sau khi xóa thành công
                                } else {
                                    Toast.makeText(lessonDetail.this, "Xóa bài học thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null) // Không làm gì khi nhấn "Cancel"
                        .show();
            }
        });


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