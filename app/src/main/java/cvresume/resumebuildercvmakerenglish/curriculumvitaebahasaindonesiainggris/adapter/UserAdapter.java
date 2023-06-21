package cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.DataUserActivity;
import com.dampyocalculator.cvbuilder.R;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.database.DatabaseHandler;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.database.usermodels;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.list_user;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    Activity activity;
    Context context;
    private ArrayList<usermodels> list = new ArrayList<>();
    private DatabaseHandler databaseHandler;
    String id, profil, foto;


    public UserAdapter(Activity activity, Context context) {
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
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //usermodels usermodels = list.get(position);
        id = list.get(position).getId();
        holder.tv_name.setText(list.get(position).getNama());
        holder.tv_posisi.setText(list.get(position).getPosisi());
        profil = list.get(position).getProfil();
        foto = list.get(position).getImage();
        holder.adsAdapter.loadInter();


        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.adsAdapter.showInterstitial(DataUserActivity.class,list.get(position).getId());
                //Intent intent = new Intent(activity, DataUserActivity.class);
               // intent.putExtra("id", list.get(position).getId());
                //intent.putExtra("nama", list.get(position).getNama());
                //intent.putExtra("posisi", list.get(position).getPosisi());
               // intent.putExtra("profil", list.get(position).getProfil());
               // intent.putExtra("foto", list.get(position).getImage());
               // activity.startActivity(intent);
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("di klik", "delete");
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                builder.setTitle(context.getString(R.string.delete_conf)+" "+list.get(position).getNama());
                builder.setMessage(context.getString(R.string.put_delete_conf));

                builder.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseHandler = new DatabaseHandler(context);
                        databaseHandler.delete(id);
                        Toast.makeText(activity, context.getString(R.string.dialog_succ_delete), Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(activity, list_user.class);
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

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_posisi;
        Button btn_edit, btn_delete;
        AdsAdapter adsAdapter;;


        public UserViewHolder(@NonNull View itemView) {

            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_item_nama);
            tv_posisi = (TextView) itemView.findViewById(R.id.tv_item_posisi);

            btn_edit = (Button) itemView.findViewById(R.id.btn_edit);
            btn_delete = (Button) itemView.findViewById(R.id.btn_delete);

            adsAdapter = new AdsAdapter(activity, context);
           // Random rnd = new Random();
            //int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

            //cardView = (CardView) itemView.findViewById(R.id.cv_item_user);
           // cardView.setCardBackgroundColor(color);

        }
    }
}
