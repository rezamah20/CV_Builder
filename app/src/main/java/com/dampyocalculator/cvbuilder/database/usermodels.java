package com.dampyocalculator.cvbuilder.database;

public class usermodels {
    String id;
    String nama;
    String posisi;
    String profil;
    String image;

    String primarykeypendidikanar;
    String namasekolah;
    String namajurusan;
    String tahunmasuk;
    String tahunlulus;
    String keteranganpendidikan;

    String primarykeyskill;
    String namaskill;

    String primarykeybahasa;
    String keyidbahasa;
    String nama_bahasa;
    String level_bahasa;

    // constructor
    //public usermodels(String id, String posisi, String nama){
    //    this.id = id;
    //    this.nama = nama;
    //    this.posisi = posisi;
    //}

    // setter and getter

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getNama(){
        return nama;
    }

    public void setName(String name) {
        this.nama = name;
    }

    public String getPosisi(){
        return posisi;
    }

    public void setPosisi(String posisi){
        this.posisi = posisi;
    }

    public String getProfil(){
        return profil;
    }

    public void setProfil(String profil){
        this.profil = profil;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public String getPrimarykeypendidikanar() {
        return primarykeypendidikanar;
    }

    public void setPrimarykeypendidikanar(String primarykeypendidikanar) {
        this.primarykeypendidikanar = primarykeypendidikanar;
    }

    public String getNamasekolah() {
        return namasekolah;
    }

    public void setNamasekolah(String namasekolah) {
        this.namasekolah = namasekolah;
    }

    public String getNamajurusan() {
        return namajurusan;
    }

    public void setNamajurusan(String namajurusan) {
        this.namajurusan = namajurusan;
    }

    public String getTahunmasuk() {
        return tahunmasuk;
    }

    public void setTahunmasuk(String tahunmasuk) {
        this.tahunmasuk = tahunmasuk;
    }

    public String getTahunlulus() {
        return tahunlulus;
    }

    public void setTahunlulus(String tahunlulus) {
        this.tahunlulus = tahunlulus;
    }

    public String getKeteranganpendidikan() {
        return keteranganpendidikan;
    }

    public void setKeteranganpendidikan(String keteranganpendidikan) {
        this.keteranganpendidikan = keteranganpendidikan;
    }

    public String getPrimarykeyskill() {
        return primarykeyskill;
    }

    public void setPrimarykeyskill(String primarykeyskill) {
        this.primarykeyskill = primarykeyskill;
    }

    public String getNamaskill() {
        return namaskill;
    }

    public void setNamaskill(String namaskill) {
        this.namaskill = namaskill;
    }

    public String getPrimarykeybahasa() {
        return primarykeybahasa;
    }

    public void setPrimarykeybahasa(String primarykeybahasa) {
        this.primarykeybahasa = primarykeybahasa;
    }

    public String getKeyidbahasa() {
        return keyidbahasa;
    }

    public void setKeyidbahasa(String keyidbahasa) {
        this.keyidbahasa = keyidbahasa;
    }

    public String getNama_bahasa() {
        return nama_bahasa;
    }

    public void setNama_bahasa(String nama_bahasa) {
        this.nama_bahasa = nama_bahasa;
    }

    public String getLevel_bahasa() {
        return level_bahasa;
    }

    public void setLevel_bahasa(String level_bahasa) {
        this.level_bahasa = level_bahasa;
    }
}
