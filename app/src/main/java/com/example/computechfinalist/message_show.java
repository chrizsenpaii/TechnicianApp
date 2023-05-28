package com.example.computechfinalist;

import static com.example.computechfinalist.login.SHARED_PREFS;
import static com.example.computechfinalist.login.USERNAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class message_show extends AppCompatActivity {
    private static final String URL_MES = "http://192.168.1.113/computech/customer/mes_show.php";
    List<mes> mesList;
    RecyclerView recyclerViewss;


    public String customuser;

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_show);
        recyclerViewss = findViewById(R.id.recylcerViewx);
        recyclerViewss.setHasFixedSize(true);
        recyclerViewss.setLayoutManager(new LinearLayoutManager(this));



        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.message);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.message);

        mesList = new ArrayList<>();



        loadHistory();
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
                            MessageAdapter adapter = new MessageAdapter(message_show.this, mesList);
                            recyclerViewss.setAdapter(adapter);
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
    }
}
