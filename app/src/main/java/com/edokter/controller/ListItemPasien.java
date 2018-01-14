package com.edokter.controller;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.edokter.R;

import java.util.List;

public class ListItemPasien extends ArrayAdapter<Reservasi>{
    private Activity context;
    List<Reservasi> reservasi;

    public ListItemPasien(Activity context, List<Reservasi> reservasi) {
        super(context, R.layout.list_pasien_item,reservasi);
        this.context=context;
        this.reservasi=reservasi;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.list_pasien_item,null,true);

        TextView nama=(TextView)listViewItem.findViewById(R.id.tvNama);
        TextView alamat=(TextView)listViewItem.findViewById(R.id.tvAlamat);

        Reservasi reservasi1=reservasi.get(position);
        nama.setText(reservasi1.getNama());
        alamat.setText(reservasi1.getAlamat()
                +"\n"+reservasi1.getNoktp()
                +"\n"+reservasi1.getNohp());

        return listViewItem;
    }
}
