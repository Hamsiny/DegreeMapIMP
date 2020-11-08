package com.randomone.androidmonsterc3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import static com.randomone.androidmonsterc3.StudentProfileEdit.SHARED_PREFS;

public class StudentProfile extends AppCompatActivity {
    private ImageView mStudentImage;
    private TextView mStudentFirstname;
    private TextView mStudentLastname;
    private TextView mStudentID;
    private TextView mStudentEmail;
    private TextView mStudentPhone;
    private TextView mStudentPathway;
    private Button mStudentProfileEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        mStudentImage = (ImageView) findViewById(R.id.student_profile_image);
        mStudentFirstname = (TextView) findViewById(R.id.student_profile_firstname);
        mStudentLastname = (TextView) findViewById(R.id.student_profile_lastname);
        mStudentID = (TextView) findViewById(R.id.student_profile_id);
        mStudentEmail = (TextView) findViewById(R.id.student_profile_email);
        mStudentPhone = (TextView) findViewById(R.id.student_profile_phone);
        mStudentPathway = (TextView) findViewById(R.id.student_profile_pathway);
        mStudentProfileEdit = (Button) findViewById(R.id.button_student_profile_edit);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String studentfn = sharedPreferences.getString("studentfn", mStudentFirstname.getText().toString());
        String studentln = sharedPreferences.getString("studentln", mStudentLastname.getText().toString());
        String studentid = sharedPreferences.getString("studentid", mStudentID.getText().toString());
        String studentem = sharedPreferences.getString("studentem", mStudentEmail.getText().toString());
        String studentph = sharedPreferences.getString("studentph", mStudentPhone.getText().toString());
        String studentphw = sharedPreferences.getString("studentphw", mStudentPathway.getText().toString());

        mStudentFirstname.setText(studentfn);
        mStudentLastname.setText(studentln);
        mStudentID.setText(studentid);
        mStudentEmail.setText(studentem);
        mStudentPhone.setText(studentph);
        mStudentPathway.setText(studentphw);

        mStudentProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentProfile.this, StudentProfileEdit.class);
                intent.putExtra("FirstName", mStudentFirstname.getText().toString());
                intent.putExtra("LastName", mStudentLastname.getText().toString());
                intent.putExtra("ID", mStudentID.getText().toString());
                intent.putExtra("Email", mStudentEmail.getText().toString());
                intent.putExtra("Phone", mStudentPhone.getText().toString());
                intent.putExtra("Pathway", mStudentPathway.getText().toString());
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        String headerFirstname = mStudentFirstname.getText().toString();
        String headerLastname = mStudentLastname.getText().toString();
        String headerID = mStudentID.getText().toString();
        intent.putExtra("headerFirstname", headerFirstname);
        intent.putExtra("headerLastname", headerLastname);
        intent.putExtra("headerID", headerID);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                String FirstName = data.getStringExtra("EditFirstName");
                String LastName = data.getStringExtra("EditLastName");
                String StudentID = data.getStringExtra("EditStudentID");
                String Email = data.getStringExtra("EditEmail");
                String Phone = data.getStringExtra("EditPhone");
                String Pathway = data.getStringExtra("EditPathway");

                mStudentFirstname.setText(FirstName);
                mStudentLastname.setText(LastName);
                mStudentID.setText(StudentID);
                mStudentEmail.setText(Email);
                mStudentPhone.setText(Phone);
                mStudentPathway.setText(Pathway);
            }
        }
    }

}