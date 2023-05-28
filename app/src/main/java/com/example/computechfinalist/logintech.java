package com.example.computechfinalist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class logintech extends AppCompatActivity {
    TextInputEditText username,password;
    Button login;
    TextView signup, logintv;
    SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logintech);
        username = findViewById(R.id.edlogusertech);
        password = findViewById(R.id.edlogPasstech);
        signup = findViewById(R.id.tvSigntech);
        login = findViewById(R.id.btnLoginztech);
        logintv = findViewById(R.id.tvlogtech);
        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
       logintv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(logintech.this, com.example.computechfinalist.login.class);
               startActivity(intent);
               finish();
           }
       });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(logintech.this, com.example.computechfinalist.signuptech.class);
                startActivity(intent);
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullnamez, usernamez, emailz, cityz, phonez, passwordz;
                usernamez = String.valueOf(username.getText());
                passwordz = String.valueOf(password.getText());


                if (!usernamez.equals("") && !passwordz.equals("")) {

                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = usernamez;
                            data[1] = passwordz;

                            PutData putData = new PutData("http://192.168.1.113/computech/technician/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Login Success")) {
                                        // Store the entered username in shared preferences
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(USERNAME, usernamez);
                                        editor.apply();

                                        Intent intent = new Intent(logintech.this, main_menu_tech.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                    //End ProgressBar (Set visibility to GONE)
                                }
                            }

                        }
                    });

                } else {
                    Toast.makeText(logintech.this, "All fields are required!!!!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}