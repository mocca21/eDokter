package com.edokter.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.edokter.R;
import com.edokter.controller.DataDokter;
import com.edokter.controller.ListDaftarDokter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DaftarDokter extends AppCompatActivity {

    private DatabaseReference database;
    ListView ListDokter;
    List<DataDokter> dokters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_dokter);

        database = FirebaseDatabase.getInstance().getReference("dokter");
        ListDokter = (ListView) findViewById(R.id.lvDokter);
        dokters = new ArrayList<>();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dokters.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getValue(DataDokter.class) == null) {
                        Toast.makeText(DaftarDokter.this, "List Kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        DataDokter dok = snapshot.getValue(DataDokter.class);
                        dokters.add(dok);
                    }
                }
                ListDaftarDokter adapter = new ListDaftarDokter(DaftarDokter.this, dokters);
                ListDokter.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ListDokter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataDokter dokter=dokters.get(i);
                Intent antrian=new Intent(DaftarDokter.this,LihatAntrian.class);
                Bundle bundle=new Bundle();
                bundle.putString("UID",dokter.getId());
                antrian.putExtra("id",bundle);
                startActivity(antrian);
            }
        });
    }
}
