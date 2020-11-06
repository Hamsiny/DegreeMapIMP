package com.randomone.androidmonsterc3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class ModuleActivityStudents extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TextView studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_students);


        Toolbar toolbar = findViewById(R.id.toolbar_student);
        setSupportActionBar(toolbar);                   //replacing action bar with toolbar for navmenu

        drawer = findViewById(R.id.drawer_layout_student);
        NavigationView navigationView = findViewById(R.id.nav_view_student);        //used to call the onNavigationItemSelected Method
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        studentName = findViewById(R.id.student_name);



        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_student,
                new CoreFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_student,
                        new AboutFragment()).commit();
                break;
            case R.id.software_stream:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_student,
                        new SoftwareFragment()).commit();
                break;
            case R.id.database_stream:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_student,
                        new DatabaseFragment()).commit();
                break;
            case R.id.networking_stream:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_student,
                        new NetworkingFragment()).commit();
                break;
            case R.id.web_stream:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_student,
                        new WebFragment()).commit();
                break;
            case R.id.nav_student_profile:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_student,
//                        new StudentFragment()).commit();
                Intent intent = new Intent(ModuleActivityStudents.this, StudentProfile.class);
                startActivity(intent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}