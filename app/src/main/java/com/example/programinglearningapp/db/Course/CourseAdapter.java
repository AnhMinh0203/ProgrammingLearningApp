package com.example.programinglearningapp.db.Course;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.programinglearningapp.R;
import com.example.programinglearningapp.model.Course;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Course> courseList;
    private Context context;
    private OnCourseClickListener onCourseClickListener;

    public CourseAdapter(Context context, List<Course> courseList, OnCourseClickListener onCourseClickListener) {
        this.context = context;
        this.courseList = courseList;
        this.onCourseClickListener = onCourseClickListener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.courseTitle.setText(course.getTitle());
        holder.courseDescription.setText(course.getDescription());

        // Check if image URL is valid
        String imageUrl = course.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Use Glide to load image
            Log.d("ImageURL", "Image URL: " + course.getImageUrl());
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_course) // optional placeholder
//                    .error(R.drawable.error) // optional error image
                    .into(holder.courseImage);
        } else {
            Log.e("CourseAdapter", "Image URL is null or empty for course: " + course.getTitle());
            holder.courseImage.setImageResource(R.drawable.placeholder_course); // Set default image if URL is invalid
        }

        holder.itemView.setOnClickListener(v -> {
            if (onCourseClickListener != null) {
                onCourseClickListener.onCourseClick(course);
            }
        });
    }


    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        ImageView courseImage;
        TextView courseTitle, courseDescription;

        public CourseViewHolder(View itemView) {
            super(itemView);
            courseImage = itemView.findViewById(R.id.courseImage);
            courseTitle = itemView.findViewById(R.id.courseTitle);
            courseDescription = itemView.findViewById(R.id.courseDescription);
        }
    }
    public interface OnCourseClickListener {
        void onCourseClick(Course course);
    }
    public void updateCourseList(List<Course> newCourses) {
        courseList.clear();
        courseList.addAll(newCourses);
        notifyDataSetChanged();
    }
}
