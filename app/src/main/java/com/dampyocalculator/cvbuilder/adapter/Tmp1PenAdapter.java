package com.dampyocalculator.cvbuilder.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dampyocalculator.cvbuilder.CreateCvActivity;
import com.dampyocalculator.cvbuilder.R;
import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.dampyocalculator.cvbuilder.database.usermodels;

import java.util.ArrayList;

public class Tmp1PenAdapter extends RecyclerView.Adapter<Tmp1PenAdapter.Tmp1PenViewHolder> {

    Activity activity;
    Context context;
    private ArrayList<usermodels> list = new ArrayList<>();

    private DatabaseHandler databaseHandler;
    RecyclerView mRecyclerView;
    View view;


    public Tmp1PenAdapter(Activity activity, Context context){
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
    public Tmp1PenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tmp1_pendidikan, parent, false);
        return new Tmp1PenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Tmp1PenViewHolder holder, int position) {
       // list = getData();
        holder.tv_namapen.setText(list.get(position).getNamasekolah());
        holder.tv_jurusan.setText(list.get(position).getNamajurusan());
        holder.tv_tahun_masuk.setText(list.get(position).getTahunmasuk());
        holder.tv_tahun_lulus.setText(list.get(position).getTahunlulus());
        holder.tv_ket_pen.setText(list.get(position).getKeteranganpendidikan());
       // Log.d("tag list", "list pendidikan = " +list.get(position).getNamasekolah());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Tmp1PenViewHolder extends RecyclerView.ViewHolder {
        TextView tv_namapen, tv_tahun_masuk, tv_tahun_lulus, tv_jurusan, tv_ket_pen;

        public Tmp1PenViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_namapen = itemView.findViewById(R.id.tmp1_tv_item_nama_sekolah);
            tv_tahun_masuk = itemView.findViewById(R.id.tmp1_tahun_masuk_txt);
            tv_tahun_lulus = itemView.findViewById(R.id.tmp1_tahun_lulus_txt);
            tv_jurusan = itemView.findViewById(R.id.tmp1_tv_item_jurusan_sekolah);
            tv_ket_pen = itemView.findViewById(R.id.tmp1_tv_item_ket_sekolah);
        }
    }


}
