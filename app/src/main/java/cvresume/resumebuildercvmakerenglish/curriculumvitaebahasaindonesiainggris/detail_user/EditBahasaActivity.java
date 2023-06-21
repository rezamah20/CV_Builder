package cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.detail_user;

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
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.dampyocalculator.cvbuilder.R;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.adapter.AdsAdapter;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.adapter.BahasaAdapter;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.adapter.ForbidenCharAdapter;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.database.DatabaseHandler;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.database.usermodels;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class EditBahasaActivity extends AppCompatActivity {

    private Button tambah_bahasa;
    AlertDialog alertDialog;
    private String id, isi_bahasa, levelbahasa_txt;
    private TextInputEditText isi_bahasa_et;
    private Spinner level_bahasa;
    private DatabaseHandler db =new DatabaseHandler(this);
    private ArrayList<usermodels> list;
    private BahasaAdapter adapter;
    RecyclerView rv_frag_exp_list;

    AdsAdapter adsAdapter = new AdsAdapter(this, this);;
    ForbidenCharAdapter forbidenCharAdapter =new ForbidenCharAdapter(this, this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bahasa);

        tambah_bahasa = (Button) findViewById(R.id.tambah_bahasa);
        rv_frag_exp_list =findViewById(R.id.rview_bahasa);
        alertDialog = expFormDiaglog();

        adsAdapter.loadbanner();

        id = getIntent().getStringExtra("id");
        adapter = new BahasaAdapter(this, this);

        db.getReadableDatabase();
        list = getData();
        adapter.setListUser(list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EditBahasaActivity.this);
        rv_frag_exp_list.setLayoutManager(layoutManager);
        rv_frag_exp_list.setAdapter(adapter);


        tambah_bahasa.setOnClickListener(new View.OnClickListener() {
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
        View view = inflater.inflate(R.layout.frag_bahasa, null);

        isi_bahasa_et = view.findViewById(R.id.isi_bahasa_et);

        //forbiden character
        isi_bahasa_et.setFilters(new InputFilter[] { forbidenCharAdapter });

        builder.setTitle(this.getString(R.string.add_language));
        level_bahasa = view.findViewById(R.id.level_bahasa);
        ArrayAdapter<String> adapter_spiner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.level_bahasa));
        adapter_spiner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        level_bahasa.setAdapter(adapter_spiner);
        builder.setView(view);

        builder.setPositiveButton(this.getString(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (level_bahasa.getSelectedItem().toString().equalsIgnoreCase("Level Bahasa")){
                    Toast.makeText(EditBahasaActivity.this, "Pilih Level Bahasa Dahulu", Toast.LENGTH_SHORT).show();
                }else{
                    saveData();
                }

            }
        });

        builder.setNeutralButton("Batalkan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


        return builder.create();
    }

    private void saveData(){
        SQLiteDatabase insert = db.getWritableDatabase();

        isi_bahasa = isi_bahasa_et.getText().toString();
        levelbahasa_txt = level_bahasa.getSelectedItem().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHandler.key_id_bahasa, id);
        contentValues.put(DatabaseHandler.nama_bahasa, isi_bahasa);
        contentValues.put(DatabaseHandler.level_bahasa, levelbahasa_txt);

        insert.insert(DatabaseHandler.table_bahasa, null, contentValues);

        isi_bahasa_et.getText().clear();
        level_bahasa.clearFocus();

        list = getData();
        adapter.setListUser(list);
        adapter.notifyDataSetChanged();

    }

    public ArrayList<usermodels> getData(){
        ArrayList <usermodels> usermodelsArrayList = new ArrayList<>();
        SQLiteDatabase ReadData = db.getReadableDatabase();

        Cursor c = ReadData.rawQuery("SELECT "+DatabaseHandler.primarykey_bahasa+", "
                + DatabaseHandler.table_bahasa+"."+DatabaseHandler.key_id_bahasa+", "
                + DatabaseHandler.nama_bahasa+", "
                + DatabaseHandler.level_bahasa+
                " FROM " + DatabaseHandler.table_user+
                " INNER JOIN " + DatabaseHandler.table_bahasa+ " on " + DatabaseHandler.table_bahasa +"."+DatabaseHandler.key_id+ " = " + DatabaseHandler.table_user+"."+DatabaseHandler.key_id+
                " WHERE " +DatabaseHandler.table_user+"."+DatabaseHandler.key_id+ " = " +id, null);

        if (c.moveToFirst()){
            do {
                usermodels usermodels = new usermodels();
                usermodels.setPrimarykeybahasa(c.getString(0));
                usermodels.setId(c.getString(1));
                usermodels.setNama_bahasa(c.getString(2));
                usermodels.setLevel_bahasa(c.getString(3));
                usermodelsArrayList.add(usermodels);
            }while (c.moveToNext());
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