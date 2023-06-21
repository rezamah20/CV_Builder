package cvresume.resumebuildercvmakerenglish.curriculumvitaebahasaindonesiainggris.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

import com.dampyocalculator.cvbuilder.R;

public class ForbidenCharAdapter implements InputFilter {

    private String blockCharacterSet = "'\"";
    Context context;
    Activity activity;


    public ForbidenCharAdapter(Context ctx, Activity act){
        this.activity = act;
        this.context = ctx;
    }

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
        if (charSequence != null && blockCharacterSet.contains(("" + charSequence))) {
            Toast.makeText(context, context.getString(R.string.forbidenchar), Toast.LENGTH_LONG).show();
            return "";
        }
        return null;
    }
}
