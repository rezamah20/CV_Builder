package com.dampyocalculator.cvbuilder.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dampyocalculator.cvbuilder.R;
import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.dampyocalculator.cvbuilder.database.usermodels;
import com.dampyocalculator.cvbuilder.detail_user.EditPekerjaanActivity;
import com.dampyocalculator.cvbuilder.detail_user.EditPendidikanActivity;

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
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pengalaman, parent, false);
        return new PengalamanViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull PengalamanViewHolder holder, int position) {
        holder.nama_pengalaman.setText(list.get(position).getNama_pengalaman());
        holder.jabatan_pengalaman.setText(list.get(position).getJabaran_pengalaman());
        holder.tgl_masuk.setText(list.get(position).getTgl_masuk_peng());
        holder.tgl_keluar.setText(list.get(position).getTgl_selesai_peng());

        holder.id_pengalaman = list.get(position).getPrimarykeypengalaman();
        holder.key_id = list.get(position).getId();

        holder.delete_pengalaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle("konfirmasi hapus ?");
                builder.setMessage("apakah Anda Yakin Ingin Menghapus ?");
                builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseHandler = new DatabaseHandler(context);
                        SQLiteDatabase delete = databaseHandler.getWritableDatabase();
                        String query = "DELETE FROM " +DatabaseHandler.table_pengalaman+ " WHERE " +DatabaseHandler.primarykey_pengalaman+ " = " +holder.id_pengalaman;
                        delete.execSQL(query);
                        Toast.makeText(activity, "Hapus berhasil!", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(activity, EditPekerjaanActivity.class);
                        myIntent.putExtra("id", list.get(0).getId());
                        activity.startActivity(myIntent);
                        activity.finish();
                    }
                });
                builder.setNegativeButton("Tidak", null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PengalamanViewHolder extends RecyclerView.ViewHolder {
        TextView nama_pengalaman, jabatan_pengalaman, tgl_masuk, tgl_keluar;
        String key_id, id_pengalaman;
        Button delete_pengalaman;



        public PengalamanViewHolder(@NonNull View v) {
            super(v);
            nama_pengalaman = (TextView) v.findViewById(R.id.tv_item_nama_pengalaman);
            jabatan_pengalaman = (TextView) v.findViewById(R.id.tv_item_jabatan_peng_txt);
            tgl_masuk = (TextView) v.findViewById(R.id.tgl_masuk_peng_txt);
            tgl_keluar = (TextView) v.findViewById(R.id.tgl_selesai_peng_txt);
            delete_pengalaman = (Button) v.findViewById(R.id.btn_delete_pengalaman);

        }
    }
}
