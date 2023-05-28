package com.example.computechfinalist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.TextView;

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

public class main_menu extends AppCompatActivity implements techAdapter.onItemClickListener {
    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static final String URL_TECH = "http://192.168.1.113/computech/customer/display_tech.php";
    public  static final String tech_Name ="techname";
    public  static final String tech_Username ="techuname";
    public  static final String tech_email ="techmail";
    public  static final String tech_city ="techcity";
    public  static final String tech_phone ="techphone";


    //a list to store all the products
    List<Technician> technicianList;

    //the recyclerview
    RecyclerView recyclerView;
BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        technicianList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadTechnician();


        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.homez);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),account.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.homez:
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

    }
    private void loadTechnician() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_TECH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject technician = array.getJSONObject(i);

                                //adding the product to product list
                                technicianList.add(new Technician(
                                        technician.getString("name"),
                                        technician.getString("username"),
                                        technician.getString("email"),
                                        technician.getString("city"),
                                        technician.getInt("phone")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            techAdapter adapter = new techAdapter(main_menu.this, technicianList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener((techAdapter.onItemClickListener) main_menu.this);
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
        Intent detailIntent =   new Intent(this,DetailTech.class);
        Technician clickedItem = technicianList.get(position);
        detailIntent.putExtra(tech_Name,clickedItem.getName());
        detailIntent.putExtra(tech_Username,clickedItem.getUsername());
        detailIntent.putExtra(tech_email,clickedItem.getEmail());
        detailIntent.putExtra(tech_city,clickedItem.getCity());
        detailIntent.putExtra(tech_phone,clickedItem.getPhone());
        startActivity(detailIntent);
    }
}
