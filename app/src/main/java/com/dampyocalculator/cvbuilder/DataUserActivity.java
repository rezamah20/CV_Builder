package com.dampyocalculator.cvbuilder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.dampyocalculator.cvbuilder.detail_user.EditBahasaActivity;
import com.dampyocalculator.cvbuilder.detail_user.EditKontakActivity;
import com.dampyocalculator.cvbuilder.detail_user.EditPekerjaanActivity;
import com.dampyocalculator.cvbuilder.detail_user.EditPendidikanActivity;
import com.dampyocalculator.cvbuilder.detail_user.EditSkillActivity;

import java.util.Objects;

public class DataUserActivity extends AppCompatActivity {
    private ImageView imageView;
    private String image, id, nama, posisi, profil;
    private Button edit_profil, edit_kontak, edit_pendidikan, edit_skill, edit_bahasa ,edit_pengalaman;
    private DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_user);
        db = new DatabaseHandler(this);


        imageView = (ImageView) findViewById(R.id.data_img_user);
        edit_profil = (Button) findViewById(R.id.edit_profil);
        edit_kontak = (Button) findViewById(R.id.edit_kontak);
        edit_pendidikan = (Button) findViewById(R.id.edit_pendidikan);
        edit_skill = (Button) findViewById(R.id.edit_skill);
        edit_pengalaman = (Button) findViewById(R.id.edit_pengalaman);
        edit_bahasa = (Button) findViewById(R.id.edit_bahasa);

        image = getIntent().getStringExtra("foto");

        //ambil data
        id = getIntent().getStringExtra("id");
        getData();

        edit_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataUserActivity.this, Edit_Activity.class);
                intent.putExtra("id", id);
                //intent.putExtra("nama", nama);
                //intent.putExtra("posisi", posisi);
                //intent.putExtra("profil", profil);
                //intent.putExtra("foto", image);
                DataUserActivity.this.startActivity(intent);
            }
        });

        edit_kontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataUserActivity.this, EditKontakActivity.class);
                intent.putExtra("id", id);

                DataUserActivity.this.startActivity(intent);
            }
        });

        edit_pendidikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataUserActivity.this, EditPendidikanActivity.class);
                intent.putExtra("id", id);
                DataUserActivity.this.startActivity(intent);
            }
        });

        edit_pengalaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataUserActivity.this, EditPekerjaanActivity.class);
                intent.putExtra("id", id);
                DataUserActivity.this.startActivity(intent);
            }
        });

        edit_skill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataUserActivity.this, EditSkillActivity.class);
                intent.putExtra("id", id);
                DataUserActivity.this.startActivity(intent);
            }
        });

        edit_bahasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DataUserActivity.this, EditBahasaActivity.class);
                intent.putExtra("id", id);
                DataUserActivity.this.startActivity(intent);
            }
        });

    }
    private void getData(){
        SQLiteDatabase ReadData = db.getReadableDatabase();
        Cursor c =ReadData.rawQuery("SELECT * FROM " + DatabaseHandler.table_user+ " WHERE " +DatabaseHandler.key_id+ " = " +id, null);
        //Cursor c = ReadData.rawQuery("SELECT * FROM tb_user WHERE id = 1", null);

        c.moveToFirst();
        if (c.moveToFirst()){
            image = c.getString(4);

            setTitle("Edit Profil");
            imageView.setImageURI(Uri.parse(image));
            imageView.setImageURI(Uri.parse(image));
        }
    }

    protected void onResume() {
        super.onResume();
        getData();
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