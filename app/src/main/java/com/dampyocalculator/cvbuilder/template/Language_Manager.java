package com.dampyocalculator.cvbuilder.template;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.dampyocalculator.cvbuilder.R;

import java.util.Locale;

public class Language_Manager {
    private Context context;
    AlertDialog alertDialog;
    public Language_Manager(Context ctx){
        context = ctx;
    }

    public void updateResource(String code){
        Locale locale = new Locale(code);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
      //  alertDialog = langFormDiaglog();
      //  alertDialog.show();
    }


    private AlertDialog langFormDiaglog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(context.getString(R.string.choose_lang_title));
        builder.setMessage(context.getString(R.string.choose_lang_message));

        builder.setPositiveButton(context.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return builder.create();
    }
}
