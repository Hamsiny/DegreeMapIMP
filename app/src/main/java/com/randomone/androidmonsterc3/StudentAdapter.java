package com.randomone.androidmonsterc3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class StudentAdapter extends FirestoreRecyclerAdapter<Student, StudentAdapter.StudentViewholder>  {


    public StudentAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }

    @NonNull
    @Override
    public StudentAdapter.StudentViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new StudentAdapter.StudentViewholder(view);
    }


    private int expandedItemIndex = -1;
    @Override
    protected void onBindViewHolder(@NonNull StudentViewholder holder, int position, @NonNull Student model) {
        holder.name.setText(model.getfName() + " " + model.getlName());
        holder.email.setText(model.getEmail());
        holder.phone.setText(model.getPhone());
        holder.pathway.setText(model.getPathway());
        holder.id.setText(String.valueOf(model.getStudentID()));
        Glide.with(holder.itemView.getContext()).load(model.getPhotoURL()).centerCrop().into(holder.image);
    }

    public class StudentViewholder extends RecyclerView.ViewHolder {
        TextView name, id, email, phone, pathway;
        ImageView image;

        public StudentViewholder(@NonNull View view) {
            super(view);

            name = view.findViewById(R.id.student_recycler_name);
            id = view.findViewById(R.id.student_recycler_id);
            image = view.findViewById(R.id.student_recycler_photo);
            email = view.findViewById(R.id.student_recycler_email);
            phone = view.findViewById(R.id.student_recycler_phone);
            pathway = view.findViewById(R.id.student_recycler_pathway);

        }
    }

    public void deleteStudent(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }
}
