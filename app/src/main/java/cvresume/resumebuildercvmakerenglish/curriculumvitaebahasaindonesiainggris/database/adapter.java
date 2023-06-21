package cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.database;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dampyocalculator.cvbuilder.R;

import java.util.List;

public class adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<usermodels> lists;

    public adapter(Activity activity, List<usermodels> lists){
        this.activity =activity;
        this.lists = lists;

    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int i) {
        return lists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (inflater == null){
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null & inflater != null){
            view = inflater.inflate(R.layout.list_profil, null);
        }
        if (view != null) {
            TextView nama = view.findViewById(R.id.nama);
            TextView posisi = view.findViewById(R.id.posisi);
            usermodels usermodels = lists.get(i);
            nama.setText(usermodels.nama);
            posisi.setText(usermodels.posisi);
        }
        return view;
    }
}
