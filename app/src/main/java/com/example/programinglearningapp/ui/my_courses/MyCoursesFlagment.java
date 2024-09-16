package com.example.programinglearningapp.ui.my_courses;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.Course.CourseAdapter;
import com.example.programinglearningapp.db.Course.CourseHelper;
import com.example.programinglearningapp.db.DatabaseHelper;
import com.example.programinglearningapp.model.Course;
import com.example.programinglearningapp.ui.courseManagement.CourseManager_Create;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MyCoursesFlagment extends Fragment {
    private RecyclerView recyclerViewCourses;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;
    private CourseHelper courseHelper;
    private static final int REQUEST_CODE_CREATE_COURSE = 1;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        courseHelper = new CourseHelper(requireContext());
        // Inflate the layout for this fragment and store it in 'view'
        View view = inflater.inflate(R.layout.activity_my_courses, container, false);
        androidx.appcompat.widget.Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        // Initialize RecyclerView
        recyclerViewCourses = view.findViewById(R.id.courseRegistered);
        recyclerViewCourses.setLayoutManager(new GridLayoutManager(getContext(), 2));
        courseList = new ArrayList<>();
        courseAdapter = new CourseAdapter(getContext(), courseList);
        // Load course data (this can be from your SQLite DB)
        loadCourses();

        // Initialize adapter and set it to RecyclerView
        recyclerViewCourses.setAdapter(courseAdapter);

        // Add padding to RecyclerView
        recyclerViewCourses.setPadding(0, 250, 0, 0);

        return view;
    }
    private void loadCourses() {
        List<Course> myCourseList = courseHelper.getCoursesRegisterForUser(1);

        if (myCourseList != null) {
            courseList.clear(); // Clear the old data
            courseList.addAll(myCourseList); // Add new data
            courseAdapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
        } else {
            Log.e("MyCoursesFlagment", "Failed to load courses: newCourseList is null");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Hiển thị lại Toolbar khi rời khỏi fragment
        androidx.appcompat.widget.Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }
}
