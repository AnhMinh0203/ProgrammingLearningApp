package com.example.programinglearningapp.ui.auth;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.programinglearningapp.MainActivity;
import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.DatabaseHelper;

public class Register extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        dbHelper = new DatabaseHelper(this);

        // Ghi thử dữ liệu vào bảng "users"
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", "test_user");
        values.put("password", "test_password");
        values.put("email", "testhiiii@example.com");
        values.put("dob", "1990-01-01");
    }
}
