package com.randomone.androidmonsterc3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleCreatorActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.randomone.androidmonsterc3.EXTRA_ID";
    public static final String EXTRA_CODE = "com.randomone.androidmonsterc3.EXTRA_CODE";
    public static final String EXTRA_TITLE = "com.randomone.androidmonsterc3.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.randomone.androidmonsterc3.EXTRA_DESCRIPTION";
    public static final String EXTRA_CREDIT = "com.randomone.androidmonsterc3.EXTRA_CREDIT";
    public static final String EXTRA_LEVEL = "com.randomone.androidmonsterc3.EXTRA_LEVEL";
    public static final String EXTRA_PATHWAY = "com.randomone.androidmonsterc3.EXTRA_PATHWAY";
    public static final String EXTRA_TIME = "com.randomone.androidmonsterc3.EXTRA_TIME";
    public static final String EXTRA_PREREQUISITE = "com.randomone.androidmonsterc3.EXTRA_PREREQUISITE";
    public static final String EXTRA_COREQUISITE = "com.randomone.androidmonsterc3.EXTRA_COREQUISITE";

    private EditText codeInput, titleInput, descriptionInput, creditsInput, levelInput, prerequisiteInput, corequisiteInput;
    private Spinner semesterInput;
    private CheckBox coreBox, softwareBox, networkBox, webBox, databaseBox;
    private String time;
    private List<String> pathway = new ArrayList<String>();
    private List<String> prerequisites = new ArrayList<String>();
    private List<String> corequisites = new ArrayList<String>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "ModuleCreatorActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_creator);

        codeInput = findViewById(R.id.code_input);
        titleInput = findViewById(R.id.title_input);
        descriptionInput = findViewById(R.id.desc_input);
        creditsInput = findViewById(R.id.credits_input);
        levelInput = findViewById(R.id.level_input);
        prerequisiteInput = findViewById(R.id.prerequisite_input);
        corequisiteInput = findViewById(R.id.corequisite_input);
        semesterInput = findViewById(R.id.time_spinner);
        coreBox = findViewById(R.id.core_box);
        softwareBox = findViewById(R.id.software_box);
        networkBox = findViewById(R.id.network_box);
        webBox = findViewById(R.id.web_box);
        databaseBox = findViewById(R.id.database_box);

        //making semester spinner functional
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.time_picker_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterInput.setAdapter(adapter);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Module");
            codeInput.setText(intent.getStringExtra(EXTRA_CODE));
            titleInput.setText(intent.getStringExtra(EXTRA_TITLE));
            descriptionInput.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            creditsInput.setText(intent.getStringExtra(EXTRA_CREDIT));
            levelInput.setText(intent.getStringExtra(EXTRA_LEVEL));

            //setting spinner based on time
            switch (intent.getStringExtra(EXTRA_TIME)){
                case "S1":
                    semesterInput.setSelection(0);
                    break;
                case "S2":
                    semesterInput.setSelection(1);
                    break;
                case "S3":
                    semesterInput.setSelection(2);
                    break;
                case "S4":
                    semesterInput.setSelection(3);
                    break;
                case "S5":
                    semesterInput.setSelection(4);
                    break;
                case "S6":
                    semesterInput.setSelection(5);
                    break;
            }

            loop: for (String pathwayItem : intent.getStringArrayListExtra(EXTRA_PATHWAY)) {
                switch (pathwayItem){
                    case "core":
                        coreBox.setChecked(true);
                        break loop;
                    case "software":
                        softwareBox.setChecked(true);
                        break;
                    case "networking":
                        networkBox.setChecked(true);
                        break;
                    case "database":
                        databaseBox.setChecked(true);
                        break;
                    case "web":
                        webBox.setChecked(true);
                        break;
                }
            }

            ArrayList<String> prerequisiteCheck = intent.getStringArrayListExtra(EXTRA_PREREQUISITE);
            ArrayList<String> corequisiteCheck = intent.getStringArrayListExtra(EXTRA_COREQUISITE);
            if ( prerequisiteCheck != null ) {
                if (!prerequisiteCheck.isEmpty()) {
                    String prerequisiteLine = String.join(", ", prerequisiteCheck);
                    prerequisiteInput.setText(prerequisiteLine);
                }
            }
            if ( corequisiteCheck != null ) {
                if (!corequisiteCheck.isEmpty()) {
                    String corequisiteLine = String.join(", ", corequisiteCheck);
                    corequisiteInput.setText(corequisiteLine);
                }
            }
       }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.create_module_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_module:
                uploadModule();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //CORE is in all pathways, therefore no other pathways can be selected while it is selected
    public void coreBox(View view) {
        if (coreBox.isChecked()) {
            softwareBox.setChecked(false);
            networkBox.setChecked(false);
            webBox.setChecked(false);
            databaseBox.setChecked(false);

        }
    }
    //Disables core when specific streams are selected
    public void specificBox(View view) {
        if (softwareBox.isChecked() || networkBox.isChecked() || webBox.isChecked() || databaseBox.isChecked()) {
            coreBox.setChecked(false);
        }
    }

    public void uploadModule() {
        //turns the spinner text into a position that gets turned into a valid semester time (e.g. Semester 1 = S1)
        int semesterPosition = semesterInput.getSelectedItemPosition();
        String[] semesterActual = getResources().getStringArray(R.array.semester_array);
        time = String.valueOf(semesterActual[semesterPosition]);

        //uses the checkboxes to determine what pathways to set (core = core + all)
        if (!pathway.isEmpty()){            //todo check if the EditText boxes are empty
            pathway.clear();
        }
        if(coreBox.isChecked()) {
            pathway.add("core");
            pathway.add("database");
            pathway.add("networking");
            pathway.add("software");
            pathway.add("web");
        }
        if (databaseBox.isChecked()) {
            pathway.add("database");
        }
        if (networkBox.isChecked()) {
            pathway.add("networking");
        }
        if (softwareBox.isChecked()) {
            pathway.add("software");
        }
        if (webBox.isChecked()) {
            pathway.add("web");
        }


        String raw = prerequisiteInput.getText().toString();
        if (raw != null && !raw.trim().isEmpty()){
           String[] preprerequisites = raw.split("\\s*,\\s*");
           prerequisites = Arrays.asList(preprerequisites);

        }

        raw = corequisiteInput.getText().toString();
        if (raw != null && !raw.trim().isEmpty()) {
            String[] precorequisites = raw.split("\\s*,\\s*");
            corequisites = Arrays.asList(precorequisites);
        }

        Module module = new Module(
                codeInput.getText().toString(),
                titleInput.getText().toString(),
                descriptionInput.getText().toString(),
                time,
                Integer.parseInt(creditsInput.getText().toString()),
                Integer.parseInt(levelInput.getText().toString()),
                pathway,
                prerequisites,
                corequisites);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            db.collection("modules").document(intent.getStringExtra(EXTRA_ID)).set(module);
        }
        else {
            db.collection("modules").add(module);
        }
        finish();
    }
}
