package cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dampyocalculator.cvbuilder.R;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.database.DatabaseHandler;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.database.usermodels;
import cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.detail_user.EditSkillActivity;

import java.util.ArrayList;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillViewHolder> {

    Activity activity;
    Context context;
    private ArrayList<usermodels> list = new ArrayList<>();
    private DatabaseHandler databaseHandler;
    RecyclerView mRecyclerView;


    public SkillAdapter(Activity activity, Context context){
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
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skill, parent, false);
        return new SkillViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
            holder.nama_skill.setText(list.get(position).getNamaskill());

            holder.key_id = list.get(position).getId();
            holder.primarykeyskill = list.get(position).getPrimarykeyskill();

            holder.hapus_skill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseHandler = new DatabaseHandler(context);
                    SQLiteDatabase delete = databaseHandler.getWritableDatabase();

                    String Query = "DELETE FROM " +DatabaseHandler.table_skill+ " WHERE " +DatabaseHandler.primarykey_id_skill+ " = " +holder.primarykeyskill;;
                    delete.execSQL(Query);
                    Toast.makeText(activity, "Hapus berhasil!", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(activity, EditSkillActivity.class);
                    myIntent.putExtra("id", list.get(0).getId());
                    activity.startActivity(myIntent);
                    activity.finish();

                }
            });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SkillViewHolder extends RecyclerView.ViewHolder {
        TextView nama_skill;
        String  primarykeyskill, key_id;
        ImageButton hapus_skill;

        public SkillViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_skill = (TextView) itemView.findViewById(R.id.tv_item_skill);
            hapus_skill = (ImageButton) itemView.findViewById(R.id.hapus_skill);

        }
    }
}
