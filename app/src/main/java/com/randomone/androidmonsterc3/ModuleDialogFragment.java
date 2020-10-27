package com.randomone.androidmonsterc3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ModuleDialogFragment extends DialogFragment {

    private TextView mCode, mTitle, mDescription, mTime, mCredits, mLevel, mPathway, mPrerequisites, mCorequisites;

    static ModuleDialogFragment newInstance(String id) {
        ModuleDialogFragment frag = new ModuleDialogFragment();
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
