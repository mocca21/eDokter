package com.edokter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edokter.controller.Reservasi;
import com.edokter.menu.HomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReservasiActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText namaDokter,Nama,Alamat,NomerKtp,NoTelp;
    private Button btnDaftar;
    private DatabaseReference database;
    private DatabaseReference dataCounter;
    String bundle;
    String tanggal;
    String angka;
    int count=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservasi);

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf=new  SimpleDateFormat("ddMMyyyy");
        tanggal=sdf.format(cal.getTime());

        bundle=getIntent().getBundleExtra("reservasi").getString("nama");
        database= FirebaseDatabase.getInstance().getReference("reservasi").child(bundle).child(tanggal);
        dataCounter=FirebaseDatabase.getInstance().getReference("counter").child(bundle).child(tanggal);

        namaDokter=(EditText)findViewById(R.id.etNamaDokter);
        Nama=(EditText)findViewById(R.id.etNamaPasien);
        Alamat=(EditText)findViewById(R.id.etAlamatPasien);
        NomerKtp=(EditText)findViewById(R.id.etnoKTP);
        NoTelp=(EditText)findViewById(R.id.etNoHp);
        btnDaftar=(Button)findViewById(R.id.btnDaftar);

        namaDokter.setText("Dokter");
        btnDaftar.setOnClickListener(this);

        dataCounter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        if (snapshot.getValue()==null){
                            count=1;
                        }else {
                        count=Integer.valueOf(snapshot.getValue().toString());
                        count++;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnDaftar){
            simpandata();
            startActivity(new Intent(ReservasiActivity.this, HomeActivity.class));
            finish();
        }
    }

    private void simpandata(){
        try{
            String nama=Nama.getText().toString();
            String alamat=Alamat.getText().toString();
            String noktp= NomerKtp.getText().toString();
            String nohp=NoTelp.getText().toString();
            if (TextUtils.isEmpty(nama)&&TextUtils.isEmpty(alamat)&&TextUtils.isEmpty(nohp)&&TextUtils.isEmpty(noktp)){
                Toast.makeText(this, "Data Harap Dimasukan", Toast.LENGTH_SHORT).show();
            }else {
                dataCounter.child(tanggal).setValue(count);
                Reservasi reservasi=new Reservasi(nama,alamat,noktp,nohp,count);
                database.child(noktp).setValue(reservasi);
                Toast.makeText(this, "Berhasil Terdaftar", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            Toast.makeText(this, "Gagal Terdaftar", Toast.LENGTH_SHORT).show();
        }
    }
}
