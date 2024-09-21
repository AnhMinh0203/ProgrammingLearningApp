package com.example.programinglearningapp.ui.MemberManager;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.programinglearningapp.R;
//import com.example.programinglearningapp.db.CourseAdapter;
import com.example.programinglearningapp.db.DatabaseHelper;
import com.example.programinglearningapp.ui.User;

import java.util.ArrayList;
import java.util.List;

public class MemberManagementFragment extends Fragment {

    private static final int REQUEST_CODE_EDIT = 100;
    private DatabaseHelper dbHelper;
    private List<User> userList;
    private TableLayout tableLayout;
    private Button btn_search;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_member_management, container, false);
        dbHelper = new DatabaseHelper(getContext());

        // Find the TableLayout by its ID
        tableLayout = view.findViewById(R.id.tableLayout);

        // Load danh sách tài khoản
        loadAccounts();

        btn_search = view.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(v -> {
            EditText search = view.findViewById(R.id.editText_fullName);
            String search_value = search.getText().toString();
            if(search_value.isEmpty()) loadAccounts();
            else searchUsersByName(search_value);
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            // Gọi lại hàm loadAccounts() để tải lại danh sách người dùng
            loadAccounts();
        }
    }

    private void loadAccounts() {
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

        TableRow.LayoutParams columnParams = new TableRow.LayoutParams(
                0, TableRow.LayoutParams.WRAP_CONTENT, 1f); // Equal weight for all columns
        final TableRow[] selectedRow = {null};
        for (User user : userList) {
            TableRow tableRow = new TableRow(getContext());

            TextView textViewUsername = new TextView(getContext());
            textViewUsername.setText(user.getUsername());
            textViewUsername.setPadding(8, 8, 8, 8);
            textViewUsername.setGravity(Gravity.CENTER);
            textViewUsername.setLayoutParams(columnParams);

            TextView textViewEmail = new TextView(getContext());
            textViewEmail.setText(user.getEmail());
            textViewEmail.setPadding(8, 8, 8, 8);
            textViewEmail.setGravity(Gravity.CENTER);
            textViewEmail.setLayoutParams(columnParams);

            TextView textViewRole = new TextView(getContext());
            textViewRole.setText(user.getRole() == 1 ? "Admin" : "User");
            textViewRole.setPadding(8, 8, 8, 8);
            textViewRole.setGravity(Gravity.CENTER);
            textViewRole.setLayoutParams(columnParams);
            tableRow.setTag(user.getId());

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Đổi màu hàng được bấm
                    if (selectedRow[0] != null) {
                        selectedRow[0].setBackgroundColor(0x00000000);
                    }
                    selectedRow[0] = tableRow;
                    tableRow.setBackgroundColor(0xFFDDDDDD);

                    // Lấy ID của hàng được bấm
                    int selectedUserId = (int) tableRow.getTag();
                    Intent intent = new Intent(getActivity(), MemberManagementEdit.class);
                    intent.putExtra("userId", user.getId());
                    intent.putExtra("username", user.getUsername());
                    intent.putExtra("email", user.getEmail());
                    intent.putExtra("dob", user.getDob());
                    intent.putExtra("role", user.getRole());
                    startActivityForResult(intent,REQUEST_CODE_EDIT);
                    Toast.makeText(getContext(), "Selected User ID: " + selectedUserId, Toast.LENGTH_SHORT).show();
                }
            });
            tableRow.addView(textViewUsername);
            tableRow.addView(textViewEmail);
            tableRow.addView(textViewRole);
            tableLayout.addView(tableRow);
        }

    }



    public void searchUsersByName(String name) {
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
        TableRow.LayoutParams columnParams = new TableRow.LayoutParams(
                0, TableRow.LayoutParams.WRAP_CONTENT, 1f); // Equal weight for all columns
        final TableRow[] selectedRow = {null};
        for (User user : userList) {
            TableRow tableRow = new TableRow(getContext());

            TextView textViewUsername = new TextView(getContext());
            textViewUsername.setText(user.getUsername());
            textViewUsername.setPadding(8, 8, 8, 8);
            textViewUsername.setGravity(Gravity.CENTER);
            textViewUsername.setLayoutParams(columnParams);

            TextView textViewEmail = new TextView(getContext());
            textViewEmail.setText(user.getEmail());
            textViewEmail.setPadding(8, 8, 8, 8);
            textViewEmail.setGravity(Gravity.CENTER);
            textViewEmail.setLayoutParams(columnParams);

            TextView textViewRole = new TextView(getContext());
            textViewRole.setText(user.getRole() == 1 ? "Admin" : "User");
            textViewRole.setPadding(8, 8, 8, 8);
            textViewRole.setGravity(Gravity.CENTER);
            textViewRole.setLayoutParams(columnParams);
            tableRow.setTag(user.getId());

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Đổi màu hàng được bấm
                    if (selectedRow[0] != null) {
                        selectedRow[0].setBackgroundColor(0x00000000);
                    }
                    selectedRow[0] = tableRow;
                    tableRow.setBackgroundColor(0xFFDDDDDD);

                    // Lấy ID của hàng được bấm
                    int selectedUserId = (int) tableRow.getTag();
                    Intent intent = new Intent(getActivity(), MemberManagementEdit.class);
                    intent.putExtra("userId", user.getId());
                    intent.putExtra("username", user.getUsername());
                    intent.putExtra("email", user.getEmail());
                    intent.putExtra("dob", user.getDob());
                    intent.putExtra("role", user.getRole());
                    startActivityForResult(intent,REQUEST_CODE_EDIT);
                    Toast.makeText(getContext(), "Selected User ID: " + selectedUserId, Toast.LENGTH_SHORT).show();
                }
            });
            tableRow.addView(textViewUsername);
            tableRow.addView(textViewEmail);
            tableRow.addView(textViewRole);
            tableLayout.addView(tableRow);
        }
    }
}
