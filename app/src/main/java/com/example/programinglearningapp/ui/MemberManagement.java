package com.example.programinglearningapp.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.programinglearningapp.MainActivity;
import com.example.programinglearningapp.R;
import com.example.programinglearningapp.databinding.ActivityMainBinding;
import com.example.programinglearningapp.db.DatabaseHelper;
import com.example.programinglearningapp.ui.auth.Authentication;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.widget.Toast;

public class MemberManagement extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private List<User> userList;
    private Button btn_search;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_management);



        dbHelper = new DatabaseHelper(this);

        // Load danh sách tài khoản
        loadAccounts();

        btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(v -> {
            EditText search = findViewById(R.id.editText_fullName);
            String search_value = search.getText().toString();
            if(search_value.isEmpty()) loadAccounts();
            else searchUsersByName(search_value);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void loadAccounts() {
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        int headerId = R.id.header_row;  // Thay thế bằng ID thực tế của header row nếu cần

        // Lấy số lượng các hàng trong TableLayout
        int childCount = tableLayout.getChildCount();

        // Lặp qua tất cả các hàng trong TableLayout
        for (int i = childCount - 1; i >= 0; i--) {
            View child = tableLayout.getChildAt(i);

            // Kiểm tra xem hàng có phải là header không
            if (child.getId() != headerId) {
                // Xóa hàng không phải là header
                tableLayout.removeViewAt(i);
            }
        }

        // Lấy danh sách tài khoản từ cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users", null);
        userList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
                user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                user.setDob(cursor.getString(cursor.getColumnIndexOrThrow("dob")));
                user.setRole(cursor.getInt(cursor.getColumnIndexOrThrow("role")));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        for (User user : userList) {
            TableRow tableRow = new TableRow(this);

            TextView textViewUsername = new TextView(this);
            textViewUsername.setText(user.getUsername());
            textViewUsername.setPadding(8, 8, 8, 8);
            textViewUsername.setGravity(View.TEXT_ALIGNMENT_CENTER);
            // Giảm kích thước font
            textViewUsername.setTextSize(12);  // Ví dụ kích thước 12sp
            // Cho phép tự động xuống dòng nếu văn bản dài
            textViewUsername.setSingleLine(false);
            textViewUsername.setMaxLines(3);  // Tối đa 3 dòng
            textViewUsername.setEllipsize(null);  // Không có dấu "..." khi xuống dòng

            TextView textViewEmail = new TextView(this);
            textViewEmail.setText(user.getEmail());
            textViewEmail.setPadding(8, 8, 8, 8);
            textViewEmail.setGravity(View.TEXT_ALIGNMENT_CENTER);
            // Giảm kích thước font
            textViewEmail.setTextSize(12);  // Ví dụ kích thước 12sp
            // Cho phép tự động xuống dòng nếu văn bản dài
            textViewEmail.setSingleLine(false);
            textViewEmail.setMaxLines(3);  // Tối đa 3 dòng
            textViewEmail.setEllipsize(null);  // Không có dấu "..." khi xuống dòng

            TextView textViewRole = new TextView(this);
            int role = user.getRole();
            if(role == 1) textViewRole.setText("Admin");
            else textViewRole.setText("User");
            textViewRole.setPadding(8, 8, 8, 8);
            textViewRole.setGravity(View.TEXT_ALIGNMENT_CENTER);
            // Giảm kích thước font
            textViewRole.setTextSize(12);  // Ví dụ kích thước 12sp
            // Cho phép tự động xuống dòng nếu văn bản dài
            textViewRole.setSingleLine(false);
            textViewRole.setMaxLines(3);  // Tối đa 3 dòng
            textViewRole.setEllipsize(null);  // Không có dấu "..." khi xuống dòng

            Button buttonEdit = new Button(this);
            buttonEdit.setText("Edit");
            buttonEdit.setTextSize(12);  // Ví dụ kích thước 12sp
            // Điều chỉnh kích thước của Button
            TableRow.LayoutParams paramsEdit = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            buttonEdit.setLayoutParams(paramsEdit);
            buttonEdit.setOnClickListener(v -> {
                Intent intent = new Intent(MemberManagement.this, MemberManagementEdit.class);
                intent.putExtra("userId", user.getId());
                intent.putExtra("username", user.getUsername());
                intent.putExtra("email", user.getEmail());
                intent.putExtra("dob", user.getDob());
                intent.putExtra("role", user.getRole());
                startActivity(intent);
            });

            Button buttonDelete = new Button(this);
            buttonDelete.setText("Delete");
            buttonDelete.setTextSize(12);  // Ví dụ kích thước 12sp
            // Điều chỉnh kích thước của Button
            TableRow.LayoutParams paramsDelete = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            buttonDelete.setLayoutParams(paramsDelete);
            buttonDelete.setOnClickListener(v -> {
                Delete(user.getId());
            });

            tableRow.addView(textViewUsername);
            tableRow.addView(textViewEmail);
            tableRow.addView(textViewRole);
            tableRow.addView(buttonEdit);
            tableRow.addView(buttonDelete);
            tableLayout.addView(tableRow);
        }
    }
    private void Delete(int userId) {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận")
                .setMessage("Bạn có chắc chắn không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete("users", "id = ?", new String[]{String.valueOf(userId)});
                        db.close();
                        Toast.makeText(MemberManagement.this, "User deleted", Toast.LENGTH_SHORT).show();
                        loadAccounts();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng nhấn "Không"
                    }
                })
                .show();
    }


    public void searchUsersByName(String name) {
        TableLayout tableLayout = findViewById(R.id.tableLayout);
        int headerId = R.id.header_row;  // Thay thế bằng ID thực tế của header row nếu cần

        // Lấy số lượng các hàng trong TableLayout
        int childCount = tableLayout.getChildCount();

        // Lặp qua tất cả các hàng trong TableLayout
        for (int i = childCount - 1; i >= 0; i--) {
            View child = tableLayout.getChildAt(i);

            // Kiểm tra xem hàng có phải là header không
            if (child.getId() != headerId) {
                // Xóa hàng không phải là header
                tableLayout.removeViewAt(i);
            }
        }

        // Lấy danh sách tài khoản từ cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM users WHERE username LIKE ?";
        String[] selectionArgs = new String[]{"%" + name + "%"};
        Cursor cursor = db.rawQuery(query, selectionArgs);
        userList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
                user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                user.setDob(cursor.getString(cursor.getColumnIndexOrThrow("dob")));
                user.setRole(cursor.getInt(cursor.getColumnIndexOrThrow("role")));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        for (User user : userList) {
            TableRow tableRow = new TableRow(this);

            TextView textViewUsername = new TextView(this);
            textViewUsername.setText(user.getUsername());
            textViewUsername.setPadding(8, 8, 8, 8);
            textViewUsername.setGravity(View.TEXT_ALIGNMENT_CENTER);
            // Giảm kích thước font
            textViewUsername.setTextSize(12);  // Ví dụ kích thước 12sp
            // Cho phép tự động xuống dòng nếu văn bản dài
            textViewUsername.setSingleLine(false);
            textViewUsername.setMaxLines(3);  // Tối đa 3 dòng
            textViewUsername.setEllipsize(null);  // Không có dấu "..." khi xuống dòng

            TextView textViewEmail = new TextView(this);
            textViewEmail.setText(user.getEmail());
            textViewEmail.setPadding(8, 8, 8, 8);
            textViewEmail.setGravity(View.TEXT_ALIGNMENT_CENTER);
            // Giảm kích thước font
            textViewEmail.setTextSize(12);  // Ví dụ kích thước 12sp
            // Cho phép tự động xuống dòng nếu văn bản dài
            textViewEmail.setSingleLine(false);
            textViewEmail.setMaxLines(3);  // Tối đa 3 dòng
            textViewEmail.setEllipsize(null);  // Không có dấu "..." khi xuống dòng

            TextView textViewRole = new TextView(this);
            int role = user.getRole();
            if(role == 1) textViewRole.setText("Admin");
            else textViewRole.setText("User");
            textViewRole.setPadding(8, 8, 8, 8);
            textViewRole.setGravity(View.TEXT_ALIGNMENT_CENTER);
            // Giảm kích thước font
            textViewRole.setTextSize(12);  // Ví dụ kích thước 12sp
            // Cho phép tự động xuống dòng nếu văn bản dài
            textViewRole.setSingleLine(false);
            textViewRole.setMaxLines(3);  // Tối đa 3 dòng
            textViewRole.setEllipsize(null);  // Không có dấu "..." khi xuống dòng

            Button buttonEdit = new Button(this);
            buttonEdit.setText("Edit");
            buttonEdit.setTextSize(12);  // Ví dụ kích thước 12sp
            // Điều chỉnh kích thước của Button
            TableRow.LayoutParams paramsEdit = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            buttonEdit.setLayoutParams(paramsEdit);
            buttonEdit.setOnClickListener(v -> {
                Delete(user.getId());
                loadAccounts();
            });

            Button buttonDelete = new Button(this);
            buttonDelete.setText("Delete");
            buttonDelete.setTextSize(12);  // Ví dụ kích thước 12sp
            // Điều chỉnh kích thước của Button
            TableRow.LayoutParams paramsDelete = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            buttonDelete.setLayoutParams(paramsDelete);
            buttonDelete.setOnClickListener(v -> {
                Delete(user.getId());
            });

            tableRow.addView(textViewUsername);
            tableRow.addView(textViewEmail);
            tableRow.addView(textViewRole);
            tableRow.addView(buttonEdit);
            tableRow.addView(buttonDelete);
            tableLayout.addView(tableRow);
        }
    }

}