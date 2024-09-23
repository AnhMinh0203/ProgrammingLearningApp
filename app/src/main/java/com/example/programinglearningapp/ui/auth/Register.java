package com.example.programinglearningapp.ui.auth;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

public class Register extends AppCompatActivity {
    private Button button_signIn;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);
        button_signIn = findViewById(R.id.button_signIn);
        button_signIn.setOnClickListener(v->{
            EditText editTextFullName = findViewById(R.id.editText_fullName);
            EditText editTextEmail = findViewById(R.id.editText_email);
            EditText editTextPassword = findViewById(R.id.editText_password);

            String fullname = editTextFullName.getText().toString();
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            if(!fullname.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                boolean isLoggedIn = dbHelper.registerUser(fullname,email, password);
                if (isLoggedIn) {
                    Cursor cursor = dbHelper.loginUser(email, password);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        int role = cursor.getInt(cursor.getColumnIndexOrThrow("role"));
                        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("user_role", role);
                        editor.apply();
                        Intent i = new Intent(Register.this, MainActivity.class);
                        startActivity(i);
                        cursor.close();
                    }
                } else {
                    Toast.makeText(this, "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }

        });

        TextView textView4 = findViewById(R.id.textView4);

        textView4.setOnClickListener(v->{

            Intent i = new Intent(Register.this, Authentication.class);
            startActivity(i);
        });
    }
}