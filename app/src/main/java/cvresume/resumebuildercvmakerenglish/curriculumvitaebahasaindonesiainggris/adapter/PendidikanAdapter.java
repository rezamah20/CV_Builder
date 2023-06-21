package cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dampyocalculator.cvbuilder.R;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.database.DatabaseHandler;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.database.usermodels;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.detail_user.EditPendidikanActivity;

import java.util.ArrayList;

public class PendidikanAdapter extends RecyclerView.Adapter<PendidikanAdapter.PendidikanViewHolder> {

    Activity activity;
    Context context;
    private ArrayList<usermodels> list = new ArrayList<>();
    private DatabaseHandler databaseHandler;
    RecyclerView mRecyclerView;


    public PendidikanAdapter(Activity activity, Context context){
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
    public PendidikanAdapter.PendidikanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pendidikan, parent, false);
        return new PendidikanViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull PendidikanAdapter.PendidikanViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_namasekolah.setText(list.get(position).getNamasekolah());
        holder.tv_jurusan.setText(list.get(position).getNamajurusan());
        holder.tv_tahunmasuk.setText(list.get(position).getTahunmasuk());
        holder.tv_tahunlulus.setText(list.get(position).getTahunlulus());

        holder.id_pendidikan = list.get(position).getPrimarykeypendidikanar();
        holder.key_id = list.get(position).getId();

        holder.delete_pendidikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle(context.getString(R.string.delete_conf)+" "+list.get(position).getNamasekolah());
                builder.setMessage(context.getString(R.string.put_delete_conf));
                builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseHandler = new DatabaseHandler(context);

                        SQLiteDatabase delete = databaseHandler.getWritableDatabase();
                        String query = "DELETE FROM " +DatabaseHandler.table_pendidikan+ " WHERE " +DatabaseHandler.primarykey_id_pendidikan+ " = " +holder.id_pendidikan;
                        delete.execSQL(query);
                        Toast.makeText(activity, context.getString(R.string.dialog_succ_delete), Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(activity, EditPendidikanActivity.class);
                        myIntent.putExtra("id", list.get(0).getId());
                        activity.startActivity(myIntent);
                        activity.finish();
                    }
                });



                builder.setNegativeButton(context.getString(R.string.no), null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PendidikanViewHolder extends RecyclerView.ViewHolder {
        TextView tv_namasekolah, tv_jurusan, tv_tahunmasuk, tv_tahunlulus;
        String key_id, id_pendidikan;
        Button delete_pendidikan;

        public PendidikanViewHolder(View v) {
            super(v);
            tv_namasekolah = (TextView) v.findViewById(R.id.tv_item_nama_sekolah);
            tv_jurusan = (TextView) v.findViewById(R.id.tv_item_jurusan_sekolah);
            tv_tahunmasuk = (TextView) v.findViewById(R.id.tahun_masuk_txt);
            tv_tahunlulus = (TextView) v.findViewById(R.id.tahun_lulus_txt);
            delete_pendidikan = (Button) v.findViewById(R.id.btn_delete_pendidikan);


        }
    }


}
