package com.example.programinglearningapp.ui.courseManagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.Course.CourseAdapter;
import com.example.programinglearningapp.db.Course.CourseHelper;
import com.example.programinglearningapp.model.Course;
import com.example.programinglearningapp.ui.course.courseDetail;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CourseManagementFragment extends Fragment implements CourseAdapter.OnCourseClickListener {

    private RecyclerView recyclerViewCourses;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;
    private CourseHelper courseHelper;
    private static final int REQUEST_CODE_CREATE_COURSE = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        courseHelper = new CourseHelper(requireContext());
        // Inflate the layout for this fragment and store it in 'view'
        View view = inflater.inflate(R.layout.fragment_course_management, container, false);

        // Initialize RecyclerView
        recyclerViewCourses = view.findViewById(R.id.recyclerViewCourses);
        recyclerViewCourses.setLayoutManager(new GridLayoutManager(getContext(), 2));
        courseList = new ArrayList<>();
        courseAdapter = new CourseAdapter(getContext(), courseList, this); // Pass 'this' as OnCourseClickListener
        recyclerViewCourses.setAdapter(courseAdapter);

        // Load course data
        loadCourses();

        // Initialize FloatingActionButton
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            // Intent to navigate to Course Creation Activity
            Intent intent = new Intent(getActivity(), CourseManager_Create.class);
            startActivityForResult(intent, REQUEST_CODE_CREATE_COURSE);
        });

        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CREATE_COURSE && resultCode == getActivity().RESULT_OK) {
            // Course created successfully, reload the course list
            loadCourses();
        }
    }

    private void loadCourses() {
        List<Course> newCourseList = courseHelper.getAllCourses();

        if (newCourseList != null) {
            courseList.clear(); // Clear the old data
            courseList.addAll(newCourseList); // Add new data
            courseAdapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
        } else {
            Log.e("CourseManagementFragment", "Failed to load courses: newCourseList is null");
        }
    }

    @Override
    public void onCourseClick(Course course) {
        // Navigate to CourseDetailActivity
        Intent intent = new Intent(getActivity(), courseDetail.class);
        intent.putExtra("courseTitle", course.getTitle());
        intent.putExtra("courseDescription", course.getDescription());
        intent.putExtra("courseImage", course.getImageUrl());
        startActivity(intent);
    }
}

