package com.randomone.androidmonsterc3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StudentCreatorActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;


    private EditText mFirstname, mLastname, mPhone, mEmail;
    private Button mUploadButton;
    private ImageView mProfileImage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_creator);

        mFirstname = findViewById(R.id.student_firstname);
        mLastname = findViewById(R.id.student_lastname);
        mPhone = findViewById(R.id.student_phone);
        mEmail = findViewById(R.id.student_email);
        mUploadButton = findViewById(R.id.student_upload_image);
        mProfileImage = findViewById(R.id.student_image);

        //open file dialog for profile image
        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });


    }
}
