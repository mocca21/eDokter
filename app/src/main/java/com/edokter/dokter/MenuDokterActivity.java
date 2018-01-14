package com.edokter.dokter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.edokter.R;
import com.edokter.menu.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuDokterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private TextView logout,masukandata,listpasien,lihatdata,Count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dokter);

        lihatdata=(TextView)findViewById(R.id.tvLihatData);
        listpasien=(TextView)findViewById(R.id.tvLihatAntrian);
        masukandata=(TextView)findViewById(R.id.tvmasukanData);
        logout=(TextView)findViewById(R.id.tvlogout);
        Count=(TextView)findViewById(R.id.tvCount);

        auth=FirebaseAuth.getInstance();

        Count.setOnClickListener(this);
        lihatdata.setOnClickListener(this);
        logout.setOnClickListener(this);
        masukandata.setOnClickListener(this);
        listpasien.setOnClickListener(this);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    startActivity(new Intent(MenuDokterActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                startActivity(new Intent(MenuDokterActivity.this, LoginActivity.class));
                finish();
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.tvlogout){
            auth.signOut();

            FirebaseAuth.AuthStateListener authListtener=new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user=firebaseAuth.getCurrentUser();
                    if (user==null){
                        startActivity(new Intent(MenuDokterActivity.this,HomeActivity.class));
                        finish();
                    }
                }
            };
        }
        else if (view.getId()==R.id.tvmasukanData){
            Intent intent=new Intent(MenuDokterActivity.this,MasukanDataDokterActivity.class);
            startActivity(intent);
        }

        else if (view.getId()==R.id.tvLihatAntrian){
            startActivity(new Intent(MenuDokterActivity.this,ListPasien.class));
        }

        else if (view.getId()==R.id.tvLihatData){
            startActivity(new Intent(MenuDokterActivity.this,RekapData.class));
        }

        else if (view.getId()==R.id.tvCount){
            startActivity(new Intent(MenuDokterActivity.this, CountAntrian.class));
        }
    }

    public void onBackPressed(){
        startActivity(new Intent(MenuDokterActivity.this,HomeActivity.class));
        finish();
    }
}
