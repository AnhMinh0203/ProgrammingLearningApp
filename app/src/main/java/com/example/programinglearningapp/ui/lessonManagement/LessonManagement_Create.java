package com.example.programinglearningapp.ui.lessonManagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.DatabaseHelper;
import com.example.programinglearningapp.ui.course.courseDetail;
import com.google.gson.Gson;

import jp.wasabeef.richeditor.RichEditor;

public class LessonManagement_Create extends AppCompatActivity {
    private RichEditor mEditor;
    CheckBox quizCheckbox;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lesson_management_create);

        quizCheckbox = findViewById(R.id.quizCheckbox);
        LinearLayout quizCreationLayout = findViewById(R.id.quizCreationLayout);
        Button addQuestionButton = findViewById(R.id.addQuestionButton);
        LinearLayout questionContainer = findViewById(R.id.questionContainer);
        Button saveLessonButton = findViewById(R.id.saveLesson);
        // Khi bấm nút "Lưu"
        saveLessonButton.setOnClickListener(v -> saveLesson(this));

        quizCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    quizCreationLayout.setVisibility(View.VISIBLE);
                    addQuestionButton.setVisibility(View.VISIBLE);
                } else {
                    quizCreationLayout.setVisibility(View.GONE);
                    addQuestionButton.setVisibility(View.GONE);
                }
            }
        });

        addQuestionButton.setOnClickListener(v -> {
            // Inflate layout câu hỏi trắc nghiệm từ file XML
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View newQuestionView = inflater.inflate(R.layout.quiz_item_create, null);

            // Thêm layout mới này vào questionContainer
            questionContainer.addView(newQuestionView);

            // Bây giờ bạn có thể xử lý câu hỏi mới được thêm tương tự như câu hỏi ban đầu
            // Ví dụ:
            EditText questionEditText = newQuestionView.findViewById(R.id.quizQuestion);
            RadioGroup correctAnswerGroup = newQuestionView.findViewById(R.id.correctAnswerGroup);

            // Bạn có thể lấy dữ liệu từ các EditText và RadioButton này khi cần.
        });


        mEditor = (RichEditor) findViewById(R.id.editor);
        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(16);
        mEditor.setPadding(10, 10, 10, 10);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor.setPlaceholder("Insert text here...");

        findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.undo();
            }
        });

        findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.redo();
            }
        });

        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBold();
            }
        });

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setItalic();
            }
        });

        findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setSubscript();
            }
        });

        findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setSuperscript();
            }
        });

        findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });

        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setUnderline();
            }
        });

        findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(1);
            }
        });

        findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(2);
            }
        });

        findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(3);
            }
        });

        findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(4);
            }
        });

        findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(5);
            }
        });

        findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setHeading(6);
            }
        });

        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            private boolean isChanged;

            @Override
            public void onClick(View v) {
                mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
                isChanged = !isChanged;
            }
        });

        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setIndent();
            }
        });

        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setOutdent();
            }
        });

        findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignLeft();
            }
        });

        findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignCenter();
            }
        });

        findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });

        findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBlockquote();
            }
        });

        findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setBullets();
            }
        });

        findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.setNumbers();
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertImage("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg",
                        "dachshund", 320);
            }
        });

        findViewById(R.id.action_insert_youtube).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertYoutubeVideo("https://www.youtube.com/embed/pS5peqApgUA");
            }
        });

        findViewById(R.id.action_insert_audio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertAudio("https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_5MG.mp3");
            }
        });

        findViewById(R.id.action_insert_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertVideo("https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_10MB.mp4", 360);
            }
        });

        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertLink("https://github.com/wasabeef", "wasabeef");
            }
        });
        findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditor.insertTodo();
            }
        });
    }

    private void saveLesson(Context context) {
        String lessonContent = mEditor.getHtml();
        String lessonTitle =  ((EditText) findViewById(R.id.lessonTitle)).getText().toString(); // Lấy từ EditText nhập tiêu đề

        // Lưu bài học vào bảng lessons
        long lessonId = saveLessonToDb(this,lessonTitle, lessonContent);
        if (lessonId != -1) {
            // Lưu câu hỏi trắc nghiệm
            saveQuizQuestionsToDb(context,lessonId);

        } else {
            Toast.makeText(this, "Lưu bài học thất bại.", Toast.LENGTH_SHORT).show();
        }


    }

    private long saveLessonToDb(Context context,String title, String content) {
        dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String idCourse = courseDetail.courseId;
        values.put("course_id", idCourse); // Lấy course_id hiện tại
        values.put("title", title);
        values.put("description", content);
//        values.put("content", content); // Thêm giá trị cho content

        long lessonId = db.insert("lessons", null, values);
        db.close();
        return lessonId;
    }

    // Hàm lưu câu hỏi trắc nghiệm vào bảng exams
    private void saveQuizQuestionsToDb(Context context,long lessonId) {
        LinearLayout questionContainer = findViewById(R.id.questionContainer);
        int questionCount = questionContainer.getChildCount();

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

            // Lưu câu hỏi vào bảng exams
            saveQuestionToDb(context,lessonId, question, optionsJson, correctAnswer);
        }
    }

    // Hàm lưu từng câu hỏi vào bảng exams
    private void saveQuestionToDb(Context context,long lessonId, String question, String optionsJson, String correctAnswer) {
        dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("lesson_id", lessonId);
        values.put("question", question);
        values.put("options", optionsJson);
        values.put("correct_answer", correctAnswer);

        db.insert("exams", null, values);
        db.close();
        Toast.makeText(this, "Bài học và câu hỏi trắc nghiệm đã được lưu!", Toast.LENGTH_SHORT).show();
    }
}