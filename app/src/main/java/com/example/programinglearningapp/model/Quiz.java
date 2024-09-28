package com.example.programinglearningapp.model;

import java.io.Serializable;
import java.util.List;

public class Quiz implements Serializable {
    private int id; // Thêm thuộc tính ID
    private String question;
    private List<String> options; // Danh sách đáp án

    public Quiz(int id, String question, List<String> options) { // Thay đổi constructor
        this.id = id;
        this.question = question;
        this.options = options;
    }

    public int getId() { // Thêm phương thức getId()
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }
}
