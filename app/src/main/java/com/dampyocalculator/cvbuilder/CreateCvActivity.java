package com.dampyocalculator.cvbuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.dampyocalculator.cvbuilder.adapter.UserAdapter;
import com.dampyocalculator.cvbuilder.database.DatabaseHandler;
import com.dampyocalculator.cvbuilder.database.usermodels;

import java.util.ArrayList;
import java.util.List;

public class CreateCvActivity extends AppCompatActivity {
    Spinner choose_profil;
    private Button test_nama;
    private ArrayList<usermodels> list = new ArrayList<>();
    private UserAdapter userAdapter;
    private DatabaseHandler databaseHandler;
    private List<String> id_user;
    private List<String> nama_user;
    private List<String> jabatan;
    private String id, nama, jabatan_txt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cv);

        choose_profil = (Spinner) findViewById(R.id.choose_profil);
        test_nama = (Button) findViewById(R.id.test_nama);
        userAdapter = new UserAdapter(this, this);
        databaseHandler = new DatabaseHandler(this);
        list.clear();
        list = databaseHandler.getAll();


        getData();
        test_nama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CreateCvActivity.this, "ID Dipilih " +id, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData(){

        id_user = new ArrayList<String>();
        nama_user = new ArrayList<String>();
        jabatan = new ArrayList<String>();


        for (int i = 0; i < list.size(); i++) {
            id_user.add(list.get(i).getId());
            nama_user.add(list.get(i).getNama()+" - " +list.get(i).getPosisi());
            System.out.println("data 2 "+list.get(i).getNama());

        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(CreateCvActivity.this,
                android.R.layout.simple_spinner_item, nama_user);
        spinnerAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        choose_profil.setAdapter(spinnerAdapter);
        choose_profil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                id = id_user.get(position);
                nama = nama_user.get(position);
                Toast.makeText(CreateCvActivity.this, "Anda Memilih ID: "+id+", Nama: "+nama, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /*String selectQuery = "SELECT * FROM " +DatabaseHandler.table_user;
        SQLiteDatabase db = databaseHandler.getReadableDatabase();
        ArrayList<String>labels= new ArrayList<>();
        Cursor cursor=db.rawQuery(selectQuery,null);
        id = new String[cursor.getCount()];
        nama = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (i = 0; i<nama.length;i++)
        {
            id[i] = cursor.getString(0);
            nama[i] = cursor.getString(1);
            testnamatxt = nama[i];
            test_id = id[i];
            labels.add(test_id + testnamatxt);
            cursor.moveToNext();
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        choose_profil.setAdapter(dataAdapter);
*/
       // if (cursor.moveToFirst()){
        //    do{
        //        labels.add(cursor.getString(1)+" - "+cursor.getString(2));
        //    }while (cursor.moveToNext());
        //}
        //cursor.close();
        //db.close();
        //return labels;
    }

}