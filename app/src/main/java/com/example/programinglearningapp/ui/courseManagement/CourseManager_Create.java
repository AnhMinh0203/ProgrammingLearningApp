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

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.model.Course;

import com.example.programinglearningapp.db.Course.CourseHelper;


public class CourseManager_Create extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private static final int TAKE_PHOTO = 2;

    private ImageView ivImage;
    private Uri imageUri;
    private CourseHelper courseHelper;

    private Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        courseHelper = new CourseHelper(this);
        setContentView(R.layout.activity_course_manager_create);

        ivImage = findViewById(R.id.iv_image);
        ivImage.setOnClickListener(v -> showImagePickerDialog());

        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(v->{
            EditText etTitle = findViewById(R.id.et_title);
            EditText etDescription = findViewById(R.id.et_description);

            Course course = new Course();
            course.setTitle(etTitle.getText().toString());
            course.setDescription(etDescription.getText().toString());
            course.setImageUrl( imageUri != null ? imageUri.toString() : null);

            boolean check = courseHelper.addCourse(course);
            if(check){
                Toast.makeText(this, "Thêm khóa học thành công", Toast.LENGTH_SHORT).show();
            }
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE && data != null) {
                // Handle image picking
                imageUri = data.getData();
                ivImage.setImageURI(imageUri);
            } else if (requestCode == TAKE_PHOTO && data != null) {
                // Handle taking photo
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ivImage.setImageBitmap(imageBitmap);
            }
        }
    }
}