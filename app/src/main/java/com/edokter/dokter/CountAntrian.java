package com.edokter.dokter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.edokter.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CountAntrian extends AppCompatActivity implements View.OnClickListener {

    private Button Mulai,Next,Reset;
    private TextView Nomer;
    private DatabaseReference database;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_antrian);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        database= FirebaseDatabase.getInstance().getReference("countering").child(user.getUid());
        Mulai=(Button)findViewById(R.id.btnMulai);
        Next=(Button)findViewById(R.id.btnNext);
        Reset=(Button)findViewById(R.id.btnReset);
        Nomer=(TextView)findViewById(R.id.tvNomer);

        Mulai.setOnClickListener(this);
        Next.setOnClickListener(this);
        Reset.setOnClickListener(this);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue()==null){

                    }else {
                        Nomer.setText(dataSnapshot.getValue().toString());
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        String nomer=Nomer.getText().toString();
        if (nomer.contains("0")){
            tombol(true,false,false);
        }
        else {
            tombol(false,true,true);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btnMulai){
            database.setValue(++count);
            Nomer.setText(String.valueOf(count));
            tombol(false,true,true);
        }
        else if (view.getId()==R.id.btnNext){
            count=Integer.valueOf(Nomer.getText().toString());
            database.setValue(++count);
            Nomer.setText(String.valueOf(count));
        }
        else if (view.getId()==R.id.btnReset){
            database.setValue(0);
            Nomer.setText("0");
            count=0;
            tombol(true,false,false);
        }
    }

    private void tombol(boolean mulai,boolean next,boolean reset){
        Mulai.setEnabled(mulai);
        Next.setEnabled(next);
        Reset.setEnabled(reset);
    }
}
