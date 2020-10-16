package com.randomone.androidmonsterc3;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class ModuleAdapter extends FirebaseRecyclerAdapter<Module, ModuleAdapter.moduleViewholder> {

    @Override
    protected void onBindViewHolder(@NonNull ModuleAdapter.moduleViewholder holder, int position, @NonNull Module model) {

    }

    @NonNull
    @Override
    public ModuleAdapter.moduleViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public class moduleViewholder {
    }
}
