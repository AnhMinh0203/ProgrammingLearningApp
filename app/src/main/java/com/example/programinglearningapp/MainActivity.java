package com.example.programinglearningapp;

import static androidx.core.content.ContentProviderCompat.requireContext;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.programinglearningapp.db.Course.CourseAdapter;
import com.example.programinglearningapp.db.Course.CourseHelper;
import com.example.programinglearningapp.model.Course;
import com.example.programinglearningapp.ui.auth.Authentication;
import com.example.programinglearningapp.ui.course.CourseListFragment;
import com.example.programinglearningapp.ui.course.courseDetail;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.programinglearningapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private RecyclerView recyclerViewHomeCourses;
    private CourseAdapter courseAdapter;
    private List<Course> courseList;
    private CourseHelper courseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nhận vai trò của người dùng từ Intent
        String userRole = getIntent().getStringExtra("userRole");

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Cấu hình điều hướng dựa trên vai trò người dùng
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_infor_personal, R.id.nav_course_management_user,
                R.id.nav_log_out) // Chỉ hiển thị các mục này cho user
                .setOpenableLayout(drawer)
                .build();

        if (userRole.equals("0")) {
            // Nếu người dùng là admin, thêm các mục điều hướng quản lý
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_infor_personal, R.id.nav_course_management_user,
                    R.id.nav_member_management, R.id.nav_course_management_admin, R.id.nav_log_out)
                    .setOpenableLayout(drawer)
                    .build();

        } else {
            // Ẩn mục dành cho admin nếu vai trò là user
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_member_management).setVisible(false);
            menu.findItem(R.id.nav_course_management_admin).setVisible(false);
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }


    private void loadHomeFragment() {
        Fragment fragment = new CourseListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_content_main, fragment)
                .commit();
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

}