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
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.detail_user.EditBahasaActivity;

import java.util.ArrayList;

public class BahasaAdapter extends RecyclerView.Adapter<BahasaAdapter.BahasaViewHolder> {
    Activity activity;
    Context context;
    private ArrayList<usermodels> list = new ArrayList<>();
    private DatabaseHandler databaseHandler;
    RecyclerView mRecyclerView;

    public BahasaAdapter(Activity activity, Context context){
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
    public BahasaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bahasa, parent, false);
        return new BahasaViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull BahasaViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_namabahasa.setText(list.get(position).getNama_bahasa());
        holder.tv_levelbahasa.setText(list.get(position).getLevel_bahasa());
        holder.id_bahasa = list.get(position).getPrimarykeybahasa();
        holder.key_id = list.get(position).getId();

        holder.delete_bahasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle(context.getString(R.string.delete_conf)+" "+list.get(position).getNama_bahasa());
                builder.setMessage(context.getString(R.string.put_delete_conf));

                builder.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseHandler = new DatabaseHandler(context);
                        SQLiteDatabase delete = databaseHandler.getWritableDatabase();
                        String query = "DELETE FROM " +DatabaseHandler.table_bahasa+ " WHERE " +DatabaseHandler.primarykey_bahasa+ " = " +holder.id_bahasa;
                        delete.execSQL(query);
                        Toast.makeText(activity, context.getString(R.string.dialog_succ_delete), Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(activity, EditBahasaActivity.class);
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

    public class BahasaViewHolder extends RecyclerView.ViewHolder {
        TextView tv_namabahasa, tv_levelbahasa;
        String key_id, id_bahasa;
        Button delete_bahasa;

        public BahasaViewHolder(@NonNull View v) {
            super(v);
            tv_namabahasa = (TextView) v.findViewById(R.id.tv_item_bahasa);
            tv_levelbahasa = (TextView) v.findViewById(R.id.tv_item_level_bahasa);

            delete_bahasa = (Button) v.findViewById(R.id.btn_delete_bahasa);
        }
    }
}
