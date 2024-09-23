package com.example.programinglearningapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import static androidx.core.content.ContentProviderCompat.requireContext;

import static java.security.AccessController.getContext;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.programinglearningapp.db.Course.CourseAdapter;
import com.example.programinglearningapp.db.Course.CourseHelper;
import com.example.programinglearningapp.model.Course;
import com.example.programinglearningapp.ui.course.CourseListFragment;
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

        // Initialize CourseHelper with the current Activity context
//        courseHelper = new CourseHelper(this);

        // Set up RecyclerView
//        recyclerViewHomeCourses = binding.recyclerViewHomeCourses;
//        recyclerViewHomeCourses.setLayoutManager(new GridLayoutManager(this, 2));
//        courseList = new ArrayList<>();

        // Initialize CourseAdapter with Activity context and OnCourseClickListener
//        courseAdapter = new CourseAdapter(this, courseList, this);
//        recyclerViewHomeCourses.setAdapter(courseAdapter);

        // Load courses from the database
//        loadCourses();

        setSupportActionBar(binding.appBarMain.toolbar);
//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null)
//                        .setAnchorView(R.id.fab).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_infor_personal, R.id.nav_course_management_user, R.id.nav_member_management,R.id.nav_course_management_admin,R.id.nav_log_out)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        int role = sharedPreferences.getInt("user_role", 0);
        Menu menu = navigationView.getMenu();
        MenuItem memberManagementItem = menu.findItem(R.id.nav_member_management);

        if (role != 1) {
            memberManagementItem.setVisible(false);
        }

//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//
//                if (id == R.id.nav_member_management) {
//                    // Mở MemberManagementActivity
//                    Intent intent = new Intent(MainActivity.this, MemberManagement.class);
//                    startActivity(intent);
//                } else {
//                    // Xử lý các mục khác
//                    NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main);
//                    if (id == R.id.nav_home) {
//                        navController.navigate(R.id.nav_home);
//                    } else if (id == R.id.nav_infor_personal) {
//                        navController.navigate(R.id.nav_infor_personal);
//                    } else if (id == R.id.nav_infor_course) {
//                        navController.navigate(R.id.nav_infor_course);
//                    } else if (id == R.id.nav_course_management) {
//                        navController.navigate(R.id.nav_course_management);
////                        Intent intent = new Intent(MainActivity.this, course_management.class);
////                        startActivity(intent);
//
//                    }else if (id == R.id.nav_course_management_admin) {
//                        navController.navigate(R.id.nav_course_management_admin);
//                    } else if (id == R.id.nav_log_out) {
//                        // Xử lý đăng xuất
//                    }
//                }
//
//                DrawerLayout drawer = binding.drawerLayout;
//                drawer.closeDrawer(GravityCompat.START);
//                return true;
//            }
//        });
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