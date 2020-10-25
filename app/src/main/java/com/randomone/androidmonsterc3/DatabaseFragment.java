package com.randomone.androidmonsterc3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class DatabaseFragment extends Fragment {
    FirestoreRecyclerOptions<Module> options;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    int sIndex = 0;
    private TextView mSemesterText;
    private Button mSemesterNext;
    private Button mSemesterPrev;
    private RecyclerView mRecyclerView;
    private FirebaseFirestore mDatabaseRef;
    private ModuleAdapter adapter;


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

        getActivity().setTitle("Database Stream");

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
                    .setQuery(mDatabaseRef.collection("modules").whereArrayContains("pathway", "database").orderBy("time"), Module.class)
                    .build();
        } else {
            options = new FirestoreRecyclerOptions.Builder<Module>()
                    .setQuery(mDatabaseRef.collection("modules").whereArrayContains("pathway", "database").whereEqualTo("time", "S" + sIndex), Module.class)
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

        //document reference
        final String id = adapter.getSnapshots().getSnapshot(position).getReference().getId();

        DocumentReference documentRef = db.collection("modules").document(id);
        documentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot moduleDoc = task.getResult();
                if (moduleDoc.exists()){

                    //savng snapshot data to extras
                    Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
                    String code = (String) moduleDoc.get("code");
                    String title = (String) moduleDoc.get("title");
                    String description = (String) moduleDoc.get("description");
                    long creditLong = (Long) moduleDoc.get("credits");
                    String credits = Long.toString(creditLong);
                    long levelLong = (Long) moduleDoc.get("level");
                    String level = Long.toString(levelLong);            //format is long but won't convert in a single line for some reason, so i have to define it as a long first
                    String time = (String) moduleDoc.get("time");
                    List<String> pathway = new ArrayList<String>();
                    pathway = (ArrayList<String>) moduleDoc.get("pathway");
                    List<String> prerequisites = new ArrayList<String>();
                    prerequisites = (ArrayList<String>) moduleDoc.get("prerequisites");
                    List<String> corequisites = new ArrayList<String>();
                    corequisites = (ArrayList<String>) moduleDoc.get("corequisites");

                    //Sending Snapshot data as extra
                    Intent intent = new Intent(getContext(), ModuleCreatorActivity.class);
                    intent.putExtra(ModuleCreatorActivity.EXTRA_ID, id);
                    intent.putExtra(ModuleCreatorActivity.EXTRA_CODE, code);
                    intent.putExtra(ModuleCreatorActivity.EXTRA_TITLE, title);
                    intent.putExtra(ModuleCreatorActivity.EXTRA_DESCRIPTION, description);
                    intent.putExtra(ModuleCreatorActivity.EXTRA_CREDIT, credits);
                    intent.putExtra(ModuleCreatorActivity.EXTRA_LEVEL, level);
                    intent.putExtra(ModuleCreatorActivity.EXTRA_TIME, time);
                    intent.putStringArrayListExtra(ModuleCreatorActivity.EXTRA_PATHWAY, (ArrayList<String>) pathway);
                    intent.putStringArrayListExtra(ModuleCreatorActivity.EXTRA_PREREQUISITE, (ArrayList<String>) prerequisites);
                    intent.putStringArrayListExtra(ModuleCreatorActivity.EXTRA_COREQUISITE, (ArrayList<String>) corequisites);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(getContext(), "ERROR: DOCUMENT_ID_NOT_RETRIEVED", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
