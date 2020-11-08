package com.randomone.androidmonsterc3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    public static final String MANAGER_ENTRANCE_PASSWORD = "WinITDMP01";
    final Context context = this;
    Dialog dialog;
    boolean isActivityRestarting;
    private Button mStudentEntrance;
    private Button mManagerEntrance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showDisclaimerPopup();
        mStudentEntrance = (Button) findViewById(R.id.button_entrance_student);
        mManagerEntrance = (Button) findViewById(R.id.button_entrance_manager);

        //add logo on action bar
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.androidmonsterlogo);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        mStudentEntrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ModuleActivity.class);  //todo pass Manager boolean to ModuleActivity to decide if we should use manager or student XML
                startActivity(intent);
            }
        });

        mManagerEntrance.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ManagerEntranceActivity.class);
                startActivity(intent);
            }
        }));
    }



    public void showDisclaimerPopup() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.disclaimer_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button button = (Button) dialog.findViewById(R.id.btnOK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
