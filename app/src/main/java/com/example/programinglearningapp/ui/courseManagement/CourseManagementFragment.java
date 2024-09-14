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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CourseManagementFragment extends Fragment {
    private RecyclerView recyclerViewCourses;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;
    private CourseHelper courseHelper;
    private static final int REQUEST_CODE_CREATE_COURSE = 1;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        courseHelper = new CourseHelper(requireContext());
        // Inflate the layout for this fragment and store it in 'view'
        View view = inflater.inflate(R.layout.fragment_course_management, container, false);

        // Initialize RecyclerView
        recyclerViewCourses = view.findViewById(R.id.recyclerViewCourses);
        recyclerViewCourses.setLayoutManager(new GridLayoutManager(getContext(), 2));
        courseList = new ArrayList<>();
        courseAdapter = new CourseAdapter(getContext(), courseList);
        // Load course data (this can be from your SQLite DB)
        loadCourses();

        // Initialize adapter and set it to RecyclerView
        recyclerViewCourses.setAdapter(courseAdapter);

        // Initialize FloatingActionButton
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            // Intent to navigate to Course Creation Activity
            Intent intent = new Intent(getActivity(), CourseManager_Create.class);
            startActivityForResult(intent, REQUEST_CODE_CREATE_COURSE);
        });
        return view;
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
}
