package com.example.computechfinalist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class signup extends AppCompatActivity {
        TextInputEditText fullname,username,email,city,phone,password;
        TextView login;
        Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fullname = findViewById(R.id.edFullname);
        username = findViewById(R.id.eduser);
        email = findViewById(R.id.edEmail);
        city = findViewById(R.id.edCity);
        phone = findViewById(R.id.edphone);
        password = findViewById(R.id.edPass);
        login = findViewById(R.id.tvLog);
        signup = findViewById(R.id.btnSignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullnamez, usernamez, emailz, cityz, phonez, passwordz;
                fullnamez = String.valueOf(fullname.getText());
                usernamez = String.valueOf(username.getText());
                emailz = String.valueOf(email.getText());
                cityz = String.valueOf(city.getText());
                phonez = String.valueOf(phone.getText());
                passwordz = String.valueOf(password.getText());

                if (!fullnamez.equals("") && !usernamez.equals("") && !passwordz.equals("") && !emailz.equals("") && !cityz.equals("") && !phonez.equals("")) {

                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[6];
                            field[0] = "name";
                            field[1] = "username";
                            field[2] = "email";
                            field[3] = "city";
                            field[4] = "phone";
                            field[5] = "password";
                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = fullnamez;
                            data[1] = usernamez;
                            data[2] = emailz;
                            data[3] = cityz;
                            data[4] = phonez;
                            data[5] = passwordz;

                            PutData putData = new PutData("http://192.168.1.113/computech/customer/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")) {
                                        Intent intent = new Intent(com.example.computechfinalist.signup.this,
                                                com.example.computechfinalist.login.class);
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
                    Toast.makeText(signup.this, "All fields are required!!!!", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}