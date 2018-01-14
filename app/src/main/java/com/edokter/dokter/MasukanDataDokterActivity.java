package com.edokter.dokter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edokter.R;
import com.edokter.controller.DataDokter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MasukanDataDokterActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private DatabaseReference database;
    private Button lokasi, simpan;
    private EditText nama, alamat, nomer, informasi;
    private Location mLastlocation;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masukan_data_dokter);

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance().getReference("dokter").child(user.getUid());

        nama = (EditText) findViewById(R.id.etnamainfo);
        alamat = (EditText) findViewById(R.id.etAlamatinfo);
        nomer = (EditText) findViewById(R.id.etNoinfo);
        informasi = (EditText) findViewById(R.id.etinfo);
        lokasi = (Button) findViewById(R.id.btnGetLokasi);
        simpan = (Button) findViewById(R.id.btnSimpan);
        
            database.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue(DataDokter.class)==null){
                        Toast.makeText(MasukanDataDokterActivity.this, "Silahkan isi data dengan sesuai", Toast.LENGTH_SHORT).show();
                    }else {
                        DataDokter dataDokter = dataSnapshot.getValue(DataDokter.class);
                        nama.setText(dataDokter.getNama());
                        alamat.setText(dataDokter.getAlamat());
                        nomer.setText(dataDokter.getNomer());
                        informasi.setText(dataDokter.getInformasi());

                    }
                    }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        lokasi.setOnClickListener(this);
        simpan.setOnClickListener(this);

        setupGoogleAPI();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSimpan) {
            simpandata();
        }
        else if (view.getId() == R.id.btnGetLokasi) {
            if (mLastlocation != null) {
                Toast.makeText(this, "get Lokasi \nlongitude: " + mLastlocation.getLongitude() +
                        "\n latitude: " + mLastlocation.getLatitude(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Lokasi tidak Ditemukan", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void simpandata() {
        try {
            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
            String id = user.getUid();
            String email=user.getEmail().toString();
            String name = nama.getText().toString();
            String addres = alamat.getText().toString();
            String numb = nomer.getText().toString();
            String info = informasi.getText().toString();
            double loni = mLastlocation.getLongitude();
            double lati = mLastlocation.getLatitude();

            DataDokter data = new DataDokter(id, email, name, addres, numb, info, lati, loni);
            database.setValue(data);

            Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Data gagal dimasukan " + e, Toast.LENGTH_SHORT).show();
        }
    }


    private void setupGoogleAPI() {
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop(){
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastlocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastlocation != null) {
            Toast.makeText(this," Connected to Google Location API", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
