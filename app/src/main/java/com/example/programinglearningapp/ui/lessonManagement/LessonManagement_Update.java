package com.example.programinglearningapp.ui.lessonManagement;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.DatabaseHelper;
import com.example.programinglearningapp.db.lesson.QuizAdapter;
import com.example.programinglearningapp.db.lesson.lessonHelper;
import com.example.programinglearningapp.model.Quiz;
import com.example.programinglearningapp.ui.course.courseDetail;
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
    lessonHelper lessonHelper;
    private RecyclerView rvQuizEditor;
    private QuizAdapter quizAdapter;
    private List<Quiz> quizList;
    Button addQuestionButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lesson_manager_update);

        lessonHelper = new lessonHelper(this);
        mEditor = findViewById(R.id.editorUpdate);
        quizCheckbox = findViewById(R.id.quizCheckboxUpdate);
        Intent intent = getIntent();
        String lessonTitle = intent.getStringExtra("lessonTitle");
        String lessonContent = intent.getStringExtra("lessonContent");
        UpdateLessonId = intent.getIntExtra("lessonId", -1);
        addQuestionButton = findViewById(R.id.addQuestionButtonUpdate);
        LinearLayout questionContainer = findViewById(R.id.questionContainer);
        quizList = (List<Quiz>) intent.getSerializableExtra("quizData");

        // Populate the editor and title field
        ((EditText) findViewById(R.id.lessonTitleUpdate)).setText(lessonTitle);
        mEditor.setHtml(lessonContent);

        quizCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                addQuestionButton.setVisibility(View.VISIBLE);
            } else {
                addQuestionButton.setVisibility(View.GONE);
                questionContainer.removeAllViews(); // Remove existing questions if the checkbox is unchecked
            }
        });

        addQuestionButton.setOnClickListener(v -> {
            // Inflate layout câu hỏi trắc nghiệm từ file XML
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View newQuestionView = inflater.inflate(R.layout.quiz_item_create, null);
            questionContainer.addView(newQuestionView);
        });

        // Populate existing quiz questions
        for (Quiz quiz : quizList) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View newQuestionView = inflater.inflate(R.layout.quiz_item_create, null);

            // Get components from newQuestionView
            EditText quizQuestion = newQuestionView.findViewById(R.id.quizQuestion);
            EditText answer1 = newQuestionView.findViewById(R.id.answer1);
            EditText answer2 = newQuestionView.findViewById(R.id.answer2);
            EditText answer3 = newQuestionView.findViewById(R.id.answer3);
            EditText answer4 = newQuestionView.findViewById(R.id.answer4);
            RadioButton correctAnswer1 = newQuestionView.findViewById(R.id.correctAnswer1);
            RadioButton correctAnswer2 = newQuestionView.findViewById(R.id.correctAnswer2);
            RadioButton correctAnswer3 = newQuestionView.findViewById(R.id.correctAnswer3);
            RadioButton correctAnswer4 = newQuestionView.findViewById(R.id.correctAnswer4);

            // Set data from quiz
            quizQuestion.setText(quiz.getQuestion());
            List<String> options = quiz.getOptions();
            if (options != null && options.size() >= 4) {
                answer1.setText(options.get(0));
                answer2.setText(options.get(1));
                answer3.setText(options.get(2));
                answer4.setText(options.get(3));
            }

            int correctAnswerIndex = lessonHelper.getCorrectAnswerIndex(quiz.getId());

            // Set state for RadioButton according to correct answer
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
                    correctAnswer1.setChecked(false);
                    correctAnswer2.setChecked(false);
                    correctAnswer3.setChecked(false);
                    correctAnswer4.setChecked(false);
                    break;
            }
            questionContainer.addView(newQuestionView);
        }

        // Update lesson button click listener
        Button updateLessonButton = findViewById(R.id.updateLesson);
        updateLessonButton.setOnClickListener(v -> updateLesson(this));
    }

    private void updateLesson(Context context) {
        mEditor = findViewById(R.id.editorUpdate);
        String lessonContent = mEditor.getHtml();
        String lessonTitle = ((EditText) findViewById(R.id.lessonTitleUpdate)).getText().toString();

        updateLessonInDb(this, UpdateLessonId, lessonTitle, lessonContent);
        saveQuizQuestionsToDb(context, UpdateLessonId);
    }

    private void updateLessonInDb(Context context, int lessonId, String title, String content) {
        dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("description", content);

        int rowsAffected = db.update("lessons", values, "id = ?", new String[]{String.valueOf(lessonId)});
        db.close();
        if (rowsAffected > 0) {
            Toast.makeText(context, "Bài học đã được cập nhật!", Toast.LENGTH_SHORT).show();
        }
    }

    // Hàm lưu câu hỏi trắc nghiệm vào bảng exams
    // Hàm lưu câu hỏi trắc nghiệm vào bảng exams
    private void saveQuizQuestionsToDb(Context context, int lessonId) {
        LinearLayout questionContainer = findViewById(R.id.questionContainer);
        int questionCount = questionContainer.getChildCount();

        // Duyệt qua các câu hỏi trong view
        for (int i = 0; i < questionCount; i++) {
            View questionView = questionContainer.getChildAt(i);

            EditText questionEditText = questionView.findViewById(R.id.quizQuestion);
            EditText answer1EditText = questionView.findViewById(R.id.answer1);
            EditText answer2EditText = questionView.findViewById(R.id.answer2);
            EditText answer3EditText = questionView.findViewById(R.id.answer3);
            EditText answer4EditText = questionView.findViewById(R.id.answer4);
            RadioGroup correctAnswerGroup = questionView.findViewById(R.id.correctAnswerGroup);

            String question = questionEditText.getText().toString();
            String[] options = {
                    answer1EditText.getText().toString(),
                    answer2EditText.getText().toString(),
                    answer3EditText.getText().toString(),
                    answer4EditText.getText().toString()
            };

            // Lấy đáp án đúng
            int correctAnswerId = correctAnswerGroup.getCheckedRadioButtonId();
            String correctAnswer = "";
            if (correctAnswerId == R.id.correctAnswer1) correctAnswer = options[0];
            else if (correctAnswerId == R.id.correctAnswer2) correctAnswer = options[1];
            else if (correctAnswerId == R.id.correctAnswer3) correctAnswer = options[2];
            else if (correctAnswerId == R.id.correctAnswer4) correctAnswer = options[3];

            // Chuyển options thành chuỗi JSON
            String optionsJson = new Gson().toJson(options);

            // Kiểm tra nếu đây là quiz cũ (có trong danh sách quizList) thì cập nhật
            if (i < quizList.size()) {
                Quiz currentQuiz = quizList.get(i);
                saveQuestionToDb(context, lessonId, question, optionsJson, correctAnswer, currentQuiz.getId());
            } else {
                // Nếu là quiz mới, thêm mới vào DB
                saveQuestionToDb(context, lessonId, question, optionsJson, correctAnswer, null);
            }
        }
    }


    // Hàm lưu từng câu hỏi vào bảng exams
    // Hàm lưu từng câu hỏi vào bảng exams
    private void saveQuestionToDb(Context context, long lessonId, String question, String optionsJson, String correctAnswer, Integer quizId) {
        dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("lesson_id", lessonId);
        values.put("question", question);
        values.put("options", optionsJson);
        values.put("correct_answer", correctAnswer);

        if (quizId != null) {
            // Cập nhật nếu ID của câu hỏi tồn tại
            String whereClause = "id = ?";
            String[] whereArgs = {String.valueOf(quizId)};
            int rowsUpdated = db.update("exams", values, whereClause, whereArgs);

            // Nếu không có bản ghi nào được cập nhật, thực hiện insert
            if (rowsUpdated == 0) {
                db.insert("exams", null, values);
            }
        } else {
            // Chèn mới nếu không có ID
            db.insert("exams", null, values);
        }

        db.close();
        Toast.makeText(this, "Bài học và câu hỏi trắc nghiệm đã được lưu!", Toast.LENGTH_SHORT).show();
    }

}
