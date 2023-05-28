package com.example.computechfinalist;

import static com.example.computechfinalist.login.SHARED_PREFS;
import static com.example.computechfinalist.login.USERNAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class account extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView tvname, tvusername, tvemail, tvcity, tvphone;
    Button logout, btnhistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.account);
        logout = findViewById(R.id.btnlogout);
        tvname = findViewById(R.id.tvName);
        tvusername = findViewById(R.id.tvUsername);
        tvemail = findViewById(R.id.tvEmail);
        tvcity = findViewById(R.id.tvCity);
        tvphone = findViewById(R.id.tvPhone);
        btnhistory = findViewById(R.id.btnhistory);

        // Retrieve the usernamez value from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String usernamez = sharedPreferences.getString(USERNAME, "");

        // Make sure the usernamez value is not empty before fetching user data
        if (!usernamez.isEmpty()) {
            // Instantiate the RequestQueue
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://192.168.1.113/computech/customer/fetch_user_data.php?usernamez=" + usernamez;

            // Make a GET request
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        // Handle the response
                        try {
                            // Get the user data from the response JSON object
                            String name = response.getString("name");
                            String username = response.getString("username");
                            String email = response.getString("email");
                            String city = response.getString("city");
                            int phone = response.getInt("phone");

                            // Set the TextViews with the user data
                            tvname.setText(name);
                            tvusername.setText(username);
                            tvemail.setText(email);
                            tvcity.setText(city);
                            tvphone.setText(String.valueOf(phone));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        // Handle the error
                        Toast.makeText(this, "Error fetching user data", Toast.LENGTH_SHORT).show();
                    });

            // Add the request to the RequestQueue
            queue.add(request);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.account:
                        return true;
                    case R.id.homez:
                        startActivity(new Intent(getApplicationContext(),main_menu.class));
                        overridePendingTransition(0,0);
                        return  true;
                    case R.id.message:
                        startActivity(new Intent(getApplicationContext(),messages.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.activity:
                        startActivity(new Intent(getApplicationContext(),activity_show.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(account.this,login.class);
                startActivity(intent);
                finish();
            }
        });
       btnhistory.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(account.this,history.class);
               startActivity(intent);
           }
       });
    }




}
