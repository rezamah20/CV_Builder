package com.dampyocalculator.cvbuilder.detail_user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.dampyocalculator.cvbuilder.R;
import com.dampyocalculator.cvbuilder.adapter.AdsAdapter;
import com.dampyocalculator.cvbuilder.adapter.ForbidenCharAdapter;
import com.dampyocalculator.cvbuilder.adapter.PendidikanAdapter;
import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.dampyocalculator.cvbuilder.database.usermodels;
import com.dampyocalculator.cvbuilder.list_user;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class EditPendidikanActivity extends AppCompatActivity {

    private Button add_pendidikan;
    private String id, nama_sekolah, nama_jurusan, tahun_masuk, tahun_lulus, keterangan_pendidikan;
    private TextInputEditText nama_sekolah_et, nama_jurusan_et,tahun_masuk_et, tahun_lulus_et, keterangan_pendidikan_et;
    RecyclerView rv_frag_exp_list;
    private ArrayList<usermodels> list;
    AlertDialog alertDialog;
    private DatabaseHandler db =new DatabaseHandler(this);
    private PendidikanAdapter adapter;
    AdsAdapter adsAdapter = new AdsAdapter(this, this);;
    ForbidenCharAdapter forbidenCharAdapter =new ForbidenCharAdapter(this, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pendidikan);
        alertDialog = expFormDiaglog();

        adsAdapter.loadbanner();

        rv_frag_exp_list =findViewById(R.id.rview_pendidikan);
        add_pendidikan = (Button) findViewById(R.id.tambah_pendidikan);

        id = getIntent().getStringExtra("id");

        adapter = new PendidikanAdapter(this, this);

        db.getReadableDatabase();
        list = getData();
        adapter.setListUser(list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EditPendidikanActivity.this);
        rv_frag_exp_list.setLayoutManager(layoutManager);
        rv_frag_exp_list.setAdapter(adapter);


        add_pendidikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    alertDialog.show();
            }
        });

    }

    protected void onResume() {
        super.onResume();
        list = getData();
        adapter.setListUser(list);
        adapter.notifyDataSetChanged();
    }

    private AlertDialog expFormDiaglog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.frag_pendidikan, null);

        nama_sekolah_et = view.findViewById(R.id.nama_sekolah_et);
        nama_jurusan_et = view.findViewById(R.id.nama_jurusan_et);
        tahun_masuk_et = view.findViewById(R.id.tahun_masuk_et);
        tahun_lulus_et = view.findViewById(R.id.tahun_lulus_et);
        keterangan_pendidikan_et = view.findViewById(R.id.keterangan_pendidikan_et);

        //forbiden character
        nama_sekolah_et.setFilters(new InputFilter[] { forbidenCharAdapter });
        nama_jurusan_et.setFilters(new InputFilter[] { forbidenCharAdapter });
        tahun_masuk_et.setFilters(new InputFilter[] { forbidenCharAdapter });
        tahun_lulus_et.setFilters(new InputFilter[] { forbidenCharAdapter });
        keterangan_pendidikan_et.setFilters(new InputFilter[] { forbidenCharAdapter });



        builder.setView(view);

        builder.setTitle("Tambahkan Pekerjaan");
        builder.setNeutralButton("Batalkan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT);
            }
        });
        builder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveData();
            }
        });
        return builder.create();
    }

    private void saveData(){
        SQLiteDatabase insert = db.getReadableDatabase();

        nama_sekolah = nama_sekolah_et.getText().toString();
        nama_jurusan = nama_jurusan_et.getText().toString();
        tahun_masuk = tahun_masuk_et.getText().toString();
        tahun_lulus = tahun_lulus_et.getText().toString();
        keterangan_pendidikan = keterangan_pendidikan_et.getText().toString();

        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.key_id_pendidikan, id);
        values.put(DatabaseHandler.key_nama_sekolah, nama_sekolah);
        values.put(DatabaseHandler.key_nama_jurusan, nama_jurusan);
        values.put(DatabaseHandler.key_tahun_masuk, tahun_masuk);
        values.put(DatabaseHandler.key_tahun_lulus, tahun_lulus);
        values.put(DatabaseHandler.key_keterangan_pendidikan, keterangan_pendidikan);

        insert.insert(DatabaseHandler.table_pendidikan, null, values);

        nama_sekolah_et.getText().clear();
        nama_jurusan_et.getText().clear();
        tahun_masuk_et.getText().clear();
        tahun_lulus_et.getText().clear();
        keterangan_pendidikan_et.getText().clear();

        list = getData();
        adapter.setListUser(list);
        adapter.notifyDataSetChanged();
    }

    private ArrayList<usermodels> getData(){
        ArrayList <usermodels> usermodelsArrayList = new ArrayList<>();

        SQLiteDatabase ReadData = db.getReadableDatabase();
        Cursor c = ReadData.rawQuery("SELECT "+DatabaseHandler.primarykey_id_pendidikan+", "
                + DatabaseHandler.table_pendidikan+"."+DatabaseHandler.key_id_pendidikan+", "
                + DatabaseHandler.key_nama_sekolah+", "
                + DatabaseHandler.key_nama_jurusan+", "
                + DatabaseHandler.key_tahun_masuk+", "
                + DatabaseHandler.key_tahun_lulus+", "
                + DatabaseHandler.key_keterangan_pendidikan+
                " FROM " + DatabaseHandler.table_user+
                " INNER JOIN " + DatabaseHandler.table_pendidikan+ " on " + DatabaseHandler.table_pendidikan +"."+DatabaseHandler.key_id+ " = " + DatabaseHandler.table_user+"."+DatabaseHandler.key_id+
                " WHERE " +DatabaseHandler.table_user+"."+DatabaseHandler.key_id+ " = " +id, null);

        if (c.moveToFirst()) {
            do {
                usermodels usermodels = new usermodels();
                usermodels.setPrimarykeypendidikanar(c.getString(0));
                usermodels.setId(c.getString(1));
                usermodels.setNamasekolah(c.getString(2));
                usermodels.setNamajurusan(c.getString(3));
                usermodels.setTahunmasuk(c.getString(4));
                usermodels.setTahunlulus(c.getString(5));
                usermodels.setKeteranganpendidikan(c.getString(6));
                usermodelsArrayList.add(usermodels);
            } while (c.moveToNext());
        }
        return usermodelsArrayList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.optionmenu, menu);
        getMenuInflater().inflate(R.menu.action_share, menu);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);
        MenuCompat.setGroupDividerEnabled(menu, true);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        final String appPackageName = getPackageName();
        switch (item.getItemId()){
            case R.id.shareButton:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String urlapp = "https://play.google.com/store/apps/details?id="+appPackageName;
                String shareBody = "Create Your CV Resume Download App in Store \n \n"+urlapp;
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Create Your CV Resume"));
                return true;
            case R.id.more_app:
                Uri uri = Uri.parse("https://play.google.com/store/apps/dev?id=7965844334266665422");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;
            case R.id.privacy_policy:
                Uri uri2 = Uri.parse("https://doc-hosting.flycricket.io/resume-builder-cv-marker-pdf/eedb7b8a-5f82-452d-ad1a-100566b0a91b/privacy");
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}