package com.randomone.androidmonsterc3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import static com.randomone.androidmonsterc3.StudentProfileEdit.SHARED_PREFS;

public class ManagerDashboard extends AppCompatActivity {

    /*private DrawerLayout drawer;
    private FloatingActionButton fab;
    private ExtendedFloatingActionButton efabStudent, efabModule;
    private Animation fabOpen, fabClose;
    private TextView mheaderStudentname;
    private TextView mheaderStudentID;
    String studentfn, studentln, studentid;


    public Boolean managerMode = true;
    Boolean isOpen = false;*/


    private static final String TAG = "ManagerDashboard";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference studentRef = db.collection("students");
    CollectionReference modulesRef = db.collection("modules");

    MaterialCardView studentsCard;
    TextView txtTotal;
    TextView txtSE;
    TextView txtNE;
    TextView txtDB;
    TextView txtWD;
    TextView txtNone;
    TextView txtModuleTotal;
    TextView txtStreams;
    TextView txtModuleTotalSE;
    TextView txtModuleTotalNE;
    TextView txtModuleTotalWD;
    TextView txtModuleTotalDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dashboard);

        txtTotal = findViewById(R.id.txtTotal);
        txtSE = findViewById(R.id.txtSE);
        txtNE = findViewById(R.id.txtNE);
        txtDB = findViewById(R.id.txtDB);
        txtWD = findViewById(R.id.txtWD);
        txtNone = findViewById(R.id.txtNone);
        studentsCard = findViewById(R.id.StudentsCard);
        txtModuleTotal = findViewById(R.id.txtModuleTotal);
        txtStreams = findViewById(R.id.txtStreams);
        txtModuleTotalSE = findViewById(R.id.txtModuleTotalSE);
        txtModuleTotalNE = findViewById(R.id.txtModuleTotalNE);
        txtModuleTotalDB = findViewById(R.id.txtModuleTotalDB);
        txtModuleTotalWD = findViewById(R.id.txtModuleTotalWD);

        studentsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCLickStudents(view);
            }
        });

        getSupportActionBar().setTitle("Dashboard");



        /*Intent intent = getIntent();



        if (managerMode == true) {
            setContentView(R.layout.activity_modules_manager);
        }
        else {
            setContentView(R.layout.activity_modules_student);
        }

        fab = findViewById(R.id.fab);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);                   //replacing action bar with toolbar for navmenu

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);        //used to call the onNavigationItemSelected Method
        navigationView.setNavigationItemSelectedListener(this);

        View headView = navigationView.getHeaderView(0);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        studentfn = sharedPreferences.getString("studentfn", "Student");
        studentln = sharedPreferences.getString("studentln", "name");
        studentid = sharedPreferences.getString("studentid", "12344321");

        mheaderStudentname = headView.findViewById(R.id.student_name);
        mheaderStudentID = headView.findViewById(R.id.student_id);

        if (managerMode == true){
            mheaderStudentname.setText("Hami");
            mheaderStudentID.setText("000000");
        }else{
            mheaderStudentname.setText(studentfn + " " + studentln);
            mheaderStudentID.setText(studentid);
        }

        efabStudent = findViewById(R.id.fab2);
        efabModule = findViewById(R.id.fab1);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
        new CoreFragment()).commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
                    efabStudent.startAnimation(fabClose);
                    efabModule.startAnimation(fabClose);
                    efabStudent.setClickable(false);
                    efabModule.setClickable(false);
                    isOpen = false;
                }
                else{
                    efabStudent.startAnimation(fabOpen);
                    efabModule.startAnimation(fabOpen);
                    efabStudent.setClickable(true);
                    efabModule.setClickable(true);
                    isOpen = true;
                }
            }
        });

        efabStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabHider();
                newModule();
            }
        });
        efabModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabHider();
                newStudent();
            }
        });

        if (managerMode == false) {
            fab.setVisibility(View.GONE);
        }*/


    }


    /*public void fabHider(){
        if (isOpen == true) {
            efabStudent.startAnimation(fabClose);
            efabModule.startAnimation(fabClose);
            efabStudent.setClickable(false);
            efabModule.setClickable(false);
            isOpen = false;
        }
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutFragment()).commit();
                break;
            case R.id.software_stream:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SoftwareFragment()).commit();
                break;
            case R.id.database_stream:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DatabaseFragment()).commit();
                break;
            case R.id.networking_stream:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NetworkingFragment()).commit();
                break;
            case R.id.web_stream:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WebFragment()).commit();
                break;
            case R.id.nav_students:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new StudentFragment()).commit();
                break;
            case R.id.nav_student_profile:
                Intent intent = new Intent(ManagerDashboard.this, StudentProfile.class);
                startActivity(intent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void newModule(){
        Intent intent = new Intent(this, ModuleCreatorActivity.class);
        startActivity(intent);
    }

    public void newStudent(){
        Intent intent = new Intent(this, StudentCreatorActivity.class);
        startActivity(intent);
    }

    public Boolean getManagerMode() {
        return managerMode;
    }*/


    public void onCLickStudents(View v) {
        //FragmentManager fm = getSupportFragmentManager();
        //fm.beginTransaction().replace(R.id.containerD, new ()).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        studentRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            txtTotal.setText("Total Students : " + String.valueOf(task.getResult().size()));
                            //int count = 0;
                            //for (DocumentSnapshot document : task.getResult()) {
                            //    count++;
                            //}
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        studentRef.whereEqualTo("pathway", "Software Engineering")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            txtSE.setText("Software Engineering (SE) : " + String.valueOf(task.getResult().size()));
                            //int count = 0;
                            //for (DocumentSnapshot document : task.getResult()) {
                            //    count++;
                            //}
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        studentRef.whereEqualTo("pathway", "Network Engineering")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            txtNE.setText("Network Engineering (NE) : " + String.valueOf(task.getResult().size()));
                            //int count = 0;
                            //for (DocumentSnapshot document : task.getResult()) {
                            //    count++;
                            //}
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        studentRef.whereEqualTo("pathway", "Database Architecture")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            txtDB.setText("Database Architecture (DB) : " + String.valueOf(task.getResult().size()));
                            //int count = 0;
                            //for (DocumentSnapshot document : task.getResult()) {
                            //    count++;
                            //}
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        studentRef.whereEqualTo("pathway", "Web Development")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            txtWD.setText("Multimedia Web Development (WD) : " + String.valueOf(task.getResult().size()));
                            //int count = 0;
                            //for (DocumentSnapshot document : task.getResult()) {
                            //    count++;
                            //}
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        studentRef.whereEqualTo("pathway", "Undecided")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            txtNone.setText("Undecided : " + String.valueOf(task.getResult().size()));
                            //int count = 0;
                            //for (DocumentSnapshot document : task.getResult()) {
                            //    count++;
                            //}
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        modulesRef
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            txtModuleTotal.setText("Total Modules : " + String.valueOf(task.getResult().size()));
                            //int count = 0;
                            //for (DocumentSnapshot document : task.getResult()) {
                            //    count++;
                            //}
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        txtStreams.setText("Streams : 4");


        modulesRef.whereArrayContains("pathway", "software")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            txtModuleTotalSE.setText("Total SE Modules : " + String.valueOf(task.getResult().size()));
                            //int count = 0;
                            //for (DocumentSnapshot document : task.getResult()) {
                            //    count++;
                            //}
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        modulesRef.whereArrayContains("pathway", "networking")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            txtModuleTotalNE.setText("Total NE Modules : " + String.valueOf(task.getResult().size()));
                            //int count = 0;
                            //for (DocumentSnapshot document : task.getResult()) {
                            //    count++;
                            //}
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        modulesRef.whereArrayContains("pathway", "database")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            txtModuleTotalDB.setText("Total DB Modules : " + String.valueOf(task.getResult().size()));
                            //int count = 0;
                            //for (DocumentSnapshot document : task.getResult()) {
                            //    count++;
                            //}
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        modulesRef.whereArrayContains("pathway", "web")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            txtModuleTotalWD.setText("Total WD Modules : " + String.valueOf(task.getResult().size()));
                            //int count = 0;
                            //for (DocumentSnapshot document : task.getResult()) {
                            //    count++;
                            //}
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }
}