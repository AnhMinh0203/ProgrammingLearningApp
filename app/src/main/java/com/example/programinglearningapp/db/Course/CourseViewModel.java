package com.example.programinglearningapp.db.Course;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.programinglearningapp.model.Course;

import java.util.List;

public class CourseViewModel extends ViewModel {
    private final MutableLiveData<List<Course>> courseList = new MutableLiveData<>();

    public LiveData<List<Course>> getCourses() {
        return courseList;
    }

    public void setCourses(List<Course> courses) {
        courseList.setValue(courses);
    }
}
