package com.example.programinglearningapp.ui.lesson;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.lesson.QuizAdapter;
import com.example.programinglearningapp.model.Quiz;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class lessonDetail extends AppCompatActivity {

    private TextView tvLessonTitle, tvLessonContent;
    private RecyclerView rvQuiz;
    private QuizAdapter quizAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_detail);

        // Ánh xạ các view
        tvLessonTitle = findViewById(R.id.tvLessonTitle);
        tvLessonContent = findViewById(R.id.tvLessonContent);
        rvQuiz = findViewById(R.id.rvQuiz);

        // Lấy dữ liệu từ Intent hoặc Database
        // Example data
        String lessonTitle = "Tiêu đề bài học";
        String lessonContent = "<b><i>this is test lesson</i></b>";
        String quizJson = "[{\"question\":\"This is question?\",\"options\":[\"Option 1\",\"Option 2\",\"Option 3\",\"Option 4\"]}]";

        // Hiển thị dữ liệu
        displayLessonData(lessonTitle, lessonContent, quizJson);
    }

    private void displayLessonData(String title, String content, String quizJson) {
        tvLessonTitle.setText(title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvLessonContent.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvLessonContent.setText(Html.fromHtml(content));
        }

        List<Quiz> quizList = parseQuizFromJson(quizJson);
        quizAdapter = new QuizAdapter(quizList);
        rvQuiz.setLayoutManager(new LinearLayoutManager(this));
        rvQuiz.setAdapter(quizAdapter);
    }

    private List<Quiz> parseQuizFromJson(String quizJson) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Quiz>>(){}.getType();
        return gson.fromJson(quizJson, listType);
    }
}