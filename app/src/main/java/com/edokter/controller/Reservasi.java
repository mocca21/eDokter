package com.edokter.controller;

public class Reservasi {
    String noktp;
    String nama;
    String alamat;
    String nohp;
    int nourut;

    public Reservasi(){

    }

    public int getNourut() {
        return nourut;
    }

    public void setNourut(int nourut) {
        this.nourut = nourut;
    }

    public String getNoktp() {
        return noktp;
    }

    public void setNoktp(String noktp) {
        this.noktp = noktp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    @Override
    public String toString() {
        return "" + nama + "\n" +
                "" + alamat + "\n" +
                "" + noktp + "\n" +
                "" + nohp +"\n"+
                ""+ nourut;
    }

    public Reservasi(String nm,String al,String nktp,String nhp,int nour){
        nama=nm;
        alamat=al;
        noktp=nktp;
        nohp=nhp;
        nourut=nour;
    }

}
