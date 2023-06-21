package cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dampyocalculator.cvbuilder.R;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.database.DatabaseHandler;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.database.usermodels;

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
       // holder.tv_nametmp.setText(list.get(position).getNama());
       // holder.tv_posisitmp.setText(list.get(position).getPosisi());
        holder.skill.setText(list.get(position).getNamaskill());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class TmpViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nametmp, tv_posisitmp, skill;

        public TmpViewHolder(@NonNull View itemView) {
            super(itemView);

           // tv_nametmp = (TextView) itemView.findViewById(R.id.tv_item_nama_tmp);
           // tv_posisitmp = (TextView) itemView.findViewById(R.id.tv_item_posisi_tmp);
            skill = (TextView) itemView.findViewById(R.id.tv_item_nohp_tmp);


        }
    }

    public void generatePdf() {
       }
}
