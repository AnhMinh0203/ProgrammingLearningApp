package com.example.programinglearningapp.ui.lessonManagement;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.DatabaseHelper;
import com.example.programinglearningapp.db.lesson.QuizAdapter;
import com.example.programinglearningapp.db.lesson.lessonHelper;
import com.example.programinglearningapp.model.Quiz;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

public class LessonManagement_Update extends AppCompatActivity {
    private RichEditor mEditor;
    CheckBox quizCheckbox;
    DatabaseHelper dbHelper;
    int UpdateLessonId;
    lessonHelper lessonHelper ;
    private RecyclerView rvQuizEditor;
    private QuizAdapter quizAdapter;
    private List<Quiz> quizList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lesson_manager_update);

        lessonHelper = new lessonHelper(this);

        lessonHelper = new lessonHelper(this);
        mEditor = (RichEditor) findViewById(R.id.editorUpdate);
        Intent intent = getIntent();
        String lessonTitle = intent.getStringExtra("lessonTitle");
        String lessonContent = intent.getStringExtra("lessonContent");
        UpdateLessonId = intent.getIntExtra("lessonId", -1);
        LinearLayout questionContainer = findViewById(R.id.questionContainer);
        List<Quiz> quizList = (List<Quiz>) intent.getSerializableExtra("quizData");
        // Populate the editor and title field
        ((EditText) findViewById(R.id.lessonTitleUpdate)).setText(lessonTitle);
        mEditor.setHtml(lessonContent);

        for(Quiz quiz: quizList){
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View newQuestionView = inflater.inflate(R.layout.quiz_item_create, null);
            // Lấy các thành phần từ newQuestionView
            EditText quizQuestion = newQuestionView.findViewById(R.id.quizQuestion);
            EditText answer1 = newQuestionView.findViewById(R.id.answer1);
            EditText answer2 = newQuestionView.findViewById(R.id.answer2);
            EditText answer3 = newQuestionView.findViewById(R.id.answer3);
            EditText answer4 = newQuestionView.findViewById(R.id.answer4);
            RadioButton correctAnswer1 = newQuestionView.findViewById(R.id.correctAnswer1);
            RadioButton correctAnswer2 = newQuestionView.findViewById(R.id.correctAnswer2);
            RadioButton correctAnswer3 = newQuestionView.findViewById(R.id.correctAnswer3);
            RadioButton correctAnswer4 = newQuestionView.findViewById(R.id.correctAnswer4);

            // Thiết lập dữ liệu từ quiz
            quizQuestion.setText(quiz.getQuestion());
            List<String> options = quiz.getOptions();
            if (options != null && options.size() >= 4) {
                answer1.setText(options.get(0));
                answer2.setText(options.get(1));
                answer3.setText(options.get(2));
                answer4.setText(options.get(3));
            }

            int correctAnswerIndex  = lessonHelper.getCorrectAnswerIndex(quiz.getId());
// Đặt trạng thái cho RadioButton tương ứng với đáp án đúng
            switch (correctAnswerIndex) {
                case 0:
                    correctAnswer1.setChecked(true);
                    break;
                case 1:
                    correctAnswer2.setChecked(true);
                    break;
                case 2:
                    correctAnswer3.setChecked(true);
                    break;
                case 3:
                    correctAnswer4.setChecked(true);
                    break;
                default:
                    // Không có đáp án đúng hoặc lỗi, không chọn radio button nào
                    correctAnswer1.setChecked(false);
                    correctAnswer2.setChecked(false);
                    correctAnswer3.setChecked(false);
                    correctAnswer4.setChecked(false);
                    break;
            }
            questionContainer.addView(newQuestionView);
        }
    }
}