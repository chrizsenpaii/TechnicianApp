package com.example.computechfinalist;


import static com.example.computechfinalist.activity_show.datezx;

import static com.example.computechfinalist.activity_show.driverzx;

import static com.example.computechfinalist.activity_show.locationzx;

import static com.example.computechfinalist.activity_show.pricezx;
import static com.example.computechfinalist.login.SHARED_PREFS;
import static com.example.computechfinalist.login.USERNAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class activity extends AppCompatActivity  {
    TextView placezz, datezz, feezz, driverzz;
    Button done;
    CardView cardView;
    BottomNavigationView bottomNavigationView;

    public  String TECHNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String customusers = sharedPreferences.getString(USERNAME, "");




        placezz = findViewById(R.id.tvtechPlace);
        cardView = findViewById(R.id.cardview);
        datezz = findViewById(R.id.tvtechDate);
        feezz = findViewById(R.id.tvtechPrice);
        driverzz = findViewById(R.id.tvtechdriver);
        done = findViewById(R.id.btnDone);
        Intent intent = getIntent();
        String intplacez = intent.getStringExtra(locationzx);
        String intfeez = intent.getStringExtra(pricezx);
        String intdatez = intent.getStringExtra(datezx);
        String intnamez = intent.getStringExtra(driverzx);
        placezz.setText(intplacez);
        feezz.setText(intfeez);
        datezz.setText(intdatez);
        driverzz.setText(intnamez);

        bottomNavigationView = findViewById(R.id.bottom_navigator);

        bottomNavigationView.setSelectedItemId(R.id.activity);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isContentEmpty()) {
                    if (done.getVisibility() == View.GONE) {
                        done.setVisibility(View.VISIBLE);
                    } else {
                        done.setVisibility(View.GONE);
                    }
                }
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), account.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.homez:
                        startActivity(new Intent(getApplicationContext(), main_menu.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.message:
                        startActivity(new Intent(getApplicationContext(), messages.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.activity:
                        return true;


                }
                return false;
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity.this);
                builder.setMessage("Are you sure this service is done").setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                try {
                                    URL url = new URL("http://192.168.1.113/computech/customer/history.php");
                                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                    httpURLConnection.setRequestMethod("POST");
                                    httpURLConnection.setDoOutput(true);
                                    OutputStream outputStream = httpURLConnection.getOutputStream();
                                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                                    String data = URLEncoder.encode("customusers", "UTF-8") + "=" + URLEncoder.encode(customusers, "UTF-8") + "&" +
                                            URLEncoder.encode("techname", "UTF-8") + "=" + URLEncoder.encode(intnamez, "UTF-8") + "&" +
                                            URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(intplacez, "UTF-8") + "&" +
                                            URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(intdatez, "UTF-8") + "&" +
                                            URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(intfeez, "UTF-8");
                                    bufferedWriter.write(data);
                                    bufferedWriter.flush();
                                    bufferedWriter.close();
                                    outputStream.close();
                                    InputStream inputStream = httpURLConnection.getInputStream();
                                    inputStream.close();
                                    httpURLConnection.disconnect();
                                } catch (@SuppressLint("StaticFieldLeak") Exception e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        }.execute();

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity.this);
                        builder1.setMessage("Please rate your driver").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent1 = new Intent(activity.this,Rate.class);
                                intent1.putExtra("name", driverzz.getText().toString());
                                startActivity(intent1);
                                new AsyncTask<Void, Void, Void>() {
                                    @Override
                                    protected Void doInBackground(Void... voids) {
                                        try {
                                            URL url = new URL("http://192.168.1.113/computech/customer/delete.php");
                                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                            httpURLConnection.setRequestMethod("POST");
                                            httpURLConnection.setDoOutput(true);
                                            OutputStream outputStream = httpURLConnection.getOutputStream();
                                            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                                            String data = URLEncoder.encode("activityID", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8");
                                            bufferedWriter.write(data);
                                            bufferedWriter.flush();
                                            bufferedWriter.close();
                                            outputStream.close();
                                            InputStream inputStream = httpURLConnection.getInputStream();
                                            inputStream.close();
                                            httpURLConnection.disconnect();
                                        } catch (@SuppressLint("StaticFieldLeak") Exception e) {
                                            e.printStackTrace();
                                        }
                                        return null;
                                    }
                                }.execute();

                            }
                        });
                        AlertDialog dialog = builder1.create(); // create and show the dialog
                        dialog.show();
                    }
                });
                AlertDialog dialog = builder.create(); // create and show the dialog
                dialog.show();
            }
        });

    }
    private boolean isContentEmpty() {
        String place = placezz.getText().toString().trim();
        String fee = feezz.getText().toString().trim();
        String date = datezz.getText().toString().trim();
        String driver = driverzz.getText().toString().trim();
        return place.isEmpty() && fee.isEmpty() && date.isEmpty() && driver.isEmpty();
    }

}