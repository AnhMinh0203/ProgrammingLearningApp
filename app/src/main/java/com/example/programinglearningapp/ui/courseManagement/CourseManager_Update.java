package com.example.programinglearningapp.ui.courseManagement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.Course.CourseHelper;
import com.example.programinglearningapp.model.Course;

public class CourseManager_Update extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private static final int TAKE_PHOTO = 2;

    private EditText et_description, et_title;
    private Button btn_update;
    private ImageView iv_image;
    private CourseHelper courseHelper;
    private Uri imageUri;
    private String UpdateCourseId, courseTitle, courseContent, courseImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_manager_update);

        // Initialize views
        btn_update = findViewById(R.id.btn_update);
        et_title = findViewById(R.id.et_title);
        et_description = findViewById(R.id.et_description);
        iv_image = findViewById(R.id.iv_image_update);

        // Initialize CourseHelper
        courseHelper = new CourseHelper(this);

        // Get data from intent
        Intent intent = getIntent();
        UpdateCourseId = intent.getStringExtra("idCourse");
        courseTitle = intent.getStringExtra("titleCourse");
        courseContent = intent.getStringExtra("desCourse");
        courseImageUrl = intent.getStringExtra("imageUrl");

        // Set the course data to EditText
        et_title.setText(courseTitle);
        et_description.setText(courseContent);

        // Set course image if available
        if (courseImageUrl != null) {
            imageUri = Uri.parse(courseImageUrl);
            // Use Glide to load the image from URI
            Glide.with(this)
                    .load(imageUri)
                    .into(iv_image);
        }

        // Set image picker dialog for image selection
        iv_image.setOnClickListener(v -> showImagePickerDialog());

        // Update button action
        btn_update.setOnClickListener(view -> {
            String updatedTitle = et_title.getText().toString();
            String updatedDescription = et_description.getText().toString();

            // Create a Course object and set the updated details
            Course course = new Course();
            course.setId(Integer.parseInt(UpdateCourseId)); // Assuming courseId is passed as String
            course.setTitle(updatedTitle);
            course.setDescription(updatedDescription);
            // Set image URI for the course, if updated
            course.setImageUrl(imageUri != null ? imageUri.toString() : courseImageUrl); // Use existing image if none selected

            // Call the updateCourse method from CourseHelper
            boolean isUpdated = courseHelper.updateCourse(course);

            if (isUpdated) {
                Toast.makeText(CourseManager_Update.this, "Course updated successfully", Toast.LENGTH_SHORT).show();
                finish(); // Finish the activity and go back
            } else {
                Toast.makeText(CourseManager_Update.this, "Failed to update course", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Show image picker dialog
    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");
        builder.setItems(new CharSequence[]{"Choose from Gallery", "Take a Photo"}, (dialog, which) -> {
            switch (which) {
                case 0:
                    // Choose from Gallery
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_IMAGE);
                    break;
                case 1:
                    // Take a Photo
                    Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePhotoIntent, TAKE_PHOTO);
                    break;
            }
        });
        builder.show();
    }

    // Handle result from the image picker or camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE && data != null) {
                // Handle the image picked from gallery
                imageUri = data.getData();
                Glide.with(this) // Use Glide to load the image
                        .load(imageUri)
                        .into(iv_image);
            } else if (requestCode == TAKE_PHOTO && data != null) {
                // Handle the photo taken from the camera
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                // Convert Bitmap to URI if needed
                iv_image.setImageBitmap(imageBitmap);
                // Optionally convert Bitmap to URI and store in imageUri for further use
                // imageUri = getImageUri(this, imageBitmap);
            }
        }
    }

    // Optional method to convert Bitmap to URI
    private Uri getImageUri(Context context, Bitmap bitmap) {
        // Add your implementation to save the bitmap to a file and return the Uri
        return Uri.parse(""); // Placeholder
    }
}
