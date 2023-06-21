package cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.dampyocalculator.cvbuilder.R;

import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.adapter.AdsAdapter;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.adapter.TmpAdapter;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.adapter.UserAdapter;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.database.DatabaseHandler;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.database.usermodels;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.template.TemplateFrag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateCvActivity extends AppCompatActivity {
    Spinner choose_profil;
    private Button test_nama;
    private ArrayList<usermodels> list = new ArrayList<>();


    private UserAdapter userAdapter;
    private TmpAdapter tmpadapter;
    private DatabaseHandler databaseHandler;
    private List<String> id_user;
    private List<String> nama_user;
    private List<String> jabatan;

    static String id, nama;

    //template cv
    private List<String> listtemplate;
    private List<Integer> imglisttemplate;
    private List<String> id_tempalte;
    private String id_temp_txt;
    Spinner choose_template;
    private ArrayAdapter<String> arraytemplate;
    ImageView previewtemplate;
    private Fragment demoInvoiceFragment;

    AdsAdapter adsAdapter = new AdsAdapter(this, this);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cv);


        choose_profil = (Spinner) findViewById(R.id.choose_profil);
        choose_template = (Spinner) findViewById(R.id.choose_template);
        previewtemplate = (ImageView) findViewById(R.id.imgtemplate);

        adsAdapter.loadbanner();

        test_nama = (Button) findViewById(R.id.test_nama);
        userAdapter = new UserAdapter(this, this);
        tmpadapter = new TmpAdapter(this, this);
        databaseHandler = new DatabaseHandler(this);
        list.clear();
        userAdapter.setListUser(list);
        list = databaseHandler.getAll();

        demoInvoiceFragment = new TemplateFrag(this, this);

        getData();
        getTemplate();



        test_nama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               findViewById(R.id.create_cv_layout).setVisibility(View.GONE);
                if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
                    getSupportFragmentManager().beginTransaction()

                            .add(android.R.id.content, demoInvoiceFragment)
                            .commit();
                }

            }
        });
    }

    public static String getMyData() {
        return id;
    }

    private void getData(){
        id_user = new ArrayList<String>();
        nama_user = new ArrayList<String>();
        jabatan = new ArrayList<String>();


        for (int i = 0; i < list.size(); i++) {
            id_user.add(list.get(i).getId());
            nama_user.add(list.get(i).getNama()+" - " +list.get(i).getPosisi());
            System.out.println("data 2 "+list.get(i).getNama());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateCvActivity.this,
                R.layout.mytextview, nama_user);
        spinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        choose_profil.setAdapter(spinnerAdapter);
        choose_profil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                id = id_user.get(position);
                nama = nama_user.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void getTemplate(){
        listtemplate = new ArrayList<>();

        listtemplate.add("Template 1");
       // listtemplate.add("Template 2");

        imglisttemplate = new ArrayList<>();
        id_tempalte = new ArrayList<>();
        arraytemplate = new ArrayAdapter<>(CreateCvActivity.this, R.layout.mytextview, listtemplate);
        choose_template.setAdapter(arraytemplate);

        choose_template.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                previewtemplate.setImageResource(imglisttemplate.get(i));
                id_temp_txt = id_tempalte.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imglisttemplate.add(R.drawable.preview_cv1);
        //imglisttemplate.add(R.drawable.cvtemplate2);

        id_tempalte.add("1");
       // id_tempalte.add("2");

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