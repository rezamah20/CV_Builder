package cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.template;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.CreateCvActivity;
import com.dampyocalculator.cvbuilder.R;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.adapter.Tmp1BahasaAdapter;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.adapter.Tmp1PenAdapter;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.adapter.Tmp1PengAdapter;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.adapter.TmpAdapter;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.database.DatabaseHandler;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.database.usermodels;

import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;

import java.util.ArrayList;

public class TemplateFrag extends Fragment {
    private View finalInvoiceViewToPrint;

    TextView tv_nametmp, tv_posisitmp, no_hp, email, address, profil;
    Button generate;
    private ArrayList<usermodels> list = new ArrayList<>();
    private DatabaseHandler databaseHandler;
    Context context;
    ComponentActivity activity;
    private PdfGenerator.XmlToPDFLifecycleObserver xmlToPDFLifecycleObserver;
    RecyclerView rv_frag_exp_list, tmp1_rv_frag_pend_list, tmp1_rv_frag_peng_list, tmp1_rv_frag_bahasa_list;
    private TmpAdapter adapter;
    private Tmp1PenAdapter tmp1penAdapter;
    private Tmp1PengAdapter tmp1pengAdapter;
    private Tmp1BahasaAdapter tmp1BahasaAdapter;

    String id, name_cv;
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
        tmp1_rv_frag_bahasa_list = root.findViewById(R.id.tmp1_rview_bahasa);


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
        name_cv = list.get(0).getNama();
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


        //bahasa
        tmp1BahasaAdapter = new Tmp1BahasaAdapter(activity, context);
        list.clear();
        list = databaseHandler.getDataBahasa(id);
        tmp1BahasaAdapter.setListUser(list);
        databaseHandler = new DatabaseHandler(context);
        id = CreateCvActivity.getMyData();
        tmp1_rv_frag_bahasa_list.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerbahasa = new LinearLayoutManager(context);
        tmp1_rv_frag_bahasa_list.setLayoutManager(layoutManagerbahasa);
        tmp1_rv_frag_bahasa_list.setAdapter(tmp1BahasaAdapter);

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
                .setFileName(this.getString(R.string.name_resume)+" "+name_cv)
                .setFolderNameOrPath("demo-invoice-folder/")
                .actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.OPEN)
                .savePDFSharedStorage(xmlToPDFLifecycleObserver)
                .build(new PdfGeneratorListener() {
                    @Override
                    public void onFailure(FailureResponse failureResponse) {
                        super.onFailure(failureResponse);
                        Log.d("TAG", "onFailure: " + failureResponse.getErrorMessage());
                        Toast.makeText(getContext(), "" + failureResponse.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void showLog(String log) {
                        super.showLog(log);
                        Log.d("TAG", "log: " + log);
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
                        Log.d("TAG", "Success: " + response.getPath());

                    }
                });

    }
}
