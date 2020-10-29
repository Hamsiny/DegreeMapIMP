package com.randomone.androidmonsterc3;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class StudentCreatorActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference mStorageRef;
    StorageTask mStorageTask;

    private EditText mFirstname, mLastname, mPhone, mEmail, mStudentID;
    private Button mUploadButton;
    private Spinner mPathway;
    private ImageView mProfileImage;
    private Uri mImageUri;

    private String link;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_creator);

        mFirstname = findViewById(R.id.student_firstname);
        mLastname = findViewById(R.id.student_lastname);
        mStudentID = findViewById(R.id.student_id);
        mPhone = findViewById(R.id.student_phone);
        mEmail = findViewById(R.id.student_email);
        mUploadButton = findViewById(R.id.student_upload_image);
        mProfileImage = findViewById(R.id.student_image);
        mPathway = findViewById(R.id.student_pathway);

        mStorageRef = storage.getReference("studentImages");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.pathway_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPathway.setAdapter(adapter);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Glide.with(this).load(mImageUri).centerCrop().into(mProfileImage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.create_student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_module:
                uploadStudent();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void uploadStudent() {
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mStorageTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful()) ;
                    Uri url = urlTask.getResult();
                    link = url.toString();
                    Toast.makeText(StudentCreatorActivity.this, link, Toast.LENGTH_SHORT).show();
                }
            });

            Student student = new Student(
                    mFirstname.getText().toString(),
                    mLastname.getText().toString(),
                    Integer.parseInt(mStudentID.getText().toString()),
                    mPhone.getText().toString(),
                    mEmail.getText().toString(),
                    link,
                    mPathway.getSelectedItem().toString());

            db.collection("students").add(student);
            finish();
        }

    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

}
