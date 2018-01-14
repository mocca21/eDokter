package com.edokter.dokter;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Locale;

public class RekapData extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public DatePickerDialog datePickerDialog;
    public SimpleDateFormat dateFormater;
    private Button PilihTanggal;
    private ListView listData;
    List<Reservasi> Reservasis;
    private DatabaseReference database;
    public String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap_data);

        dateFormater=new SimpleDateFormat("ddMMyyyy", Locale.US);
        PilihTanggal=(Button)findViewById(R.id.btnPilih);
        listData=(ListView)findViewById(R.id.lvRekap);

        PilihTanggal.setOnClickListener(this);
        listData.setOnItemClickListener(this);

        Reservasis=new ArrayList<>();
    }

    void Show(){
        Calendar calendar=Calendar.getInstance();
        datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int montOfYear, int dayOfMonth) {
                Calendar newDate=Calendar.getInstance();
                newDate.set(year,montOfYear,dayOfMonth);
                date =dateFormater.format(newDate.getTime());
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                database= FirebaseDatabase.getInstance().getReference("reservasi").child(user.getUid()).child(date);
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Reservasis.clear();
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            if(snapshot.getValue(Reservasi.class)==null){
                                Toast.makeText(RekapData.this, "List Kosong", Toast.LENGTH_SHORT).show();
                            }else{
                                Reservasi reservasi=snapshot.getValue(Reservasi.class);
                                Reservasis.add(reservasi);
                            }
                        }
                        ListItemPasien adapter=new ListItemPasien(RekapData.this,Reservasis);
                        listData.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnPilih){
            Show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
