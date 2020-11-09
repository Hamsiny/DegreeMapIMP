package com.randomone.androidmonsterc3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class StudentProfileEdit extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    private static final int PICK_IMAGE_REQUEST = 1;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference mStorageRef;
    StorageTask mStorageTask;

    private ImageView mStudentImage;
    private EditText mStudentFirstname, mStudentLastname, mStudentID, mStudentEmail, mStudentPhone;
    private Spinner mStudentPathway;
    private Button mUploadImage, mSaveProfile;
    private Uri mImageUri;
    private boolean imageChanged = false;
    private String link, deviceID;

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

        mStorageRef = storage.getReference("studentImages");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        deviceID = sharedPreferences.getString("deviceID", null);


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
        link = intent.getStringExtra("imageurl");

        Glide.with(getBaseContext()).load(link).centerCrop().into(mStudentImage);

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

        mUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        mSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mStudentFirstname.getText().toString().isEmpty() || mStudentLastname.getText().toString().isEmpty() ||
                        mStudentID.getText().toString().isEmpty() || mStudentEmail.getText().toString().isEmpty() ||
                        mStudentPhone.getText().toString().isEmpty()) {
                    Toast.makeText(StudentProfileEdit.this, "One or many fields are empty.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (imageChanged == false && link == "placeholder") {
                    Toast.makeText(StudentProfileEdit.this, "You must add a profile image.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
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

                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("studentfn", mStudentFirstname.getText().toString());
                    editor.putString("studentln", mStudentLastname.getText().toString());
                    editor.putString("studentid", mStudentID.getText().toString());
                    editor.putString("studentem", mStudentEmail.getText().toString());
                    editor.putString("studentph", mStudentPhone.getText().toString());
                    editor.putString("studentphw", mStudentPathway.getSelectedItem().toString());
                    editor.apply();


                    imageUpload();
                    setResult(RESULT_OK, editIntent);
                    Toast.makeText(StudentProfileEdit.this, "Student Profile Saved.", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            if (mImageUri != null) {
                link = mImageUri.toString();
                imageChanged = true;
            }
            Glide.with(this).load(mImageUri).centerCrop().into(mStudentImage);
        }
    }

    public void imageUpload(){
        if (imageChanged == false) {        //todo make it so that images can be changed in an edit
            createStudent(link);
        } else {
            if (mImageUri != null) {
                final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                        + "." + getFileExtension(mImageUri));

                mStorageTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful());
                        Uri url = urlTask.getResult();
                        link = String.valueOf(url);
                        createStudent(link);
                    }
                });
            }
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void createStudent(String link) {
        Student student = new Student(
                mStudentFirstname.getText().toString(),
                mStudentLastname.getText().toString(),
                Long.parseLong(mStudentID.getText().toString()),
                mStudentPhone.getText().toString(),
                mStudentEmail.getText().toString(),
                link,
                mStudentPathway.getSelectedItem().toString());

        db.collection("students").document(deviceID).set(student);
    }
}