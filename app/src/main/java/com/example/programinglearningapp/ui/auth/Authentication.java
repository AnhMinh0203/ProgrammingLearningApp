package com.example.programinglearningapp.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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
        button_signIn = findViewById(R.id.button_signIn);
        button_signIn.setOnClickListener(v->{
            EditText editTextFullName = findViewById(R.id.editText_fullName);
            EditText editTextPassword = findViewById(R.id.editText_password);

            String email = editTextFullName.getText().toString();
            String password = editTextPassword.getText().toString();

            if(!email.isEmpty() && !password.isEmpty()) {
                boolean isLoggedIn = dbHelper.loginUser(email, password);
                if (isLoggedIn) {
                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Authentication.this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(this, "Sai email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }

        });

        TextView textView4 = findViewById(R.id.textView4);

        textView4.setOnClickListener(v->{

            Intent i = new Intent(Authentication.this, Register.class);
            startActivity(i);
        });
    }
}