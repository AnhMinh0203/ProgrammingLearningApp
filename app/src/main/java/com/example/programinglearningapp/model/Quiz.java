package com.example.programinglearningapp.model;

import java.util.List;

public class Quiz {
    private String question;
    private List<String> options; // Danh sách đáp án

    public Quiz(String question, List<String> options) {
        this.question = question;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }
}

