package com.dampyocalculator.cvbuilder.detail_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.dampyocalculator.cvbuilder.R;
import com.dampyocalculator.cvbuilder.adapter.PengalamanAdapter;
import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.dampyocalculator.cvbuilder.database.usermodels;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class EditPekerjaanActivity extends AppCompatActivity {
    private Button tambah_pekerjaan;
    private String id, key_id_perusaan, nama_perusahaan, tgl_mulai_bekerja, tgl_selesai_bekerja, keterangan_pengalaman;
    private TextInputEditText nama_perusahaan_et, mulai_bekerja_et, akhir_bekerja_et, keterangan_bekerja_et;
    RecyclerView rv_frag_exp_list;
    private ArrayList<usermodels> list;
    AlertDialog alertDialog;
    private DatabaseHandler db =new DatabaseHandler(this);
    private PengalamanAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pekerjaan);

        tambah_pekerjaan = (Button) findViewById(R.id.tambah_pengalaman);
        alertDialog = expFormDiaglog();


        id = getIntent().getStringExtra("id");

        adapter = new PengalamanAdapter(this,this);

        db.getReadableDatabase();
        list = getData();
        adapter.setListUser(list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EditPekerjaanActivity.this);
        rv_frag_exp_list.setLayoutManager(layoutManager);
        rv_frag_exp_list.setAdapter(adapter);


        tambah_pekerjaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private AlertDialog expFormDiaglog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.frag_pekerjaan, null);

        nama_perusahaan_et = view.findViewById(R.id.nama_perusahaan_et);
        mulai_bekerja_et = view.findViewById(R.id.mulai_bekerja_et);
        akhir_bekerja_et = view.findViewById(R.id.akhir_bekerja_et);
        keterangan_bekerja_et = view.findViewById(R.id.keterangan_bekerja_et);

        builder.setView(view);
        builder.setTitle("Tambahkan Pengalaman");
        builder.setNeutralButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }

    private void saveData(){
        SQLiteDatabase insert = db.getWritableDatabase();

        nama_perusahaan = nama_perusahaan_et.getText().toString();
        tgl_mulai_bekerja = mulai_bekerja_et.getText().toString();
        tgl_selesai_bekerja = akhir_bekerja_et.getText().toString();
        keterangan_pengalaman = keterangan_bekerja_et.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.key_id_perusahaan, id);
        contentValues.put(DatabaseHandler.nama_perusahaan, nama_perusahaan);
        contentValues.put(DatabaseHandler.tgl_mulai_bekerja, tgl_mulai_bekerja);
        contentValues.put(DatabaseHandler.tgl_selesai_bekerja, tgl_selesai_bekerja);
        contentValues.put(DatabaseHandler.ket_pengalaman, keterangan_pengalaman);

        insert.insert(DatabaseHandler.table_pengalaman, null, contentValues);

        nama_perusahaan_et.getText().clear();
        mulai_bekerja_et.getText().clear();
        akhir_bekerja_et.getText().clear();
        keterangan_bekerja_et.getText().clear();

        list = getData();
        adapter.setListUser(list);
        adapter.notifyDataSetChanged();

    }

    private ArrayList<usermodels> getData(){
        SQLiteDatabase ReadData = db.getReadableDatabase();
        ArrayList<usermodels> arrayList = new ArrayList<>();

        Cursor c = ReadData.rawQuery("SELECT "+DatabaseHandler.primarykey_pengalaman+", "
                + DatabaseHandler.table_pengalaman+"."+DatabaseHandler.key_id_perusahaan+", "
                + DatabaseHandler.nama_perusahaan+", "
                + DatabaseHandler.tgl_mulai_bekerja+", "
                + DatabaseHandler.tgl_selesai_bekerja+", "
                + DatabaseHandler.ket_pengalaman+
                " FROM " + DatabaseHandler.table_pengalaman+
                " INNER JOIN " + DatabaseHandler.table_pengalaman+ " on " + DatabaseHandler.table_pengalaman +"."+DatabaseHandler.key_id+ " = " + DatabaseHandler.table_user+"."+DatabaseHandler.key_id+
                " WHERE " +DatabaseHandler.table_user+"."+DatabaseHandler.key_id+ " = " +id, null);

        if (c.moveToFirst()){
            do {
                usermodels usermodels = new usermodels();

                usermodels.setKeyidpengalaman(c.getString(0));
                usermodels.setNama_pengalaman(c.getString(1));
                usermodels.setTgl_masuk_peng(c.getString(2));
                usermodels.setTgl_selesai_peng(c.getString(3));
                usermodels.setKet_peng(c.getString(4));

                arrayList.add(usermodels);

            }while (c.moveToNext());
        }
        return arrayList;
    }



}