package com.example.computechfinalist;


import static com.example.computechfinalist.activity_show.datezx;
import static com.example.computechfinalist.activity_show.driverzx;
import static com.example.computechfinalist.activity_show.locationzx;
import static com.example.computechfinalist.activity_show.pricezx;
import static com.example.computechfinalist.login.SHARED_PREFS;
import static com.example.computechfinalist.login.USERNAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class messages extends AppCompatActivity {
    private static final String URL_MES = "http://192.168.1.113/computech/customer/mes_show.php";
    List<mes> mesList;
    RecyclerView recyclerViewss;

    private static final long REFRESH_DELAY = 5000; // Refresh delay in milliseconds (e.g., 5000 = 5 seconds)
    private Handler refreshHandler;
    private Runnable refreshRunnable;
    public String customuser;
    private EditText messageInput;
    private Button sendButton, showButton;


    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        recyclerViewss = findViewById(R.id.mesrec);
        recyclerViewss.setHasFixedSize(true);
        recyclerViewss.setLayoutManager(new LinearLayoutManager(this));

        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);
        mesList =new ArrayList<>();


        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.message);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.message);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String customusers = sharedPreferences.getString(USERNAME, "");
        String technicianName = sharedPreferences.getString("tecname", "");


        customuser =customusers;
        loadHistory();


    sendButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String content =messageInput.getText().toString();

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        URL url = new URL("http://192.168.1.113/computech/customer/send.php");
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String data = URLEncoder.encode("customusers", "UTF-8") + "=" + URLEncoder.encode(customusers, "UTF-8") + "&" +
                                URLEncoder.encode("receive", "UTF-8") + "=" + URLEncoder.encode(technicianName, "UTF-8") + "&" +
                                URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(content, "UTF-8");
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
                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    // Clear the input field
                    messageInput.setText("");
                }
            }.execute();
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

                        return true;
                    case R.id.activity:
                        startActivity(new Intent(getApplicationContext(), activity.class));
                        overridePendingTransition(0, 0);
                        return true;


                }
                return false;
            }
        });
    }
    private void loadHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        customuser = sharedPreferences.getString(USERNAME, "");



        String url = URL_MES + "?customuser=" + customuser;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject message = array.getJSONObject(i);

                                mesList.add(new mes(
                                        message.getString("sender"),
                                        message.getString("receiver"),
                                        message.getString("content")

                                ));

                            }

                            //creating adapter object and setting it to recyclerview
                            MessageAdapter adapter = new MessageAdapter(messages.this, mesList);
                            recyclerViewss.setAdapter(adapter);
                            recyclerViewss.scrollToPosition(mesList.size() - 1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
        refreshHandler = new Handler();
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                // Refresh the data
                mesList.clear();
                loadHistory();

                // Schedule the next refresh
                refreshHandler.postDelayed(this, REFRESH_DELAY);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Start the automatic refresh when the activity resumes
        refreshHandler.postDelayed(refreshRunnable, REFRESH_DELAY);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Stop the automatic refresh when the activity is paused
        refreshHandler.removeCallbacks(refreshRunnable);
    }

    // ...
}



