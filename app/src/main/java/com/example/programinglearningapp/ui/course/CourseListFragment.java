package com.example.programinglearningapp.ui.course;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.CourseAdapter;
import com.example.programinglearningapp.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseListFragment extends Fragment {
    private RecyclerView recyclerViewCourses;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);

        // Initialize RecyclerView
        recyclerViewCourses = view.findViewById(R.id.recyclerViewCourses);
        recyclerViewCourses.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load course data (this can be from your SQLite DB)
        loadCourses();

        // Initialize adapter and set it to RecyclerView

        courseAdapter = new CourseAdapter(getContext(), courseList);
        recyclerViewCourses.setAdapter(courseAdapter);

        return view;
    }

    // Sample data for now
    private void loadCourses() {
        courseList = new ArrayList<>();
        courseList.add(new Course("Lập trình C++", "Từ cơ bản đến nâng cao", "https://example.com/img1.jpg"));
        courseList.add(new Course("Lập trình Java", "Lập trình hướng đối tượng", "https://example.com/img2.jpg"));
        // Add more sample courses...
    }
}
