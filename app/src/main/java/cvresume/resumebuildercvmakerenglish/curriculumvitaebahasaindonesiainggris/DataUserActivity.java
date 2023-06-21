package cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dampyocalculator.cvbuilder.R;

import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.adapter.AdsAdapter;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.database.DatabaseHandler;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.detail_user.EditBahasaActivity;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.detail_user.EditKontakActivity;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.detail_user.EditPekerjaanActivity;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.detail_user.EditPendidikanActivity;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.detail_user.EditSkillActivity;

import java.util.Objects;

public class DataUserActivity extends AppCompatActivity {
    private ImageView imageView;
    private String image, id, nama, posisi, profil;
    private Button edit_profil, edit_kontak, edit_pendidikan, edit_skill, edit_bahasa ,edit_pengalaman, create_cv;
    private DatabaseHandler db;

    AdsAdapter adsAdapter = new AdsAdapter(this, this);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_user);
        db = new DatabaseHandler(this);

        adsAdapter.loadbanner();
        adsAdapter.loadInter();

        imageView = (ImageView) findViewById(R.id.data_img_user);
        edit_profil = (Button) findViewById(R.id.edit_profil);
        edit_kontak = (Button) findViewById(R.id.edit_kontak);
        edit_pendidikan = (Button) findViewById(R.id.edit_pendidikan);
        edit_skill = (Button) findViewById(R.id.edit_skill);
        edit_pengalaman = (Button) findViewById(R.id.edit_pengalaman);
        edit_bahasa = (Button) findViewById(R.id.edit_bahasa);
        create_cv = (Button) findViewById(R.id.create_cv2);

        image = getIntent().getStringExtra("foto");

        //ambil data
        id = getIntent().getStringExtra("id");
        getData();

        edit_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adsAdapter.showInterstitial(Edit_Activity.class, id);
               // Intent intent = new Intent(DataUserActivity.this, Edit_Activity.class);
              //  intent.putExtra("id", id);
               // DataUserActivity.this.startActivity(intent);
            }
        });

        edit_kontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adsAdapter.showInterstitial(EditKontakActivity.class, id);
                //Intent intent = new Intent(DataUserActivity.this, EditKontakActivity.class);
                //intent.putExtra("id", id);

                //DataUserActivity.this.startActivity(intent);
            }
        });

        edit_pendidikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adsAdapter.showInterstitial(EditPendidikanActivity.class, id);

                // Intent intent = new Intent(DataUserActivity.this, EditPendidikanActivity.class);
               // intent.putExtra("id", id);
               // DataUserActivity.this.startActivity(intent);
            }
        });

        edit_pengalaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adsAdapter.showInterstitial(EditPekerjaanActivity.class, id);

                //  Intent intent = new Intent(DataUserActivity.this, EditPekerjaanActivity.class);
               // intent.putExtra("id", id);
               // DataUserActivity.this.startActivity(intent);
            }
        });

        edit_skill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adsAdapter.showInterstitial(EditSkillActivity.class, id);

                // Intent intent = new Intent(DataUserActivity.this, EditSkillActivity.class);
               // intent.putExtra("id", id);
              //  DataUserActivity.this.startActivity(intent);
            }
        });

        edit_bahasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adsAdapter.showInterstitial(EditBahasaActivity.class, id);

                // Intent intent = new Intent(DataUserActivity.this, EditBahasaActivity.class);
               // intent.putExtra("id", id);
               // DataUserActivity.this.startActivity(intent);
            }
        });

        create_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adsAdapter.showInterstitial(CreateCvActivity.class, id);

                // Intent intent = new Intent(DataUserActivity.this, EditBahasaActivity.class);
                // intent.putExtra("id", id);
                // DataUserActivity.this.startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DataUserActivity.this, list_user.class);
        startActivity(intent);
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