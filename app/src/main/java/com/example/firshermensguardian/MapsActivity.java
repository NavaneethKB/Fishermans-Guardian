package com.example.firshermensguardian;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
private  static final long START_TIME=1000;
    private GoogleMap mMap;
    Geocoder geocoder;
     DatabaseReference rootRef;
private Chronometer chronometer;
private long pauseOffset;
private boolean running=false;
Button mapStart,mapAlert;
FusedLocationProviderClient fusedLocationProviderClient;
LocationRequest locationRequest;
String checkforship="notaship";
    int flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        LinearLayout linearLayout;
        LinearLayout linearLayout1;
        checkforship=getIntent().getStringExtra("check_for_ship");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationRequest=locationRequest.create();
        locationRequest.setInterval(500);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        geocoder=new Geocoder(this);
        chronometer=findViewById(R.id.timer);
        mapStart=findViewById(R.id.map_start);
        mapAlert=findViewById(R.id.map_alert);
    linearLayout1=findViewById(R.id.help_details);
//    String content="New Climate Alert Issued!Stay Alert!!";
//        NotificationCompat.Builder builder=new NotificationCompat.Builder(MapsActivity.this)
//                .setSmallIcon(R.drawable.google_icon)
//                .setContentTitle("Fishermens's Guardian")
//                .setContentText(content)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setAutoCancel(true);
//        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0,builder.build());
        String title="Fishermem's Guardian";
        String body="New Climate Alert Issued!STAY ALERT";
        final String channel_ID="HEADS_UP_NOTIFICATION";
        NotificationChannel channel=new NotificationChannel(
                channel_ID,
                "MyNotification",
                NotificationManager.IMPORTANCE_HIGH);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification=new Notification.Builder(this,channel_ID)
                .setContentTitle(title)
                .setContentText(body)

                .setSmallIcon(R.drawable.google_icon)
                .setAutoCancel(true);
        rootRef=  FirebaseDatabase.getInstance().getReference().child("AdminAlerts");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null) {
                    NotificationManagerCompat.from(MapsActivity.this).notify(1, notification.build());
                }else{
                    Log.d("error","notification_error");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

      if(checkforship==null){           mapStart.setVisibility(View.VISIBLE);
           linearLayout1.setVisibility(View.INVISIBLE);
      }
       if(checkforship!=null){
            mapStart.setVisibility(View.INVISIBLE);
          linearLayout1.setVisibility(View.VISIBLE);
       }
             linearLayout=findViewById(R.id.map_details);
        mapAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SendAlertActivity.class));
            }
        });
        mapStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running=!running;
                //Toast.makeText(MapsActivity.this, mapStart.getText().toString(), Toast.LENGTH_SHORT).show();
              if(running==true){
             //     Toast.makeText(MapsActivity.this, mapStart.getText().toString(), Toast.LENGTH_SHORT).show();
                  mapStart.setText("Stop");
                  startTimer(view);
                  linearLayout.setVisibility(View.VISIBLE);

              }else{
                  mapStart.setText("Start");
                  linearLayout.setVisibility(View.INVISIBLE);
              }

            }
        });
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.Home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Weather:
                        startActivity(new Intent(getApplicationContext(), WeatherActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.Alerts:
                        startActivity(new Intent(getApplicationContext(), AlertsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.User:
                        startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
public void startTimer(View v){
       if(running==true){
           chronometer.setBase(SystemClock.elapsedRealtime());
           chronometer.start();

       }
}
public void stopTimer(View v){
        if(running){
            chronometer.stop();


        }
}


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setMapToolbarEnabled(false);
  LatLng border1,border2,border3,border4,border5;
        // Add a marker in Sydney and move the camera
       LatLng latLng=new LatLng(8.123867,77.329698);
       border1=new LatLng(4.231969,76.603886);
       border2=new LatLng(8.516869,78.955425);
       border3=new LatLng(8.835924,79.5067987);
       border4=new LatLng(9.7911393,79.600922);
       border5=new LatLng(10.420523,80.891916);

       Polyline border=mMap.addPolyline(new PolylineOptions()
       .add(border1,border2)
        .add(border2,border3)
        .add(border3,border4)
               .add(border4,border5)
               .width(3)
               .color(Color.WHITE)

       );
       if(checkforship==null) {
           MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Your Ship");
           mMap.addMarker(markerOptions);
           CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
           mMap.animateCamera(cameraUpdate);
       }
 LatLng LatLng2=new LatLng(8.12234,77.33396);

          //  geocoder.getFromLocation(8.122344,77.333062,2);
        if(checkforship!=null) {
            //
           // 8.087025, 77.424960
            latLng=new LatLng(8.120948,77.329933);
            LatLng2=new LatLng(8.122107,77.334355);
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Your Ship");
            mMap.addMarker(markerOptions);
            MarkerOptions markerOptions2 = new MarkerOptions().position(LatLng2).title("Raj's Ship");
            mMap.addMarker(markerOptions2);
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(8.120948, 77.329933), new LatLng(8.122107, 77.334355))

                    .width(5)
                    .color(Color.RED));
            CameraUpdate cameraUpdate2 = CameraUpdateFactory.newLatLngZoom(LatLng2, 15);
            mMap.animateCamera(cameraUpdate2);
        }


    }
    private void startLocationUpdates(){}
    private void stopLocationUpdates(){}
}