package com.dampyocalculator.cvbuilder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.dampyocalculator.cvbuilder.adapter.AdsAdapter;
import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Objects;

public class Edit_Activity extends AppCompatActivity {
    private Button btn_simpan, btn_chose_photo;
    private TextInputEditText editnama, editposisi, editprofil;
    private DatabaseHandler db;
    private String id, nama, posisi, profil, image;

    private static final int PICKFILE_RESULT_CODE = 1;
    private static final int RESULT_OK = -1;
    private Uri img_uri;
    private ImageView imageView;

    AdsAdapter adsAdapter = new AdsAdapter(this, this);;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        btn_simpan = findViewById(R.id.simpan);

        adsAdapter.loadbanner();

        editnama = findViewById(R.id.namaprofil_et);
        editposisi = findViewById(R.id.posisiprofil_et);
        editprofil = findViewById(R.id.dataprofil_et);
        btn_chose_photo = findViewById(R.id.add_image);
        imageView = (ImageView) findViewById(R.id.img_user);
        db = new DatabaseHandler(this);

        //getData();
        id = getIntent().getStringExtra("id");
        /*nama = getIntent().getStringExtra("nama");
        posisi = getIntent().getStringExtra("posisi");
        profil = getIntent().getStringExtra("profil");
        image = getIntent().getStringExtra("foto");
        */


        if (id == null || id.equals("")){
            setTitle("Tambah Profil");
        }else{
            getData();
        }

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (id == null || id.equals("")){
                        save();
                    }else{
                        edit();
                    }
                }catch (Exception e){
                    Log.d("Simpan", e.getMessage());
                }
            }
        });

        btn_chose_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = CropImage.activity().setAspectRatio(3,3).getIntent(getBaseContext());
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
                //Intent chosephoto = new Intent(Intent.ACTION_GET_CONTENT);
                //chosephoto.setType("image/*");
                //chosephoto =Intent.createChooser(chosephoto, "Pilih Foto");
               // startActivityForResult(chosephoto, PICKFILE_RESULT_CODE);
            }
        });

    }

    private void save (){
        if (String.valueOf(editnama.getText()).equals("") || String.valueOf(editposisi.getText()).equals("")){
            Toast.makeText(this, this.getString(R.string.fill_coloms), Toast.LENGTH_SHORT).show();
        }else{
            db.insert(editnama.getText().toString(), editposisi.getText().toString(), editprofil.getText().toString(), img_uri.toString());
            getLastData();
        }
    }

    public void edit(){
        if (String.valueOf(editnama.getText()).equals("") || String.valueOf(editposisi.getText()).equals("")){
            Toast.makeText(this, this.getString(R.string.fill_coloms), Toast.LENGTH_SHORT).show();
        }else{
            Log.d("Tag update posisi", editposisi.getText().toString());
            db.update(Integer.parseInt(id), editnama.getText().toString(), editposisi.getText().toString(), editprofil.getText().toString(), img_uri.toString());
            finish();
        }
    }

    private void getData(){
        SQLiteDatabase ReadData = db.getReadableDatabase();
        Cursor c =ReadData.rawQuery("SELECT * FROM " +DatabaseHandler.table_user+ " WHERE " +DatabaseHandler.key_id+ " = " +id, null);
        //Cursor c = ReadData.rawQuery("SELECT * FROM tb_user WHERE id = 1", null);

        c.moveToFirst();
        if (c.moveToFirst()){
                nama = c.getString(1);
                posisi = c.getString(2);
                profil = c.getString(3);
                image = c.getString(4);

            img_uri = Uri.parse(image);
            setTitle("Edit Profil");
            editnama.setText(nama);
            editposisi.setText(posisi);
            editprofil.setText(profil);
            imageView.setImageURI(Uri.parse(image));
        }
    }
    private  void getLastData(){
        SQLiteDatabase ReadData = db.getReadableDatabase();
        Cursor c =ReadData.rawQuery("SELECT * FROM "
                +DatabaseHandler.table_user+ " ORDER BY " +DatabaseHandler.key_id+ " DESC LIMIT 1 ", null);
        c.moveToFirst();
        if (c.moveToFirst()){
            id = c.getString(0);

            Intent intent = new Intent(Edit_Activity.this, DataUserActivity.class);
            intent.putExtra("id", id);
            Edit_Activity.this.startActivity(intent);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode ==  Activity.RESULT_OK) {
                img_uri = result.getUri();
                Log.e("resultUri ->", String.valueOf(img_uri));
                imageView.setImageURI(img_uri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("error ->", String.valueOf(error));
            }
        }


        //if (requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK) {
         //   ImageView imageView = (ImageView) findViewById(R.id.img_user);
         //  img_uri = data.getData();

          //  try {
         //       Bitmap bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(img_uri));
         //       imageView.setImageBitmap(bitmap);
          //  }catch (FileNotFoundException e){
          //      e.printStackTrace();
          //  }

      //  }
    }
}