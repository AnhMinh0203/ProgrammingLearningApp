package com.example.programinglearningapp.ui.MemberManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.programinglearningapp.R;

public class MemberManagementEdit extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_member_management_edit, container, false);

        Bundle args = getArguments();
        if (args != null) {
            int userId = args.getInt("userId");
            String username = args.getString("username");
            String email = args.getString("email");
            String dob = args.getString("dob");
            int role = args.getInt("role");

            // Sử dụng dữ liệu để cập nhật giao diện
            EditText editTextUsername = view.findViewById(R.id.editText_fullName);
            EditText editTextEmail = view.findViewById(R.id.editText_email);
            EditText editTextDob = view.findViewById(R.id.editTextDate);
            //Spinner spinnerRole = view.findViewById(R.id.spinnerRole);

            editTextUsername.setText(username);
            editTextEmail.setText(email);
            editTextDob.setText(dob);
            //spinnerRole.setSelection(role == 1 ? 0 : 1); // Giả sử 0 là Admin và 1 là User
        }

        return view;
    }
}
