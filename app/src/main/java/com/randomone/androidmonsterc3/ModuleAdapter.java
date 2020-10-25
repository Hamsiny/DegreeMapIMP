package com.randomone.androidmonsterc3;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ModuleAdapter extends FirestoreRecyclerAdapter<Module, ModuleAdapter.ModuleViewholder> {

    public ModuleAdapter(@NonNull FirestoreRecyclerOptions<Module> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ModuleAdapter.ModuleViewholder holder, int position, @NonNull Module model) {
        holder.code.setText(model.getCode());
        holder.name.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        holder.level.setText("Level " + model.getLevel());
        holder.credits.setText(model.getCredits() + " Credits");

    }

    @NonNull
    @Override
    public ModuleAdapter.ModuleViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_card, parent, false);
        return new ModuleAdapter.ModuleViewholder(view);
    }

    public class ModuleViewholder extends RecyclerView.ViewHolder {
        TextView name, code, description, level, credits;
        public ModuleViewholder(@NonNull View view){
            super(view);

            name = view.findViewById(R.id.module_name);
            code = view.findViewById(R.id.module_code);
            description = view.findViewById(R.id.module_description);
            level = view.findViewById(R.id.module_level);
            credits = view.findViewById(R.id.module_credits);
        }
    }

    public void deleteModule(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }
}
