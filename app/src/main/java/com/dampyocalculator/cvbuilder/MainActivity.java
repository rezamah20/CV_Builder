package com.dampyocalculator.cvbuilder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.dampyocalculator.cvbuilder.adapter.AdsAdapter;
import com.dampyocalculator.cvbuilder.adapter.ChooseLangAdapter;
import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.dampyocalculator.cvbuilder.database.usermodels;
import com.dampyocalculator.cvbuilder.detail_user.EditBahasaActivity;
import com.dampyocalculator.cvbuilder.template.Language_Manager;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.Utils;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    String appname, choose_lang, cs_lang;
    private PdfGenerator.XmlToPDFLifecycleObserver xmlToPDFLifecycleObserver;
    FrameLayout create_cv, profil;
    Language_Manager language_manager;
    Activity activity = this;
    String[] countryNames={"English", "Indonesia"};
    int flags[] = {R.drawable.eng_lang, R.drawable.id_lang};

    AdsAdapter adsAdapter = new AdsAdapter(this, this);;

    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    DatabaseHandler databaseHandler;
    private ArrayList<usermodels> list = new ArrayList<>();
    int ttl_usr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appname = this.getString(R.string.app_name);
        create_cv = findViewById(R.id.create_cv);
        profil = findViewById(R.id.profil_cv);

        initializeadmob();

        databaseHandler = new DatabaseHandler(this);
        list = databaseHandler.checkusr();
        ttl_usr = list.get(0).getCek_user();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Locale current = getResources().getConfiguration().getLocales().get(0);
            cs_lang = current.toString();
        }else{
            Locale current = getResources().getConfiguration().locale;
            cs_lang = current.toString();
        }

        language_manager = new Language_Manager(this);

        final List<String> lang = Arrays.asList("en", "id");
        final Spinner spinner = findViewById(R.id.choose_lang);

        ChooseLangAdapter customAdapter=new ChooseLangAdapter(getApplicationContext(),flags,countryNames);
        spinner.setAdapter(customAdapter);
        if (!Objects.equals(cs_lang, "in")){
            spinner.setSelection(0);
        }else {
            spinner.setSelection(1);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                         if (!Objects.equals(cs_lang, "en")){
                             choose_lang = "en";
                             language_manager.updateResource(choose_lang);
                             recreate();
                         }
                        break;
                         case 1:
                            if (!Objects.equals(cs_lang, "in")){
                                choose_lang = "in";
                                language_manager.updateResource(choose_lang);
                                recreate();
                            }
                             break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        xmlToPDFLifecycleObserver = new PdfGenerator.XmlToPDFLifecycleObserver(this);
        getLifecycle().addObserver(xmlToPDFLifecycleObserver);

        create_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ttl_usr == 0){
                    adsAdapter.showInterstitial(Edit_Activity.class,"");

                }else {
                    adsAdapter.showInterstitial(CreateCvActivity.class, "");
                }
                //Intent intent = new Intent(MainActivity.this, CreateCvActivity.class);
                //MainActivity.this.startActivity(intent);
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adsAdapter.showInterstitial(list_user.class,"");
                //Intent intent = new Intent(MainActivity.this, list_user.class);
                //startActivity(intent);
            }
        });
    }

    public void initializeadmob(){
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d("Admob", "Initialize Admob Success");
                adsAdapter.loadbanner();
                adsAdapter.loadInter();
            }
        });

    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };
    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
           finishAffinity();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, this.getString(R.string.tap_exit), Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);
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