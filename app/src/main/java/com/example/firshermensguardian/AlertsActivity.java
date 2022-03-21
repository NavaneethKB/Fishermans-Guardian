package com.example.firshermensguardian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AlertsActivity extends AppCompatActivity {
RecyclerView recyclerView;
Button alert;

//alertsAdapter alertsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        BottomNavigationView bottomNavigationView;
        bottomNavigationView=findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.Alerts);
        alert=findViewById(R.id.alert_btn);
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                intent.putExtra("check_for_ship", "shipalert");
                startActivity(intent);
            }
        });
        //recyclerView=findViewById(R.id.alerts_recycler_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.Home:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case  R.id.Weather:
                        startActivity(new Intent(getApplicationContext(),WeatherActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case  R.id.Alerts:
                       // startActivity(new Intent(getApplicationContext(),AlertsActivity.class));
                       // overridePendingTransition(0,0);
                        return true;
                    case  R.id.User:
                        startActivity(new Intent(getApplicationContext(),UserProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
       // setRecyclerVew();
    }

//    private void setRecyclerVew() {
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        alertsAdapter=new alertsAdapter(this,getList());
//        recyclerView.setAdapter(alertsAdapter);
//    }
//    private List<alerts>getList(){
//
//  List<alerts>alerts_list=new ArrayList<>();
//  alerts_list.add(new alerts("1","Ram","1234651 N"));
//  alerts_list.add(new alerts("2","Raj","1233413 s"));
//  alerts_list.add(new alerts("3","Sam","3214567 W"));
//  alerts_list.add(new alerts("4","Kumar","0981314 E"));
//
//  return alerts_list;
   // }
}