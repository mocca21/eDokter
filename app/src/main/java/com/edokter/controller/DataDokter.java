package com.edokter.controller;

import java.io.Serializable;

/**
 * Created by Arief Hiadayat on 12/12/2017.
 */

public class DataDokter implements Serializable {
    private String id;
    private String email;
    private String nama;
    private String alamat;
    private String nomer;
    private String informasi;
    private double latitude,longitude;

    public DataDokter(){

    }

    public String getId(){
        return id;
    }

    public void setId(){
        this.id=id;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(){
        this.email=email;
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

    public String getNomer() {
        return nomer;
    }

    public void setNomer(String nomer) {
        this.nomer = nomer;
    }

    public String getInformasi() {
        return informasi;
    }

    public void setInformasi(String informasi) {
        this.informasi = informasi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString(){
        return ""+nama+"\n"+
                ""+alamat+"\n"+
                ""+nomer+"\n"+
                ""+informasi+"\n"+
                ""+latitude+"\n"+
                ""+longitude;
    }

    public DataDokter(String idi,String em,String nm,String al,String no,String in,double la,double lo){
        id=idi;
        email=em;
        nama=nm;
        alamat=al;
        nomer=no;
        informasi=in;
        latitude=la;
        longitude=lo;
    }
}
