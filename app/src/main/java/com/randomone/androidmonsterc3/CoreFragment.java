package com.randomone.androidmonsterc3;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class CoreFragment extends Fragment {

    FirestoreRecyclerOptions<Module> options;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    int sIndex = 0;
    private TextView mSemesterText;
    private Button mSemesterNext;
    private Button mSemesterPrev;
    private RecyclerView mRecyclerView;
    private FirebaseFirestore mDatabaseRef;
    private ModuleAdapter adapter;

    public static final String EXTRA_ID = "com.randomone.androidmonsterc3.EXTRA_ID";
    public static final String EXTRA_CODE = "com.randomone.androidmonsterc3.EXTRA_CODE";
    public static final String EXTRA_TITLE = "com.randomone.androidmonsterc3.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.randomone.androidmonsterc3.EXTRA_DESCRIPTION";
    public static final String EXTRA_CREDIT = "com.randomone.androidmonsterc3.EXTRA_CREDIT";
    public static final String EXTRA_LEVEL = "com.randomone.androidmonsterc3.EXTRA_LEVEL";
    public static final String EXTRA_PATHWAY = "com.randomone.androidmonsterc3.EXTRA_PATHWAY";
    public static final String EXTRA_TIME = "com.randomone.androidmonsterc3.EXTRA_TIME";
    public static final String EXTRA_PREREQUISITE = "com.randomone.androidmonsterc3.EXTRA_PREREQUISITE";
    public static final String EXTRA_COREQUISITE = "com.randomone.androidmonsterc3.EXTRA_COREQUISITE";

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

        if (sIndex == 0) {
            options = new FirestoreRecyclerOptions.Builder<Module>()
                    .setQuery(mDatabaseRef.collection("modules").whereArrayContains("pathway", "core").orderBy("time"), Module.class)
                    .build();
        } else {
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

                final int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    deleteDialog(position);
                    adapter.notifyDataSetChanged();
                }
                if (direction == ItemTouchHelper.RIGHT) {
                    Toast.makeText(getContext(), "TEST TOAST", Toast.LENGTH_SHORT).show();
                    editModule(position);
                    adapter.notifyDataSetChanged();
                }

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

    //method asks user for delete confirmation, and passes viewholder position to adapter if yes
    private void deleteDialog(final int position){
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Module?")
                .setMessage("This will permanently remove this module from every device.")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.deleteModule(position);
                        Toast.makeText(getContext(), "Module Deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Deletion Cancelled", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }).show();
    }


    //creating a document snapshot with the provided reference ID, then getting the data to use in EXTRA's for activity intent.
    private void editModule(final int position) {
        String id = adapter.getSnapshots().getSnapshot(position).getReference().getId();
        DocumentReference documentRef = db.collection("modules").document(id);
        documentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot moduleDoc = task.getResult();
                if (moduleDoc.exists()){
                    Toast.makeText(getContext(), "DOCUMENT WORKING", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
