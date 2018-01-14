package com.edokter.controller;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.edokter.R;

import java.util.List;

/**
 * Created by Arief Hiadayat on 08/01/2018.
 */

public class ListDaftarDokter extends ArrayAdapter<DataDokter> {
    private Activity context;
    List<DataDokter> dokter;

    public ListDaftarDokter(Activity context, List<DataDokter> dokter) {
        super(context, R.layout.list_pasien_item,dokter);
        this.context=context;
        this.dokter=dokter;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.list_pasien_item,null,true);

        TextView nama=(TextView)listViewItem.findViewById(R.id.tvNama);
        TextView alamat=(TextView)listViewItem.findViewById(R.id.tvAlamat);

        DataDokter dokter1=dokter.get(position);
        nama.setText(dokter1.getNama());
        alamat.setText(dokter1.getAlamat());

        return listViewItem;
    }
}
