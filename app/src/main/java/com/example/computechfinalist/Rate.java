package com.example.computechfinalist;

import static com.example.computechfinalist.login.SHARED_PREFS;
import static com.example.computechfinalist.login.USERNAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Rate extends AppCompatActivity {
TextView name;
RatingBar ratingBar;
Button rate;
String cusers, tname,ratingz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        name = findViewById(R.id.tvtechname);
        ratingBar = findViewById(R.id.ratingBar);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String customusers = sharedPreferences.getString(USERNAME, "");
        rate = findViewById(R.id.btnRate);
        Intent intent =getIntent();
       String techname = intent.getStringExtra("name");
       name.setText(techname);
        float rating = ratingBar.getRating();
        int ratingInt = Math.round(rating);
        String ratingString = Integer.toString(ratingInt);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tname = techname;
                ratingz = ratingString;
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        try {
                            URL url = new URL("http://192.168.1.113/computech/customer/rating.php");
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoOutput(true);
                            OutputStream outputStream = httpURLConnection.getOutputStream();
                            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                            String data = URLEncoder.encode("customusers", "UTF-8") + "=" + URLEncoder.encode(customusers, "UTF-8") + "&" +
                                    URLEncoder.encode("techname", "UTF-8") + "=" + URLEncoder.encode(tname, "UTF-8") + "&" +
                                    URLEncoder.encode("rating", "UTF-8") + "=" + URLEncoder.encode(ratingz, "UTF-8");
                            bufferedWriter.write(data);
                            bufferedWriter.flush();
                            bufferedWriter.close();
                            outputStream.close();
                            InputStream inputStream = httpURLConnection.getInputStream();
                            inputStream.close();
                            httpURLConnection.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute();
                Intent intent1 = new Intent(Rate.this,history.class);
                intent1.putExtra("nametech",tname);
                startActivity(intent1);
            }
        });
    }
}