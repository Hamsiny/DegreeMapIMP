package com.randomone.androidmonsterc3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CoreFragment extends Fragment {
    private TextView mSemesterText;
    private Button mSemesterNext;
    private Button mSemesterPrev;

    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseRef;
    private ModuleAdapter adapter;
    FirebaseRecyclerOptions<Module> options;

    int sIndex = 0;

    String[] semesterText = {
            "All Semesters",
            "Semester 1",
            "Semester 2",
            "Semester 3",
            "Semester 4",
            "Semester 5",
            "Semester 6",
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_core, container, false);     //replaces return statement

        mSemesterPrev = rootView.findViewById(R.id.arrow_left_semester_select);
        mSemesterNext = rootView.findViewById(R.id.arrow_right_semester_select);
        mSemesterText = rootView.findViewById(R.id.text_view_semester_select);

        mSemesterPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevSemester(view);
            }
        });

        mSemesterNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextSemester(view);
            }
        });


        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("modules");

        options = refreshRecycler();
        adapter = new ModuleAdapter(options);
        mRecyclerView.setAdapter(adapter);

        return rootView;
    }

    public void prevSemester(View view) {
        if (sIndex > 0) {
            sIndex--;
            mSemesterText.setText(semesterText[sIndex]);
            refreshRecycler();
        } else {
            Toast.makeText(getContext(), "Negative Semesters don't exist at Wintec!", Toast.LENGTH_SHORT).show();
        }
    }

    public void nextSemester(View view) {
        if (sIndex < 6) {
            sIndex++;
            mSemesterText.setText(semesterText[sIndex]);
            refreshRecycler();
        } else {
            Toast.makeText(getContext(), "This is your final Semester!", Toast.LENGTH_SHORT).show();
        }
    }

    public FirebaseRecyclerOptions<Module> refreshRecycler() {

        if (sIndex == 0)
            options = new FirebaseRecyclerOptions.Builder<Module>()
                    .setQuery(mDatabaseRef.orderByChild("time").startAt("S1"), Module.class)
                    .build();
        else {
            options = new FirebaseRecyclerOptions.Builder<Module>()
                    .setQuery(mDatabaseRef.orderByChild("time").equalTo("S" + sIndex), Module.class)
                    .build();
        }
        return options;


    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
