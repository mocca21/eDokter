package com.edokter.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.edokter.R;
import com.edokter.dokter.LoginActivity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView logDokter,CariLokasi,LihatNoAntrian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        logDokter=(TextView)findViewById(R.id.tvloginDokter);
        CariLokasi=(TextView)findViewById(R.id.tvCariLokasi);
        LihatNoAntrian=(TextView)findViewById(R.id.tvLihatAntrian);

        CariLokasi.setOnClickListener(this);
        logDokter.setOnClickListener(this);
        LihatNoAntrian.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.tvloginDokter){
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }
        else if(view.getId()==R.id.tvCariLokasi){
            startActivity(new Intent(HomeActivity.this,MapsActivity.class));
        }
        else if(view.getId()==R.id.tvLihatAntrian){
            startActivity(new Intent(HomeActivity.this,DaftarDokter.class));
        }
    }
}
