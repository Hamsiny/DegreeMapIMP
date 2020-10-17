package com.randomone.androidmonsterc3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ManagerEntranceActivity extends AppCompatActivity {
    private static final String MANAGER_ENTRANCE_PASSWORD = "WinITDMP01";

    private Button mManagerLogin;
    private EditText mEditTextPassword;
    private TextView mTextViewStu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_entrance);

        mManagerLogin = findViewById(R.id.button_manager_login);
        mEditTextPassword = findViewById(R.id.edit_text_password);
        mTextViewStu = findViewById(R.id.text_view_student_module);

        mTextViewStu.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTextViewStu.getPaint().setAntiAlias(true);

        mManagerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordManagerInput = mEditTextPassword.getText().toString();
                if (passwordManagerInput.equalsIgnoreCase(MANAGER_ENTRANCE_PASSWORD)){
                    Intent intent = new Intent(ManagerEntranceActivity.this, ManagerModulesActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(ManagerEntranceActivity.this, "Wrong Password, please try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mTextViewStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerEntranceActivity.this, StudentModulesActivity.class);
                startActivity(intent);
            }
        });
    }
}