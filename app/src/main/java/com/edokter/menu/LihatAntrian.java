package com.edokter.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.edokter.R;
import com.edokter.controller.Reservasi;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LihatAntrian extends AppCompatActivity implements View.OnClickListener {

    private Button Cari;
    private EditText NomorKTP;
    private TextView Nama,NomerUrut,NomorAtrian;
    private DatabaseReference database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_antrian);

        Cari=(Button)findViewById(R.id.btnCari);
        NomorKTP=(EditText)findViewById(R.id.etCari);
        Nama=(TextView)findViewById(R.id.tvNama);
        NomerUrut=(TextView)findViewById(R.id.tvNomerUrut);
        NomorAtrian=(TextView)findViewById(R.id.tvnomor);

        Cari.setOnClickListener(this);

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new  SimpleDateFormat("ddMMyyyy");
        String tanggal=sdf.format(cal.getTime());
        String bundle=getIntent().getBundleExtra("id").getString("UID");
        database= FirebaseDatabase.getInstance().getReference("reservasi").child(bundle).child(tanggal);
        databaseReference=FirebaseDatabase.getInstance().getReference("countering").child(bundle);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()==null){

                }else {
                    NomorAtrian.setText("Antrian Sekarang : "+dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnCari){
            tampilData();
        }
    }

    private void tampilData(){
        String nomor=NomorKTP.getText().toString();
        database.child(nomor).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Reservasi.class)==null){
                    Toast.makeText(LihatAntrian.this, "Anda Tidak Terdaftar", Toast.LENGTH_SHORT).show();
                }else {
                    Reservasi reservasi=dataSnapshot.getValue(Reservasi.class);
                    Nama.setText(reservasi.getNama());
                    NomerUrut.setText(String.valueOf(reservasi.getNourut()));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
