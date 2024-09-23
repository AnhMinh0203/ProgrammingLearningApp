package com.example.programinglearningapp.db.lesson;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.model.Quiz;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    private List<Quiz> quizList;

    public QuizAdapter(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item_check, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        holder.bind(quizList.get(position));
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    static class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        RadioGroup answersGroup;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.quizQuestion);
            answersGroup = itemView.findViewById(R.id.correctAnswerGroup);
        }

        public void bind(Quiz quiz) {
            questionTextView.setText(quiz.getQuestion());
            answersGroup.removeAllViews(); // Clear previous answers

            for (String option : quiz.getOptions()) {
                RadioButton radioButton = new RadioButton(itemView.getContext());
                radioButton.setText(option);
                answersGroup.addView(radioButton); // Add option to RadioGroup
            }
        }
    }
}
