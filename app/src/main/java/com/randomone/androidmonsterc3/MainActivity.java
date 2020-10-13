package com.randomone.androidmonsterc3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void onclick(){
        Toast.makeText(this, "You click me!", Toast.LENGTH_SHORT).show();
    }

    private void onclick2(){
        Toast.makeText(this, "Toast", Toast.LENGTH_SHORT).show();
    }
}