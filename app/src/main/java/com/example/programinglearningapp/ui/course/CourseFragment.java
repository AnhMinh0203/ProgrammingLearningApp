package com.example.programinglearningapp.ui.course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.CourseAdapter;
import com.example.programinglearningapp.model.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment {
//    private RecyclerView recyclerViewCourses;
//    private CourseAdapter courseAdapter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_course_list, container, false);
//
//        // Initialize RecyclerView
//        recyclerViewCourses = view.findViewById(R.id.recyclerViewCourses);
//        recyclerViewCourses.setLayoutManager(new GridLayoutManager(getContext(), 2));
//
//        // Set up sample course data (you can fetch from DB here)
//        List<Course> courseList = new ArrayList<>();
//        courseList.add(new Course("Course 1", "Description 1", "image_url_1"));
//        courseList.add(new Course("Course 2", "Description 2", "image_url_2"));
//        courseList.add(new Course("Course 3", "Description 3", "image_url_3"));
//
//        // Initialize adapter
//        courseAdapter = new CourseAdapter(courseList);
//        recyclerViewCourses.setAdapter(courseAdapter);
//
//        return view;
//    }
}
