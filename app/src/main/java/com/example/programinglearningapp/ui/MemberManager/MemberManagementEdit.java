package com.example.programinglearningapp.ui.MemberManager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.programinglearningapp.R;
import com.example.programinglearningapp.db.DatabaseHelper;
import com.example.programinglearningapp.ui.User;

public class MemberManagementEdit extends AppCompatActivity {

    private Button btn_edit;
    private Button btn_delete;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new DatabaseHelper(this);
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
        textViewId.setText(String.valueOf(userId));

        btn_edit = findViewById(R.id.edit);
        btn_edit.setOnClickListener(v -> {
            String newUsername = fullname.getText().toString();
            String newEmail = et_email.getText().toString();
            String newDob = ed_dob.getText().toString();
            int newRole = ed_role.getText().toString().equals("Admin") ? 1 : 0;

            User user = new User();
            user.setId(userId);
            user.setUsername(newUsername);
            user.setEmail(newEmail);
            user.setDob(newDob);
            user.setRole(newRole);

            int result = this.updateUser(user);

            if (result > 0) {
                // Hiển thị thông báo cập nhật thành công
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            } else {
                // Hiển thị thông báo cập nhật thất bại
                Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
            }
        });

        btn_delete = findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(v -> {
            this.Delete(userId);
        });


    }

    public int updateUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("email", user.getEmail());
        contentValues.put("dob", user.getDob());
        contentValues.put("role", user.getRole());

        // Cập nhật thông tin người dùng dựa vào id
        int rowsAffected = db.update("users", contentValues, "id = ?", new String[]{String.valueOf(user.getId())});
        db.close();
        return rowsAffected;
    }

    private void Delete(int userId) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận")
                .setMessage("Bạn có chắc chắn không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.delete("users", "id = ?", new String[]{String.valueOf(userId)});
                    db.close();
                    Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent();
                    setResult(RESULT_OK, returnIntent);
                    finish();
                })
                .setNegativeButton("Không", null)
                .show();
    }
}