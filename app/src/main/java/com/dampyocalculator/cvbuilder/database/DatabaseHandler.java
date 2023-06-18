package com.dampyocalculator.cvbuilder.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.dampyocalculator.cvbuilder.template.TemplateFrag;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int database_version = 1;

    //database name
    static final String database_name = "db_cvbuilder";
    //table name
    public static final String table_user = "tb_user";
    public static final String table_contact = "tb_contact";
    public static final String table_pendidikan = "tb_pendidikan";
    public static final String table_skill = "tb_skill";
    public static final String table_bahasa = "tb_bahasa";
    public static final String table_pengalaman = "tb_pengalaman";

    //coloume table profil
    public static final String key_id = "id";
    public static final String key_name = "nama";
    public static final String key_posisi = "posisi";
    public static final String key_profil = "profil";
    public static final String key_image = "image";

    //colome table contact
    public static final String primarykey_id_contact = "contact_id";
    public static final String key_id_contact = "id";
    public static final String key_notlpn = "no_tlpn";
    public static final String key_email = "email";
    public static final String alamat = "alamat";

    //coloume table pendidikan
    public static final String primarykey_id_pendidikan = "id_pendidikan";
    public static final String key_id_pendidikan = "id";
    public static final String key_nama_sekolah = "nama_sekolah";
    public static final String key_nama_jurusan = "nama_jurusan";
    public static final String key_tahun_masuk = "tahun_masuk";
    public static final String key_tahun_lulus = "tahun_lulus";
    public static final String key_keterangan_pendidikan = "keterangan_pendidikan";

    //colome table skill
    public static final String primarykey_id_skill = "id_skill";
    public static final String key_id_skill ="id";
    public static final String nama_skill = "nama_skill";

    //colume table bahasa
    public static final String primarykey_bahasa = "id_bahasa";
    public static final String key_id_bahasa = "id";
    public static final String nama_bahasa = "nama_bahasa";
    public static final String level_bahasa = "level_bahasa";

    //colume table pengalaman
    public static final String primarykey_pengalaman = "id_pengalaman";
    public static final String key_id_perusahaan = "id";
    public static final String nama_perusahaan ="nama_perusahaan";
    public static final String jabatan_peng = "jabatan_perusahan";
    public static final String tgl_mulai_bekerja = "tgl_mulai_bekerja";
    public static final String tgl_selesai_bekerja = "tgl_selesai_bekerja";
    public static final String ket_pengalaman = "ket_pengalaman";


    public DatabaseHandler(Context context){
        super(context, database_name, null, database_version);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        //table_user
        String create_user_table = "CREATE TABLE " + table_user + "("+key_id+" INTEGER PRIMARY KEY autoincrement, "+key_name+" TEXT, "+key_posisi+" TEXT, "+key_profil+" TEXT, "+key_image+" TEXT)";
        //table_contact
        String create_contact_table = "CREATE TABLE " + table_contact + "("+primarykey_id_contact+" INTEGER PRIMARY KEY autoincrement, "+key_id_contact+" INTEGER, "+key_notlpn+" TEXT, "+key_email+" TEXT, "+alamat+" TEXT)";
        //table_pendidikan
        String create_pendidikan = "CREATE TABLE " + table_pendidikan + "("+primarykey_id_pendidikan+" INTEGER PRIMARY KEY autoincrement, "+key_id_pendidikan+" INTEGER, "+key_nama_sekolah+" TEXT, "+key_nama_jurusan+" TEXT, "+key_tahun_masuk+" TEXT, "+key_tahun_lulus+" TEXT, "+key_keterangan_pendidikan+" TEXT)";
        //table_skill
        String create_skill = "CREATE TABLE " + table_skill + "("+primarykey_id_skill+" INTEGER PRIMARY KEY autoincrement, "+key_id_skill+" TEXT, "+nama_skill+" TEXT)";
        //table_bahasa
        String create_bahasa = "CREATE TABLE " + table_bahasa + "("+primarykey_bahasa+" INTEGER PRIMARY KEY autoincrement, "+key_id_bahasa+" INTEGER, "+nama_bahasa+" TEXT, "+level_bahasa+" TEXT)";
        //table_pengalaman
        String create_pengalaman = "CREATE TABLE " + table_pengalaman + "("+primarykey_pengalaman+" INTEGER PRIMARY KEY autoincrement, "+key_id_perusahaan+" INTEGER, "+nama_perusahaan+" TEXT, "+jabatan_peng+" TEXT,"+tgl_mulai_bekerja+" TEXT, "+tgl_selesai_bekerja+" TEXT, "+ket_pengalaman+" TEXT)";


        db.execSQL(create_user_table);
        db.execSQL(create_contact_table);
        db.execSQL(create_pendidikan);
        db.execSQL(create_skill);
        db.execSQL(create_bahasa);
        db.execSQL(create_pengalaman);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
            //drop table profil
            db.execSQL("DROP TABLE IF EXISTS " + table_user);
            onCreate(db);

            //drop table contact
            db.execSQL("DROP TABLE IF EXISTS " + table_contact);
            onCreate(db);

            //drop table pendidikan
            db.execSQL("DROP TABLE IF EXISTS " + table_pendidikan);
            onCreate(db);

            //drop table skill
            db.execSQL("DROP TABLE IF EXISTS " + table_skill);
            onCreate(db);

            //drop table bahsa
            db.execSQL("DROP TABLE IF EXISTS " + table_bahasa);
            onCreate(db);

            db.execSQL("DROP TABLE IF EXISTS " +table_pengalaman);
            onCreate(db);
    }

    public ArrayList<usermodels> getAll(){
        ArrayList <usermodels> usermodelsArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " +table_user ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                usermodels usermodels = new usermodels();
                usermodels.setId(String.valueOf(c.getInt(0)));
                usermodels.setName(c.getString(1));
                usermodels.setPosisi(c.getString(2));
                usermodels.setProfil(c.getString(3));
                usermodels.setImage(c.getString(4));
                // adding to Students list
                usermodelsArrayList.add(usermodels);
            } while (c.moveToNext());
        }
        return usermodelsArrayList;
    }
    public ArrayList<usermodels> getDataUser(String id){
        ArrayList <usermodels> usermodelsArrayList = new ArrayList<>();
        SQLiteDatabase ReadData = this.getReadableDatabase();

        Cursor c =ReadData.rawQuery("SELECT tb_user.id, tb_user.nama, tb_user.posisi, tb_user.profil, tb_user.image, tb_skill.id_skill, tb_skill.nama_skill, tb_contact.no_tlpn, tb_contact.email, tb_contact.alamat FROM tb_user " +
                "LEFT JOIN tb_skill on tb_user.id = tb_skill.id " +
                "LEFT JOIN tb_contact on tb_user.id = tb_contact.id WHERE tb_user.id = "+id, null);
        if (c.moveToFirst()) {
            do {

                usermodels usermodels = new usermodels();
                usermodels.setId(c.getString(0));
                usermodels.setName(c.getString(1));
                usermodels.setPosisi(c.getString(2));
                usermodels.setProfil(c.getString(3));
                usermodels.setImage(c.getString(4));
                usermodels.setNamaskill(c.getString(6));
                usermodels.setNotlpn(c.getString(7));
                usermodels.setEmail(c.getString(8));
                usermodels.setAlamat(c.getString(9));
                usermodelsArrayList.add(usermodels);

            }while (c.moveToNext());
        }
        return usermodelsArrayList;
    }




    public void insert(String name, String posisi, String profil, String image){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "INSERT INTO " +table_user+"("+key_name+","+key_posisi+","+key_profil+","+key_image+")VALUES('"+name+"','"+posisi+"','"+profil+"','"+image+"')";
        database.execSQL(query);
    }

    public void update (int id,String name, String posisi, String profil, String image){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "UPDATE "+table_user+" SET "+key_name+" = '"+name+"',"+key_posisi+" = '"+posisi+"',"+key_profil+" = '"+profil+"',"+key_image+" = '"+image+"' WHERE " +key_id+ " = " +id;
        database.execSQL(query);
    }

    public void delete (String id){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "DELETE FROM " +table_user+ " WHERE " +key_id+ " = " +id;
        database.execSQL(query);
    }
}
