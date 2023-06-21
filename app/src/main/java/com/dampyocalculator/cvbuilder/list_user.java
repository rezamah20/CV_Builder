package com.dampyocalculator.cvbuilder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.dampyocalculator.cvbuilder.adapter.AdsAdapter;
import com.dampyocalculator.cvbuilder.adapter.UserAdapter;
import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.dampyocalculator.cvbuilder.database.usermodels;

import java.util.ArrayList;
import java.util.Objects;

public class list_user extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ArrayList<usermodels> list;
    private DatabaseHandler databaseHandler;
    private Button btn_tambah;

    AdsAdapter adsAdapter = new AdsAdapter(this, this);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        recyclerView = (RecyclerView) findViewById(R.id.rview);
        btn_tambah = (Button) findViewById(R.id.tambah_profil);
        //cardView = (CardView) findViewById(R.id.cv_item_user);

        adsAdapter.loadbanner();

        userAdapter = new UserAdapter(this, this);

        databaseHandler = new DatabaseHandler(this);
        list = databaseHandler.getAll();
        userAdapter.setListUser(list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(list_user.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adsAdapter.showInterstitial(Edit_Activity.class,"");
                //Intent add_intent = new Intent(list_user.this, Edit_Activity.class);
                //list_user.this.startActivity(add_intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = databaseHandler.getAll();
        userAdapter.setListUser(list);
        userAdapter.notifyDataSetChanged();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(list_user.this, MainActivity.class);
        startActivity(intent);
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