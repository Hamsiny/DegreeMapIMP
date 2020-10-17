package com.randomone.androidmonsterc3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String MANAGER_ENTRANCE_PASSWORD = "WinITDMP01";

    final Context context = this;
    private Button mStudentEntrance;
    private Button mManagerEntrance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStudentEntrance = (Button) findViewById(R.id.button_entrance_student);
        mManagerEntrance = (Button) findViewById(R.id.button_entrance_manager);

        /*mManagerEntrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get popup layout view
                LayoutInflater li = LayoutInflater.from(context);
                View popupView = li.inflate(R.layout.manager_password_popup, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set popup layout to alertdialog builder
                alertDialogBuilder.setView(popupView);

                final EditText userInput = (EditText) popupView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and check the password is right or not
                                        String inputPass = userInput.getText().toString();
                                        if (inputPass.equalsIgnoreCase(MANAGER_ENTRANCE_PASSWORD)){
                                            Intent intent = new Intent(MainActivity.this, ManagerModulesActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(context, "Wrong password, please try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });*/

        mManagerEntrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ManagerEntranceActivity.class);
                startActivity(intent);
            }
        });

        mStudentEntrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StudentModulesActivity.class);
                startActivity(intent);
            }
        });

    }


}