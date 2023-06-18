package com.dampyocalculator.cvbuilder.template;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dampyocalculator.cvbuilder.CreateCvActivity;
import com.dampyocalculator.cvbuilder.R;
import com.dampyocalculator.cvbuilder.adapter.Tmp1PenAdapter;
import com.dampyocalculator.cvbuilder.adapter.Tmp1PengAdapter;
import com.dampyocalculator.cvbuilder.adapter.TmpAdapter;
import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.dampyocalculator.cvbuilder.database.usermodels;
import com.dampyocalculator.cvbuilder.detail_user.EditPendidikanActivity;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TemplateFrag extends Fragment {
    private View finalInvoiceViewToPrint;

    TextView tv_nametmp, tv_posisitmp, no_hp, email, address, profil;
    Button generate;
    private ArrayList<usermodels> list = new ArrayList<>();
    private DatabaseHandler databaseHandler;
    Context context;
    ComponentActivity activity;
    private PdfGenerator.XmlToPDFLifecycleObserver xmlToPDFLifecycleObserver;
    RecyclerView rv_frag_exp_list, tmp1_rv_frag_pend_list, tmp1_rv_frag_peng_list;
    private TmpAdapter adapter;
    private Tmp1PenAdapter tmp1penAdapter;
    private Tmp1PengAdapter tmp1pengAdapter;

    String id;
    TextView tvskill;
    String cek_array, image;
    private ImageView imageView;



    public TemplateFrag(ComponentActivity activity, Context context) {
        this.context = context;
        this.activity = activity;
    }

    private View createInvoiceViewFromRootView(View root) {
        finalInvoiceViewToPrint = root.findViewById(R.id.invoice_layout);
        tv_nametmp = (TextView) root.findViewById(R.id.tv_item_nama_tmp);
        tv_posisitmp = (TextView) root.findViewById(R.id.tv_item_posisi_tmp);

        no_hp = (TextView) root.findViewById(R.id.tmp_tv_nohp);
        email = (TextView) root.findViewById(R.id.tmp_tv_email);
        address = (TextView) root.findViewById(R.id.tmp_tv_alamat);
        profil = (TextView) root.findViewById(R.id.tv_profil_tmp);


        imageView = (ImageView) root.findViewById(R.id.tmp1_imgview);
        rv_frag_exp_list = root.findViewById(R.id.rview_template);
        tmp1_rv_frag_pend_list = root.findViewById(R.id.tmp1_rview_pendidikan);
        tmp1_rv_frag_peng_list = root.findViewById(R.id.tmp1_rview_pengalaman);


        id = CreateCvActivity.getMyData();

        //user dan pendidikan
        databaseHandler =new DatabaseHandler(context);
        adapter = new TmpAdapter(activity, context);
        databaseHandler.getReadableDatabase();
        id = CreateCvActivity.getMyData();
        list.clear();
        list = databaseHandler.getDataUser(id);
        adapter.setListUser(list);
        image = list.get(0).getImage();
        imageView.setImageURI(Uri.parse(image));
        tv_nametmp.setText(list.get(0).getNama());
        tv_posisitmp.setText(list.get(0).getPosisi());
        no_hp.setText(list.get(0).getNotlpn());
        email.setText(list.get(0).getEmail());
        address.setText(list.get(0).getAlamat());
        profil.setText(list.get(0).getProfil());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            profil.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }
        rv_frag_exp_list.setLayoutManager(new GridLayoutManager(context, 2));
        rv_frag_exp_list.setHasFixedSize(true);
        rv_frag_exp_list.addItemDecoration(new GridSpacingItemDecoration(3, 0, false));
        rv_frag_exp_list.setAdapter(adapter);


        //pendidikan
        tmp1penAdapter = new Tmp1PenAdapter(activity, context);
        list.clear();
        list = databaseHandler.getDataPend(id);
        tmp1penAdapter.setListUser(list);
        databaseHandler =new DatabaseHandler(context);
        id = CreateCvActivity.getMyData();
        tmp1_rv_frag_pend_list.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerpen = new LinearLayoutManager(context);
        tmp1_rv_frag_pend_list.setLayoutManager(layoutManagerpen);
        tmp1_rv_frag_pend_list.setAdapter(tmp1penAdapter);

        //pengalaman
        tmp1pengAdapter = new Tmp1PengAdapter(activity, context);
        list.clear();
        list = databaseHandler.getDataPeng(id);
        tmp1pengAdapter.setListUser(list);
        databaseHandler = new DatabaseHandler(context);
        id = CreateCvActivity.getMyData();
        tmp1_rv_frag_peng_list.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerpeng = new LinearLayoutManager(context);
        tmp1_rv_frag_peng_list.setLayoutManager(layoutManagerpeng);
        tmp1_rv_frag_peng_list.setAdapter(tmp1pengAdapter);

        xmlToPDFLifecycleObserver = new PdfGenerator.XmlToPDFLifecycleObserver((ComponentActivity) activity);
        getLifecycle().addObserver(xmlToPDFLifecycleObserver);





        return finalInvoiceViewToPrint;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.invoice_layout, container, false);
        Button generate_invoice_btn = root.findViewById(R.id.generate_invoice_btn);


        finalInvoiceViewToPrint = createInvoiceViewFromRootView(root);


        generate_invoice_btn.setOnClickListener(v -> {
            generatePdf();
        });
        return root;
    }

    public void generatePdf() {
        PdfGenerator.getBuilder()
                .setContext(requireActivity())
                .fromViewSource()
                .fromView(finalInvoiceViewToPrint)
                /* "fromLayoutXML()" takes array of layout resources.
                 * You can also invoke "fromLayoutXMLList()" method here which takes list of layout resources instead of array. */
                /* It takes default page size like A4,A5. You can also set custom page size in pixel
                 * by calling ".setCustomPageSize(int widthInPX, int heightInPX)" here. */
                .setFileName("demo-invoice")
                /* It is file name */
                .setFolderNameOrPath("demo-invoice-folder/")
                /* It is folder name. If you set the folder name like this pattern (FolderA/FolderB/FolderC), then
                 * FolderA creates first.Then FolderB inside FolderB and also FolderC inside the FolderB and finally
                 * the pdf file named "Test-PDF.pdf" will be store inside the FolderB. */
                .actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.OPEN)
                .savePDFSharedStorage(xmlToPDFLifecycleObserver)
                /* It true then the generated pdf will be shown after generated. */
                .build(new PdfGeneratorListener() {
                    @Override
                    public void onFailure(FailureResponse failureResponse) {
                        super.onFailure(failureResponse);
                        Log.d("TAG", "onFailure: " + failureResponse.getErrorMessage());
                        /* If pdf is not generated by an error then you will findout the reason behind it
                         * from this FailureResponse. */
                        //Toast.makeText(MainActivity.this, "Failure : "+failureResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(), "" + failureResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void showLog(String log) {
                        super.showLog(log);
                        Log.d("TAG", "log: " + log);
                        /*It shows logs of events inside the pdf generation process*/
                    }

                    @Override
                    public void onStartPDFGeneration() {

                    }

                    @Override
                    public void onFinishPDFGeneration() {

                    }

                    @Override
                    public void onSuccess(SuccessResponse response) {
                        super.onSuccess(response);
                        /* If PDF is generated successfully then you will find SuccessResponse
                         * which holds the PdfDocument,File and path (where generated pdf is stored)*/
                        //Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "Success: " + response.getPath());

                    }
                });

    }
}
