package com.randomone.androidmonsterc3;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class ModuleDialogFragment extends DialogFragment {
    private TextView mCode, mTitle, mDescription, mTime, mCredits, mLevel, mPathway, mPrerequisites, mCorequisites;
    private Module mModule;
    private ImageButton mExit;

    static ModuleDialogFragment newInstance(Module module) {
        ModuleDialogFragment frag = new ModuleDialogFragment();
        return frag;
    }

    @Override
    public void onStart() {
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent); //this removes white corners to the dialog

        //getting the device resolution and scaling them to the ideal size
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getRealSize(size);
        int screenHeight = size.y;
        int screenWidth = size.x;

        int dialogWidth = 1200;     //4k devices look bad if they use the formula so i hardcoded this for big screens
        int dialogHeight = 1800;

        if (screenHeight < 3500 || screenWidth < 1600) {
            dialogWidth = (int) (screenWidth/1.2);
            dialogHeight = (int) (screenHeight/1.7);
        }


        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);   //this sets the size of the dialogbox
        super.onStart();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment, container);
        mCode = rootView.findViewById(R.id.code);
        mTitle = rootView.findViewById(R.id.title_popup);
        mDescription = rootView.findViewById(R.id.desc_popup);
        mTime = rootView.findViewById(R.id.time_popup);
        mCredits = rootView.findViewById(R.id.credits_popup);
        mLevel = rootView.findViewById(R.id.level_popup);
        mPathway = rootView.findViewById(R.id.pathway_popup);
        mPrerequisites = rootView.findViewById(R.id.prereq_popup);
        mCorequisites = rootView.findViewById(R.id.coreq_popup);
        mExit = rootView.findViewById(R.id.close_button);

        mModule = getArguments().getParcelable("module");
        mCode.setText(mModule.getCode().toString());
        mTitle.setText(mModule.getTitle().toString());
        mDescription.setText(mModule.getDescription().toString());
        String time = (mModule.getTime().toString());

        switch (time) {
            case "S1":
                mTime.setText("Semester 1");
                break;
            case "S2":
                mTime.setText("Semester 2");
                break;
            case "S3":
                mTime.setText("Semester 3");
                break;
            case "S4":
                mTime.setText("Semester 4");
                break;
            case "S5":
                mTime.setText("Semester 5");
                break;
            case "S6":
                mTime.setText("Semester 6");
                break;
        }

        mCredits.setText(Long.toString(mModule.getCredits()));
        mLevel.setText(Long.toString(mModule.getLevel()));

        ArrayList<String> pathway = (ArrayList<String>) mModule.getPathway();
        ArrayList<String> prerequisiteCheck = (ArrayList<String>) mModule.getPrerequisites();
        ArrayList<String> corequisiteCheck = (ArrayList<String>) mModule.getCorequisite();

        if (pathway.contains("core")) {
            mPathway.setText("Core");
        }
        else{
            String pathwayLine = String.join(", ", pathway);
            mPathway.setText(pathwayLine);
        }

        if (prerequisiteCheck != null) {
            if (!prerequisiteCheck.isEmpty()) {
                String prerequisiteLine = String.join(", ", prerequisiteCheck);
                mPrerequisites.setText(prerequisiteLine);
            }
        }

        if (corequisiteCheck != null) {
            if (!corequisiteCheck.isEmpty()) {
                String corequisiteLine = String.join(", ", corequisiteCheck);
                mCorequisites.setText(corequisiteLine);
            }
        }
        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
