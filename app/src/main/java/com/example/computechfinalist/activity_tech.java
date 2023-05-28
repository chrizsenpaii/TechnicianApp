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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

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

public class activity_tech extends AppCompatActivity implements actAdapter.onItemClickListener {
    private static final String URL_HIS = "http://192.168.1.113/computech/technician/activity_tech.php";
    List<acts> actList;
    RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;

    public String techuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech);

        recyclerView = findViewById(R.id.recylcerViewz);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.activity);
        actList = new ArrayList<acts>();
        loadHistory();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), account_tech.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.homez:
                        startActivity(new Intent(getApplicationContext(), main_menu_tech.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.message:
                        startActivity(new Intent(getApplicationContext(), messages_tech.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.activity:
                        startActivity(new Intent(getApplicationContext(), activity_tech.class));
                        overridePendingTransition(0, 0);
                        return true;


                }
                return false;
            }
        });


    }

    private void loadHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        techuser = sharedPreferences.getString(USERNAME, "");


        String url = URL_HIS + "?customuser=" + techuser;

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
                                JSONObject history = array.getJSONObject(i);

                                actList.add(new acts(
                                        history.getString("location"),
                                        history.getString("date"),
                                        history.getString("price"),
                                        history.getString("techID")

                                ));

                            }

                            //creating adapter object and setting it to recyclerview
                            actAdapter adapter = new actAdapter(activity_tech.this, actList);
                            recyclerView.setAdapter(adapter);
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

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, activity_see_tech.class);
        acts clickedItem = actList.get(position);
        detailIntent.putExtra(driverzx, clickedItem.getDriverx());
        detailIntent.putExtra(datezx, clickedItem.getPlacex());
        detailIntent.putExtra(pricezx, clickedItem.getDatex());
        detailIntent.putExtra(locationzx, clickedItem.getPricex());
        startActivity(detailIntent);
    }
}