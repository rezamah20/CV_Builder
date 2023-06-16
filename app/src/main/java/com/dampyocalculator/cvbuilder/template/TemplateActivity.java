package com.dampyocalculator.cvbuilder.template;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dampyocalculator.cvbuilder.MainActivity;
import com.dampyocalculator.cvbuilder.R;
import com.dampyocalculator.cvbuilder.adapter.TmpAdapter;
import com.dampyocalculator.cvbuilder.adapter.UserAdapter;
import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.dampyocalculator.cvbuilder.database.usermodels;
import com.dampyocalculator.cvbuilder.detail_user.EditSkillActivity;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;

import java.util.ArrayList;

public class TemplateActivity extends AppCompatActivity {
    RecyclerView rv_frag_exp_list;
    private TmpAdapter adapter;
    private ArrayList<usermodels> list;
    private DatabaseHandler db =new DatabaseHandler(this);
    private String id, id_template;
    private Button generate;
    private PdfGenerator.XmlToPDFLifecycleObserver xmlToPDFLifecycleObserver;

    private Fragment demoInvoiceFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        rv_frag_exp_list = findViewById(R.id.rview_template);
       generate = (Button) findViewById(R.id.generate_pdfb);

        id = getIntent().getStringExtra("id");
        id_template = getIntent().getStringExtra("tmp");

        adapter = new TmpAdapter(this, this);
        demoInvoiceFragment = new TemplateFrag(this, this);

        db.getReadableDatabase();
        list = getData();
        adapter.setListUser(list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TemplateActivity.this);
        rv_frag_exp_list.setLayoutManager(layoutManager);
        rv_frag_exp_list.setAdapter(adapter);

        xmlToPDFLifecycleObserver = new PdfGenerator.XmlToPDFLifecycleObserver(this);
        getLifecycle().addObserver(xmlToPDFLifecycleObserver);

       generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
                    getSupportFragmentManager().beginTransaction()
                            .add(android.R.id.content, demoInvoiceFragment)
                            .commit();
                }
           }
        });


    }

    private ArrayList<usermodels> getData(){

        ArrayList <usermodels> usermodelsArrayList = new ArrayList<>();

        SQLiteDatabase ReadData = db.getReadableDatabase();
        Cursor c = ReadData.rawQuery("SELECT * FROM tb_user", null);
        if (c.moveToFirst()){
            do {
                usermodels usermodels = new usermodels();
                usermodels.setId(c.getString(0));
                usermodels.setName(c.getString(1));
                usermodels.setPosisi(c.getString(2));

                usermodelsArrayList.add(usermodels);
            }while (c.moveToNext());
        }
        return usermodelsArrayList;
    }

    private void generatepdf(View view){

        PdfGenerator.getBuilder()
                .setContext(TemplateActivity.this)
                .fromViewSource()
                .fromView(view)
                .setFileName("Demo-Text")
                .actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.SHARE)
                .savePDFSharedStorage(xmlToPDFLifecycleObserver)
                .build(new PdfGeneratorListener() {
                    @Override
                    public void onStartPDFGeneration() {

                    }

                    @Override
                    public void onFinishPDFGeneration() {

                    }
                });
    }
}