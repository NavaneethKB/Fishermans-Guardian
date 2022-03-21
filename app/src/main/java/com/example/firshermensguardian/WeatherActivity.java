package com.example.firshermensguardian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class WeatherActivity extends AppCompatActivity {
    String city="london";
    String key;
    String url="https://api.openweathermap.org/data/2.5/weather?q=Kanyakumari&units=metric&appid=85dfe063dbff6262d4a3aa6bda731eaa";


    TextView description,date,temperature;

public class DownloadJSON extends AsyncTask<String,Void,String>{

    @Override
    protected String doInBackground(String... strings) {
        String result="";
       URL url;
        HttpsURLConnection httpsURLConnection;
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        try {
            url=new URL(strings[0]);

            httpsURLConnection=(HttpsURLConnection) url.openConnection();
            inputStream=httpsURLConnection.getInputStream();
            inputStreamReader=new InputStreamReader(inputStream);
            int data= inputStreamReader.read();
            while (data!=-1){
               result+=(char)data;
               data=inputStreamReader.read();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
    }
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        description=findViewById(R.id.tv_weather_description);
        temperature=findViewById(R.id.temperature_of_the_day);
        date=findViewById(R.id.temp_date);
String temp,decs;
        DownloadJSON downloadJSON=new DownloadJSON();
        try {
            String result=downloadJSON.execute(url).get();
            JSONObject jsonObject=new JSONObject(result);
        temp=jsonObject.getJSONObject("main").getString("temp");
       decs=jsonObject.getJSONObject("weather").getString("main");
            description.setText(decs);
            temperature.setText(temp);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String Date = sdf.format(c.getTime());
      date.setText(Date);


      BottomNavigationView bottomNavigationView;
        bottomNavigationView=findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setSelectedItemId(R.id.Weather);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.Home:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case  R.id.Weather:
                     //   startActivity(new Intent(getApplicationContext(),WeatherActivity.class));
                        //overridePendingTransition(0,0);
                        return true;
                    case  R.id.Alerts:
                       startActivity(new Intent(getApplicationContext(),AlertsActivity.class));
                       overridePendingTransition(0,0);
                        return true;
                    case  R.id.User:
                        startActivity(new Intent(getApplicationContext(),UserProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}