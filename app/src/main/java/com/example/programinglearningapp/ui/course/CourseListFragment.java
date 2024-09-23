package com.example.programinglearningapp.ui.course;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class CourseListFragment extends Fragment  implements CourseAdapter.OnCourseClickListener{
    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;
    private CourseHelper courseHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewCourses);
        courseList = new ArrayList<>();
        courseAdapter = new CourseAdapter(getActivity(), courseList, (CourseAdapter.OnCourseClickListener) getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(courseAdapter);
        courseHelper = new CourseHelper(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadCourses();
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        loadCourses(); // Reload the courses every time the activity is resumed
//    }
    // Sample data for now
    private void loadCourses() {
        List<Course> newCourseList = courseHelper.getAllCourses();
        if (newCourseList != null) {
            courseList.clear();
            courseList.addAll(newCourseList);
            courseAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onCourseClick(Course course) {
        // Handle the course click event here
        // For example, you can navigate to a detail view
    }
}
