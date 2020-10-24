package com.randomone.androidmonsterc3;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class CoreFragment extends Fragment {
    private TextView mSemesterText;
    private Button mSemesterNext;
    private Button mSemesterPrev;

    private RecyclerView mRecyclerView;
    private FirebaseFirestore mDatabaseRef;
    private ModuleAdapter adapter;
    FirestoreRecyclerOptions<Module> options;


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

        mDatabaseRef = FirebaseFirestore.getInstance();

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
            adapter.updateOptions(options);
        } else {
            Toast.makeText(getContext(), "Negative Semesters don't exist at Wintec!", Toast.LENGTH_SHORT).show();
        }
    }

    public void nextSemester(View view) {
        if (sIndex < 6) {
            sIndex++;
            mSemesterText.setText(semesterText[sIndex]);
            refreshRecycler();
            adapter.updateOptions(options);
        } else {
            Toast.makeText(getContext(), "This is your final Semester!", Toast.LENGTH_SHORT).show();
        }
    }

    public FirestoreRecyclerOptions<Module> refreshRecycler() {

        if (sIndex == 0){
            options = new FirestoreRecyclerOptions.Builder<Module>()
                    .setQuery(mDatabaseRef.collection("modules").whereArrayContains("pathway", "core").orderBy("time"), Module.class)
                    .build();
    }
        else {
            options = new FirestoreRecyclerOptions.Builder<Module>()
                    .setQuery(mDatabaseRef.collection("modules").whereArrayContains("pathway", "core").whereEqualTo("time", "S" + sIndex), Module.class)
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

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(), R.color.editGreen))
                        .addSwipeRightLabel("EDIT")
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.deleteRed))
                        .addSwipeLeftLabel("DELETE")
                        .create().decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }
}
