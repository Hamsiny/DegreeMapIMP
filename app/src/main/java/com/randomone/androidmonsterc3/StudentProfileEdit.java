package com.randomone.androidmonsterc3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class StudentProfileEdit extends AppCompatActivity {
    private ImageView mStudentImage;
    private EditText mStudentFirstname, mStudentLastname, mStudentID, mStudentEmail, mStudentPhone;
    private Spinner mStudentPathway;
    private Button mUploadImage, mSaveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_edit);

        mStudentImage = (ImageView) findViewById(R.id.student_image_edit);
        mStudentFirstname = (EditText) findViewById(R.id.student_firstname_edit);
        mStudentLastname = (EditText) findViewById(R.id.student_lastname_edit);
        mStudentID = (EditText) findViewById(R.id.student_id_edit);
        mStudentEmail = (EditText) findViewById(R.id.student_email_edit);
        mStudentPhone = (EditText) findViewById(R.id.student_phone_edit);
        mStudentPathway = (Spinner) findViewById(R.id.student_pathway_edit);
        mUploadImage = (Button) findViewById(R.id.student_upload_image_edit);
        mSaveProfile = (Button) findViewById(R.id.student_save_edit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.pathway_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStudentPathway.setAdapter(adapter);

        Intent intent = getIntent();
        String FirstName = intent.getStringExtra("FirstName");
        String LastName = intent.getStringExtra("LastName");
        String StudentID = intent.getStringExtra("ID");
        String Email = intent.getStringExtra("Email");
        String Phone = intent.getStringExtra("Phone");
        String Pathway = intent.getStringExtra("Pathway");

        mStudentFirstname.setText(FirstName);
        mStudentLastname.setText(LastName);
        mStudentID.setText(StudentID);
        mStudentEmail.setText(Email);
        mStudentPhone.setText(Phone);

        switch (Pathway) {
            case "Undecided":
                mStudentPathway.setSelection(0);
                break;
            case "Software Engineering":
                mStudentPathway.setSelection(1);
                break;
            case "Network Engineering":
                mStudentPathway.setSelection(2);
                break;
            case "Database Architecture":
                mStudentPathway.setSelection(3);
                break;
            case "Web Development":
                mStudentPathway.setSelection(4);
                break;
        }

        mSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStudentFirstname.getText().toString().isEmpty() || mStudentLastname.getText().toString().isEmpty() ||
                        mStudentID.getText().toString().isEmpty() || mStudentEmail.getText().toString().isEmpty() ||
                        mStudentPhone.getText().toString().isEmpty()){
                    Toast.makeText(StudentProfileEdit.this, "One or many fields are empty.", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    String editFirstName = mStudentFirstname.getText().toString();
                    String editLastName = mStudentLastname.getText().toString();
                    String editStudentID = mStudentID.getText().toString();
                    String editEmail = mStudentEmail.getText().toString();
                    String editPhone = mStudentPhone.getText().toString();
                    String editPathway = mStudentPathway.getSelectedItem().toString();

                    Intent editIntent = new Intent();
                    editIntent.putExtra("EditFirstName", editFirstName);
                    editIntent.putExtra("EditLastName", editLastName);
                    editIntent.putExtra("EditStudentID", editStudentID);
                    editIntent.putExtra("EditEmail", editEmail);
                    editIntent.putExtra("EditPhone", editPhone);
                    editIntent.putExtra("EditPathway", editPathway);
                    
                    setResult(RESULT_OK, editIntent);
                    Toast.makeText(StudentProfileEdit.this, "Student Profile Saved.", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });




    }
}