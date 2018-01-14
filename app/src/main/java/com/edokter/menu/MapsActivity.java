package com.edokter.menu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.edokter.R;
import com.edokter.ReservasiActivity;
import com.edokter.controller.DataDokter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private Location mLastlocation;
    private GoogleApiClient mGoogleApiClient;
    private List<DataDokter> mListMarker=new ArrayList<>();
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setupGoogleAPI();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        getAllDataLocation();
    }

    private void getAllDataLocation(){
        database= FirebaseDatabase.getInstance().getReference("dokter");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mListMarker.clear();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    DataDokter dataDokter=postSnapshot.getValue(DataDokter.class);
                    mListMarker.add(dataDokter);
                }

                for(int i=0;i<mListMarker.size();i++){
                    LatLng lokasi= new LatLng(mListMarker.get(i).getLatitude(),mListMarker.get(i).getLongitude());
                    mMap.addMarker(new MarkerOptions().position(lokasi)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                            .title(mListMarker.get(i).getNama()
                            +"\n"+mListMarker.get(i).getAlamat()
                            +"\n"+mListMarker.get(i).getNomer()
                            +"\n"+mListMarker.get(i).getInformasi()).snippet(mListMarker.get(i).getId()));
                    mMap.setInfoWindowAdapter(iwa);
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Intent intent=new Intent(MapsActivity.this, ReservasiActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("nama",marker.getSnippet());
                            intent.putExtra("reservasi",bundle);
                            startActivity(intent);
                        }
                    });
                    //LatLng mylokasi=new LatLng(mLastlocation.getLatitude(),mLastlocation.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi,11.0f));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    GoogleMap.InfoWindowAdapter iwa=new GoogleMap.InfoWindowAdapter() {
        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View view=getLayoutInflater().inflate(R.layout.infowindow,null);
            TextView title=(TextView)view.findViewById(R.id.tvTitle);
            TextView alamat=(TextView)view.findViewById(R.id.tvAlamat);
            title.setText(marker.getTitle());
            alamat.setText(marker.getSnippet());
            return view;
        }
    };

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
