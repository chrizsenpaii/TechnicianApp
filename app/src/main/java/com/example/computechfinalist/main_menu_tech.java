package com.example.computechfinalist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class main_menu_tech extends AppCompatActivity {
    TextView textView;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_tech);
        textView = findViewById(R.id.textView3);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.homez);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(),account_tech.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.homez:
                        startActivity(new Intent(getApplicationContext(),main_menu_tech.class));
                        overridePendingTransition(0,0);
                        return  true;
                    case R.id.message:
                        startActivity(new Intent(getApplicationContext(),messages_tech.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.activity:
                        startActivity(new Intent(getApplicationContext(),activity_tech.class));
                        overridePendingTransition(0,0);
                        return true;


                }
                return false;
            }
        });

    }
}