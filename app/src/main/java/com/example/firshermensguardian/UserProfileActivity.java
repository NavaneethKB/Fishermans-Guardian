package com.example.firshermensguardian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfileActivity extends AppCompatActivity {

    private DatabaseReference rootRef;
    private FirebaseAuth mAuth;
    private FirebaseUser fUSer;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
       BottomNavigationView bottomNavigationView;
        bottomNavigationView=findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.User);
        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        fUSer = FirebaseAuth.getInstance().getCurrentUser();
        final String[] name = new String[1];
        username=findViewById(R.id.profileName);
 rootRef.child("User").child(fUSer.getUid()).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                name[0] =String.valueOf(task.getResult().getValue());

            }
        });
      if(name[0] ==null){
          username.setText("Raj");
      }else{
          username.setText(name[0]);
      }
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
                        startActivity(new Intent(getApplicationContext(),AlertsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case  R.id.User:
                        // startActivity(new Intent(getApplicationContext(),UserProfileActivity.class));
                        //overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


    }
}