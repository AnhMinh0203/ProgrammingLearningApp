package com.example.programinglearningapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.databinding.FragmentHomeBinding;
import com.example.programinglearningapp.db.Course.CourseAdapter;
import com.example.programinglearningapp.db.Course.CourseHelper;
import com.example.programinglearningapp.model.Course;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentWronggg extends Fragment implements CourseAdapter.OnCourseClickListener{

    private FragmentHomeBinding binding;
    private RecyclerView recyclerViewHomeCourses;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;
    private CourseHelper courseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        courseHelper = new CourseHelper(requireContext());
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewHomeCourses = view.findViewById(R.id.recyclerViewHomeCourses);
        recyclerViewHomeCourses.setLayoutManager(new GridLayoutManager(getContext(), 2));
        courseList = new ArrayList<>();
        courseAdapter = new CourseAdapter(getContext(), courseList, this); // Pass 'this' as OnCourseClickListener
        recyclerViewHomeCourses.setAdapter(courseAdapter);

        // Load course data
        loadCourses();

        return view;
    }
    private void loadCourses() {
        List<Course> newCourseList = courseHelper.getAllCourses();
        Log.d("HomeFragment", "Number of courses: " + newCourseList.size());
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
        // Handle course click here (optional)
        // You could navigate to a Course Detail Activity if necessary
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}