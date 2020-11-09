package com.randomone.androidmonsterc3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;

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
    private Uri imageLink;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        final String studentimg = sharedPreferences.getString("studentimg", "placeholder");

        final String deviceID = sharedPreferences.getString("deviceID", null);

        mStudentFirstname.setText(studentfn);
        mStudentLastname.setText(studentln);
        mStudentID.setText(studentid);
        mStudentEmail.setText(studentem);
        mStudentPhone.setText(studentph);
        mStudentPathway.setText(studentphw);

        if (studentimg == "placeholder"){
            mStudentImage.setImageResource(R.drawable.ic_placeholder);
        }
        else {
            Glide.with(getBaseContext()).load(studentimg).centerCrop().into(mStudentImage);
        }


        if (internetCheck()) {       //if device has internet pull student from firestore
            final DocumentReference documentReference = db.collection("students").document(deviceID);
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot studentProfile = task.getResult();
                    if (studentProfile.exists()) {
                        mStudentFirstname.setText(studentProfile.getString("fName"));
                        mStudentLastname.setText(studentProfile.getString("lName"));
                        long idLong = studentProfile.getLong("studentID");
                        mStudentID.setText(Long.toString(idLong));
                        mStudentEmail.setText(studentProfile.getString("email"));
                        mStudentPhone.setText(studentProfile.getString("phone"));
                        mStudentPathway.setText(studentProfile.getString("pathway"));

                        String imageLink = studentProfile.getString("photoURL");        //setting image URI to the one in sharedprefs
                        Glide.with(getBaseContext()).load(imageLink).centerCrop().into(mStudentImage);

                        //updating shared prefs from firestore
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("studentfn", studentProfile.getString("fName"));
                        editor.putString("studentln", studentProfile.getString("lName"));
                        editor.putString("studentid", Long.toString(idLong));
                        editor.putString("studentem", studentProfile.getString("email"));
                        editor.putString("studentph", studentProfile.getString("fName"));
                        editor.putString("studentphw",  studentProfile.getString("pathway"));
                        //todo save image from firebase to local and set as shared pref
                        editor.apply();
                    }
                    else {
                        Toast.makeText(StudentProfile.this, "Your profile is missing from the remote server! Please contact your tutor if you are still a current student.", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }


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
                if (studentimg != "placeholder") {
                    intent.putExtra("imageurl", studentimg);
                }
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
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
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

    public boolean internetCheck() {
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            Log.e("isInternetAvailable:", e.toString());
            return false;
        }
    }

}