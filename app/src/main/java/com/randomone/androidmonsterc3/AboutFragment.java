package com.randomone.androidmonsterc3;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AboutFragment extends Fragment {
    public static final String OS_VERSION = Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName(); //gets the SDK INT, and cross references it with the version name assigned to the VERSION_CODE
    public static final String API_LEVEL = Build.VERSION.RELEASE;


    private TextView mApiLevel;
    private TextView mOSVersion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mOSVersion = getView().findViewById(R.id.os_name);
        mOSVersion.setText("Android " + OS_VERSION);

        mApiLevel = getView().findViewById(R.id.api_name);
        mApiLevel.setText(API_LEVEL);
    }
}
