package com.dampyocalculator.cvbuilder.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dampyocalculator.cvbuilder.R;
import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.dampyocalculator.cvbuilder.database.usermodels;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;

import java.util.ArrayList;

public class TmpAdapter extends RecyclerView.Adapter<TmpAdapter.TmpViewHolder> {

    Activity activity;
    Context context;
    private ArrayList<usermodels> list = new ArrayList<>();
    private DatabaseHandler databaseHandler;
    RecyclerView mRecyclerView;
    View view;


    public TmpAdapter(Activity activity, Context context){
        this.activity = activity;
        this.context = context;
    }
    public void setListUser(ArrayList<usermodels> listNotes){
        if (listNotes.size() > 0){
            this.list.clear();
        }
        this.list.addAll(listNotes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TmpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tmp1, parent, false);
        return new TmpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TmpViewHolder holder, int position) {
        holder.tv_nametmp.setText(list.get(position).getNama());
        holder.tv_posisitmp.setText(list.get(position).getPosisi());
        Log.d("tag test", list.get(position).getNama());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TmpViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nametmp, tv_posisitmp;
        Button generate;

        public TmpViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_nametmp = (TextView) itemView.findViewById(R.id.tv_item_nama_tmp);
            tv_posisitmp = (TextView) itemView.findViewById(R.id.tv_item_posisi_tmp);

        }
    }

    public void generatePdf() {
       }
}
