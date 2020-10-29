package com.randomone.androidmonsterc3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class StudentFragment extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirestoreRecyclerOptions<Student> options;
    private RecyclerView mRecyclerView;
    private FirebaseFirestore mDatabaseRef;
    private StudentAdapter adapter;

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
                    editStudent(position);
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

    private void deleteDialog(final int position) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Student?")
                .setMessage("This will permanently remove this student from the DegreeHelper database.")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.deleteStudent(position);
                        Toast.makeText(getContext(), "Student Deleted", Toast.LENGTH_SHORT).show();
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

    private void editStudent(final int position) {
        final String id = adapter.getSnapshots().getSnapshot(position).getReference().getId();     //todo refill recycler after image change

        DocumentReference documentRef = db.collection("students").document(id);
        documentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot studentDoc = task.getResult();
                if (studentDoc.exists()) {

                    Student student = studentDoc.toObject(Student.class);
                    Intent intent = new Intent(getContext(), StudentCreatorActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("student", student);
                    startActivity(intent);
                }
            }
        });
    }
}
