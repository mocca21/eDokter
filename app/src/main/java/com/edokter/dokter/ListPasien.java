package com.edokter.dokter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.edokter.R;
import com.edokter.controller.ListItemPasien;
import com.edokter.controller.Reservasi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListPasien extends AppCompatActivity {

    private DatabaseReference database;
    ListView ListPasien;
    List<Reservasi> Reservasis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pasien);

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new  SimpleDateFormat("ddMMyyyy");
        String tanggal=sdf.format(cal.getTime());

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        database= FirebaseDatabase.getInstance().getReference("reservasi").child(user.getUid()).child(tanggal);
        ListPasien=(ListView)findViewById(R.id.lvPasien);
        Reservasis=new ArrayList<>();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Reservasis.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if(snapshot.getValue(Reservasi.class)==null){
                        Toast.makeText(ListPasien.this, "List Kosong", Toast.LENGTH_SHORT).show();
                    }else{
                    Reservasi reservasi=snapshot.getValue(Reservasi.class);
                    Reservasis.add(reservasi);
                    }
                }
                ListItemPasien adapter=new ListItemPasien(ListPasien.this,Reservasis);
                ListPasien.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
