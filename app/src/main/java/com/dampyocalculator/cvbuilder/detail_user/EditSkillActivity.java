package com.dampyocalculator.cvbuilder.detail_user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dampyocalculator.cvbuilder.R;
import com.dampyocalculator.cvbuilder.adapter.SkillAdapter;
import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.dampyocalculator.cvbuilder.database.usermodels;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class EditSkillActivity extends AppCompatActivity {
    private Button tambah_skill;
    AlertDialog alertDialog;
    private TextInputEditText  nama_skill_et;
    private String id, nama_skill;
    private DatabaseHandler db =new DatabaseHandler(this);
    private SkillAdapter adapter;
    private ArrayList<usermodels> list;
    RecyclerView rv_frag_exp_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_skill);

        tambah_skill = findViewById(R.id.tambah_skill);
        rv_frag_exp_list = findViewById(R.id.rview_skill);
        alertDialog = skillFromDialog();
        id = getIntent().getStringExtra("id");

        Log.d("TAG ID", id);

        adapter = new SkillAdapter(this, this);

        db.getReadableDatabase();
        list = getData();
        adapter.setListUser(list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EditSkillActivity.this);
        rv_frag_exp_list.setLayoutManager(layoutManager);
        rv_frag_exp_list.setAdapter(adapter);


        tambah_skill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });

    }

    private AlertDialog skillFromDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.fragskill, null);

        nama_skill_et = view.findViewById(R.id.nama_skill_et);

        builder.setView(view);
        builder.setTitle("Tambah Skill");
        builder.setNeutralButton("Batalkan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

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
        SQLiteDatabase insert = db.getWritableDatabase();

        nama_skill = nama_skill_et.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.key_id_skill, id);
        contentValues.put(DatabaseHandler.nama_skill, nama_skill);

        insert.insert(DatabaseHandler.table_skill, null, contentValues);

        nama_skill_et.getText().clear();

        list = getData();
        adapter.setListUser(list);
        adapter.notifyDataSetChanged();

    }

    private ArrayList<usermodels> getData(){
        ArrayList <usermodels> usermodelsArrayList = new ArrayList<>();

        SQLiteDatabase ReadData = db.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT "+DatabaseHandler.primarykey_id_skill+", "
                + DatabaseHandler.table_skill+"."+DatabaseHandler.key_id_skill+", "
                +DatabaseHandler.nama_skill+
                " FROM " +DatabaseHandler.table_user+
                " INNER JOIN " +DatabaseHandler.table_skill+ " on " +DatabaseHandler.table_skill+"."+DatabaseHandler.key_id+ " = "+DatabaseHandler.table_user+"."+DatabaseHandler.key_id+
                " Where " +DatabaseHandler.table_user+"."+DatabaseHandler.key_id+ " = " +id, null);
        if (cursor.moveToFirst()){
            do {
                usermodels usermodels = new usermodels();
                usermodels.setPrimarykeyskill(cursor.getString(0));
                usermodels.setId(cursor.getString(1));
                usermodels.setNamaskill(cursor.getString(2));

                usermodelsArrayList.add(usermodels);
            }while (cursor.moveToNext());
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
        switch (item.getItemId()){
            case R.id.shareButton:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Create Your CV Resume";
                String shareSubject = "Thanks for Sharing this app";
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                startActivity(Intent.createChooser(sharingIntent, "Create Your CV Resume"));
                return true;
            case R.id.more_app:
                Uri uri = Uri.parse("https://play.google.com/store/apps/dev?id=7965844334266665422");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;
            case R.id.privacy_policy:
                Uri uri2 = Uri.parse("https://pages.flycricket.io/cleaner-whatsapp/privacy.html");
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}