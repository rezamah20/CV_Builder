package com.dampyocalculator.cvbuilder.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.dampyocalculator.cvbuilder.database.usermodels;

import java.util.ArrayList;

public class PengalamanAdapter extends RecyclerView.Adapter<PengalamanAdapter.PengalamanViewHolder> {

    Activity activity;
    Context context;
    private ArrayList<usermodels> list = new ArrayList<>();
    private DatabaseHandler databaseHandler;
    RecyclerView mRecyclerView;


    public PengalamanAdapter(Activity activity, Context context){
        this.activity = activity;
        this.context = context;
    }
    public void setListUser(ArrayList<usermodels> listNotes){
        if (listNotes.size() > 0){
            this.list.clear();
        }
        this.list.addAll(listNotes);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PengalamanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PengalamanViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PengalamanViewHolder extends RecyclerView.ViewHolder {
        public PengalamanViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
