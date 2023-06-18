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

public class Tmp1PengAdapter extends RecyclerView.Adapter<Tmp1PengAdapter.Tmp1PengViewHolder> {
    Activity activity;
    Context context;
    private ArrayList<usermodels> list = new ArrayList<>();

    private DatabaseHandler databaseHandler;
    RecyclerView mRecyclerView;
    View view;


    public Tmp1PengAdapter(Activity activity, Context context){
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
    public Tmp1PengViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tmp1_pengalaman, parent, false);
        return new Tmp1PengViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Tmp1PengViewHolder holder, int position) {
        holder.tv_namapeng.setText(list.get(position).getNama_pengalaman());
        holder.tv_jabatan.setText(list.get(position).getJabaran_pengalaman());
        holder.tv_tgl_mulai.setText(list.get(position).getTgl_masuk_peng());
        holder.tv_tgl_selesai.setText(list.get(position).getTgl_selesai_peng());
        holder.tv_ket_peng.setText(list.get(position).getKet_peng());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Tmp1PengViewHolder extends RecyclerView.ViewHolder {
        TextView tv_namapeng, tv_jabatan, tv_tgl_mulai, tv_tgl_selesai, tv_ket_peng;

        public Tmp1PengViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_namapeng = itemView.findViewById(R.id.tmp1_tv_item_nama_perusahaan);
            tv_jabatan = itemView.findViewById(R.id.tmp1_tv_jabatan_peng_txt);
            tv_tgl_mulai = itemView.findViewById(R.id.tmp1_tgl_masuk_perusahaan_txt);
            tv_tgl_selesai = itemView.findViewById(R.id.tmp1_tgl_keluar_perusahan_txt);
            tv_ket_peng = itemView.findViewById(R.id.tmp1_tv_item_ket_peng);
        }
    }
}
