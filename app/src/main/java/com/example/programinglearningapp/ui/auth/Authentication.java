package com.example.programinglearningapp.ui.auth;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.programinglearningapp.MainActivity;
import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.DatabaseHelper;

public class Authentication extends AppCompatActivity {
    private Button button_signIn;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_authentication);
        dbHelper = new DatabaseHelper(this);

        // Ghi thử dữ liệu vào bảng "users"
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", "test_user");
        values.put("password", "test_password");
        values.put("email", "testhiiii@example.com");
        values.put("dob", "1990-01-01");

        long newRowId = db.insert("users", null, values); // Thao tác ghi // Thao tác ghi
        db.close();
        button_signIn = findViewById(R.id.button_signIn);
        button_signIn.setOnClickListener(v->{

            Intent i = new Intent(Authentication.this, MainActivity.class);
            startActivity(i);
        });

        TextView textView4 = findViewById(R.id.textView4);

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Viết logic khi người dùng click vào TextView này
                Toast.makeText(getApplicationContext(), "Bạn đã click vào Đăng ký", Toast.LENGTH_SHORT).show();

                // Hoặc thực hiện chuyển hướng sang một Activity khác:
                Intent intent = new Intent(Authentication.this, Register.class);
                startActivity(intent);
            }
        });
    }
}