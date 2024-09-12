package com.example.programinglearningapp.ui.MemberManager;

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
import com.example.programinglearningapp.db.CourseAdapter;
import com.example.programinglearningapp.db.DatabaseHelper;
import com.example.programinglearningapp.ui.User;

import java.util.ArrayList;
import java.util.List;

public class MemberManagementFragment extends Fragment {

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

            // Set fixed width for buttons to maintain alignment
            TableRow.LayoutParams buttonParams = new TableRow.LayoutParams(
                    0, TableRow.LayoutParams.WRAP_CONTENT, 1f);
            buttonParams.setMargins(8, 8, 8, 8); // Optional: add some margin between buttons

            Button buttonEdit = new Button(getContext());
            buttonEdit.setText("EDIT");
            buttonEdit.setLayoutParams(buttonParams);
            buttonEdit.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), MemberManagementEdit.class);
                intent.putExtra("userId", user.getId());
                intent.putExtra("username", user.getUsername());
                intent.putExtra("email", user.getEmail());
                intent.putExtra("dob", user.getDob());
                intent.putExtra("role", user.getRole());

                Bundle bundle = new Bundle();
                bundle.putInt("userId", user.getId());
                bundle.putString("username", user.getUsername());
                bundle.putString("email", user.getEmail());
                bundle.putString("dob", user.getDob());
                bundle.putInt("role", user.getRole());
                startActivity(intent,bundle);
            });

            Button buttonDelete = new Button(getContext());
            buttonDelete.setText("DELETE");
            buttonDelete.setLayoutParams(buttonParams);
            buttonDelete.setOnClickListener(v -> {
                Delete(user.getId());
            });

            // Add the components to the row
            tableRow.addView(textViewUsername);
            tableRow.addView(textViewEmail);
            tableRow.addView(textViewRole);
            tableRow.addView(buttonEdit);
            tableRow.addView(buttonDelete);

            // Add the row to the table
            tableLayout.addView(tableRow);
        }

    }

    private void Delete(int userId) {
        new AlertDialog.Builder(getContext())
                .setTitle("Xác nhận")
                .setMessage("Bạn có chắc chắn không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.delete("users", "id = ?", new String[]{String.valueOf(userId)});
                    db.close();
                    Toast.makeText(getContext(), "User deleted", Toast.LENGTH_SHORT).show();
                    loadAccounts();
                })
                .setNegativeButton("Không", null)
                .show();
    }

    public void searchUsersByName(String name) {
        TableLayout tableLayout = view.findViewById(R.id.tableLayout);
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
            TableRow tableRow = new TableRow(getContext());

            TextView textViewUsername = new TextView(getContext());
            textViewUsername.setText(user.getUsername());
            textViewUsername.setPadding(8, 8, 8, 8);
            textViewUsername.setGravity(View.TEXT_ALIGNMENT_CENTER);
            // Giảm kích thước font
            textViewUsername.setTextSize(12);  // Ví dụ kích thước 12sp
            // Cho phép tự động xuống dòng nếu văn bản dài
            textViewUsername.setSingleLine(false);
            textViewUsername.setMaxLines(3);  // Tối đa 3 dòng
            textViewUsername.setEllipsize(null);  // Không có dấu "..." khi xuống dòng

            TextView textViewEmail = new TextView(getContext());
            textViewEmail.setText(user.getEmail());
            textViewEmail.setPadding(8, 8, 8, 8);
            textViewEmail.setGravity(View.TEXT_ALIGNMENT_CENTER);
            // Giảm kích thước font
            textViewEmail.setTextSize(12);  // Ví dụ kích thước 12sp
            // Cho phép tự động xuống dòng nếu văn bản dài
            textViewEmail.setSingleLine(false);
            textViewEmail.setMaxLines(3);  // Tối đa 3 dòng
            textViewEmail.setEllipsize(null);  // Không có dấu "..." khi xuống dòng

            TextView textViewRole = new TextView(getContext());
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

            Button buttonEdit = new Button(getContext());
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

            Button buttonDelete = new Button(getContext());
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
