package com.example.programinglearningapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.programinglearningapp.MainActivity;
import com.example.programinglearningapp.R;
import com.example.programinglearningapp.ui.auth.Authentication;

public class MemberManagementEdit extends AppCompatActivity {

    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_member_management_edit);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", -1); // Giá trị mặc định là -1
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String dob = intent.getStringExtra("dob");
        int role = intent.getIntExtra("role", 0); // Giá trị mặc định là 0

        EditText fullname = findViewById(R.id.editText_fullName);
        fullname.setText(username);

        EditText et_email = findViewById(R.id.editText_email);
        et_email.setText(email);

        EditText ed_dob = findViewById(R.id.editTextDate);
        ed_dob.setText(dob);

        EditText ed_role = findViewById(R.id.editTextText_role);
        if(role == 1) ed_role.setText("Admin");
        else ed_role.setText("User");

        TextView textViewId = findViewById(R.id.IdUser);
        textViewId.setText(userId);


//        btn_back = findViewById(R.id.btn_back);
//        btn_back.setOnClickListener(v -> {
//            Intent i = new Intent(MemberManagementEdit.this, MemberManagement.class);
//            startActivity(i);
//        });

    }
}