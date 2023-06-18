package com.dampyocalculator.cvbuilder.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dampyocalculator.cvbuilder.R;
import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.dampyocalculator.cvbuilder.database.usermodels;

import java.util.ArrayList;

public class Tmp1BahasaAdapter extends RecyclerView.Adapter<Tmp1BahasaAdapter.Tmp1BahasaViewHolder> {
    Activity activity;
    Context context;
    private ArrayList<usermodels> list = new ArrayList<>();

    private DatabaseHandler databaseHandler;
    RecyclerView mRecyclerView;
    View view;


    public Tmp1BahasaAdapter(Activity activity, Context context){
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
    public Tmp1BahasaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tmp1_bahasa, parent, false);
        return new Tmp1BahasaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Tmp1BahasaViewHolder holder, int position) {
        holder.bahasa.setText(list.get(position).getNama_bahasa());
        holder.bahasa.setText(list.get(position).getLevel_bahasa());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Tmp1BahasaViewHolder extends RecyclerView.ViewHolder {
        TextView bahasa, lvl_bahasa;
        public Tmp1BahasaViewHolder(@NonNull View itemView) {
            super(itemView);

            bahasa = itemView.findViewById(R.id.tmp1_tv_item_bahasa);
            lvl_bahasa = itemView.findViewById(R.id.level_bahasa);
        }
    }
}
