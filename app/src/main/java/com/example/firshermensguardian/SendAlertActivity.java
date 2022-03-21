package com.example.firshermensguardian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SendAlertActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
     Button alert,rescue;
    private  DatabaseReference rootRef;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_alert);

        alert=findViewById(R.id.btn_send_alert);
        Spinner spinner=findViewById(R.id.alert_spinner);

        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.alerts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        String alertType=spinner.getSelectedItem().toString();
        mAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseDatabase.getInstance().getReference();
        rescue=findViewById(R.id.rescue);
        HashMap<String,Object> user_alerts=new HashMap();
        rescue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SendAlertActivity.this, "Rescue Request sent sucessfully!", Toast.LENGTH_LONG).show();

                user_alerts.put("shipid ","SH001");
                user_alerts.put("alertdisplay ","new");
                user_alerts.put("alertlocation","1000N 1000E");
                user_alerts.put("alerttype","RESCUE");

                rootRef.child("UserAlerts").child(mAuth.getCurrentUser().getUid()).setValue(user_alerts).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SendAlertActivity.this, "Rescue Request Sent Request is sent sucessfully!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                    }
                });

            }
        });
alert.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        user_alerts.put("shipid","SH001");
        user_alerts.put("alertdisplay ","new");
        user_alerts.put("alertlocation","1000N 1000E");
        user_alerts.put("alerttype",alertType);
        rootRef.child("UserAlerts").setValue(user_alerts).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SendAlertActivity.this, "Help Request Sent Request is sent sucessfully!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
            }
        });
        Toast.makeText(SendAlertActivity.this, "Help Request is sent sucessfully!", Toast.LENGTH_LONG).show();

        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
    }
});
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, "Help Request is sent sucessfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}