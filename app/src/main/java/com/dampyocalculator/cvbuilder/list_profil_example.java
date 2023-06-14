package com.dampyocalculator.cvbuilder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.dampyocalculator.cvbuilder.database.adapter;
import com.dampyocalculator.cvbuilder.database.usermodels;

import java.util.ArrayList;
import java.util.List;

public class list_profil_example extends AppCompatActivity {

    ListView listView;
    AlertDialog.Builder dialog;
    List<usermodels> list = new ArrayList<>();
    adapter adapter;
    DatabaseHandler db = new DatabaseHandler(this);
    Button tambah_profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_profil);
        //Log.d("database read", )
        db = new DatabaseHandler(getApplicationContext());
        tambah_profil = findViewById(R.id.tambah_profil);
        tambah_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(list_profil_example.this, Edit_Activity.class);
                startActivity(intent);
            }
        });
        listView = findViewById(R.id.list_profil);
        adapter = new adapter(list_profil_example.this, list);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String id = list.get(i).getId();
                final String nama = list.get(i).getNama();
                final String posisi = list.get(i).getPosisi();
                final CharSequence[] dialogItem = {"Edit", "Hapus"};
                dialog = new AlertDialog.Builder(list_profil_example.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Log.d("case", "case 0");
                                Intent intent = new Intent(list_profil_example.this, Edit_Activity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("nama", nama);
                                intent.putExtra("posisi", posisi);
                                startActivity(intent);
                                break;
                            case 1:
                                Log.d("case", "case 1");
                               // db.delete(id);
                                list.clear();
                                // panggil data ulang
                                getData();
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });
        getData();
    }

    private void getData(){
        ArrayList<usermodels> rows = db.getAll();
        for (int i = 0; i<rows.size(); i++){
            //String id = rows.get(i).get("id");
           // String nama = rows.get(i).get("nama");
            //String posisi = rows.get(i).get("posisi");

            usermodels data = new usermodels();
           // data.setId(id);
           // data.setName(nama);
           // data.setPosisi(posisi);
            list.add(data);
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        getData();
    }
}