package com.dampyocalculator.cvbuilder.detail_user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dampyocalculator.cvbuilder.R;
import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class EditKontakActivity extends AppCompatActivity {


    private TextInputEditText edit_notlp, editemail, editalamat;
    private DatabaseHandler db;
    private Button simpan_kontak;
    private String id, no_tlpn, email, alamat, id_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_kontak);
        db = new DatabaseHandler(this);

        edit_notlp = findViewById(R.id.no_tlp_et);
        editemail = findViewById(R.id.email_et);
        editalamat = findViewById(R.id.alamat_et);
        simpan_kontak = (Button) findViewById(R.id.simpan_kontak);

        id = getIntent().getStringExtra("id");

        getData();

        simpan_kontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (no_tlpn != null && email != null && alamat != null){
                    Log.d("tag update", "update data");
                    updateData();
                }else{
                    Log.d("tag save","saveing data");
                    saveData();
                }
            }
        });


    }

    private void saveData(){
        SQLiteDatabase insert = db.getWritableDatabase();

        no_tlpn = edit_notlp.getText().toString();
        email = editemail.getText().toString();
        alamat = editalamat.getText().toString();

        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.key_id_contact, id);
        values.put(DatabaseHandler.key_notlpn, no_tlpn);
        values.put(DatabaseHandler.key_email, email);
        values.put(DatabaseHandler.alamat, alamat);

        insert.insert(DatabaseHandler.table_contact, null, values);
        finish();
    }

    private void updateData(){
        SQLiteDatabase update = db.getReadableDatabase();

        no_tlpn = edit_notlp.getText().toString();
        email = editemail.getText().toString();
        alamat = editalamat.getText().toString();


        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.key_notlpn, no_tlpn);
        values.put(DatabaseHandler.key_email, email);
        values.put(DatabaseHandler.alamat, alamat);

        update.update(DatabaseHandler.table_contact, values, DatabaseHandler.primarykey_id_contact + "=" +id, null);
        finish();
    }


    private void getData(){
        SQLiteDatabase query = db.getReadableDatabase();
        Cursor c = query.rawQuery("SELECT "+DatabaseHandler.primarykey_id_contact+", "
                + DatabaseHandler.key_notlpn+", "
                + DatabaseHandler.key_email+", "
                + DatabaseHandler.alamat+
                " FROM " + DatabaseHandler.table_user+
                " INNER JOIN " + DatabaseHandler.table_contact+ " on " + DatabaseHandler.table_contact +"."+DatabaseHandler.key_id+ " = " + DatabaseHandler.table_user+"."+DatabaseHandler.key_id+
                " WHERE " +DatabaseHandler.table_user+"."+DatabaseHandler.key_id+ " = " +id, null);

        c.moveToFirst();
        if (c.moveToFirst()){
            id_contact = c.getString(0);
            no_tlpn = c.getString(1);
            email = c.getString(2);
            alamat = c.getString(3);

            if (no_tlpn != null && email != null && alamat != null){
                edit_notlp.setText(no_tlpn);
                editemail.setText(email);
                editalamat.setText(alamat);
            }
        }


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