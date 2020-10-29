package com.randomone.androidmonsterc3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FirebaseFirestore mDatabaseRef;
    private StudentAdapter adapter;

    FirestoreRecyclerOptions<Student> options;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_student, container, false);

        mRecyclerView = rootView.findViewById(R.id.student_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        mDatabaseRef = FirebaseFirestore.getInstance();

        options = new FirestoreRecyclerOptions.Builder<Student>()
                .setQuery(mDatabaseRef.collection("students").orderBy("studentID"), Student.class)
                .build();
        adapter = new StudentAdapter(options);
        mRecyclerView.setAdapter(adapter);

        getActivity().setTitle("Degree Students");

        return rootView;
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
}
