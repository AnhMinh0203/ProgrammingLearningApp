package com.example.programinglearningapp.db.Course;

import android.content.Context;
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
        // Load image using Glide or similar library
        // Glide.with(context).load(course.getImageUrl()).into(holder.courseImage);

        // Use Glide to load image
        Glide.with(context)
                .load(course.getImageUrl())
/*                .placeholder(R.drawable.placeholder) // optional placeholder
                .error(R.drawable.error) // optional error image*/
                .into(holder.courseImage);

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
}
