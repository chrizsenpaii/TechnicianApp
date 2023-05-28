    package com.example.computechfinalist;

    import static com.example.computechfinalist.login.SHARED_PREFS;
    import static com.example.computechfinalist.login.USERNAME;
    import static com.example.computechfinalist.main_menu.tech_Name;
    import static com.example.computechfinalist.main_menu.tech_Username;
    import static com.example.computechfinalist.main_menu.tech_city;
    import static com.example.computechfinalist.main_menu.tech_email;
    import static com.example.computechfinalist.main_menu.tech_phone;

    import androidx.appcompat.app.AppCompatActivity;

    import android.app.DatePickerDialog;
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.DatePicker;
    import android.widget.EditText;
    import android.widget.Spinner;
    import android.widget.TextView;
    import android.widget.Toast;

    import java.io.BufferedWriter;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.OutputStream;
    import java.io.OutputStreamWriter;
    import java.net.HttpURLConnection;
    import java.net.MalformedURLException;
    import java.net.URL;
    import java.net.URLEncoder;
    import java.text.SimpleDateFormat;
    import java.util.Calendar;
    import java.util.Date;
    import java.util.Locale;

    public class DetailTech extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
        Spinner spinner;
        Button btnavail;
        EditText date;
        Context context = this;
        String selectedDate = "";
        String location;
         public String  techusers, locz, datez, feez;

        ArrayAdapter<CharSequence> adapter;

        TextView techname, techuser, techemail, techcity, techphone, techfee,tvdates;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail_tech);
            Intent intent = getIntent();

            String intname = intent.getStringExtra(tech_Name);
            String intuname = intent.getStringExtra(tech_Username);
            String intmail = intent.getStringExtra(tech_email);
            String intcity = intent.getStringExtra(tech_city);
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            String customusers = sharedPreferences.getString(USERNAME, "");
            int intphone = intent.getIntExtra(tech_phone, 0);
            Calendar calendar = Calendar.getInstance();
            adapter = ArrayAdapter.createFromResource(this, R.array.location, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner = findViewById(R.id.datespinner);
            spinner.setAdapter(adapter);
            techfee = findViewById(R.id.tvtechfee);
            btnavail = findViewById(R.id.btnAvail);
            date = findViewById(R.id.eddate);
            tvdates = findViewById(R.id.tvdate);
            techname = findViewById(R.id.tvtechname);
            techuser = findViewById(R.id.tvtechusername);
            techemail = findViewById(R.id.tvtechemail);
            techcity = findViewById(R.id.tvtechcity);
            techphone = findViewById(R.id.tvtechphone);
            techname.setText(intname);
            techuser.setText(intuname);
            techemail.setText(intmail);
            techcity.setText(intcity);
            techphone.setText(String.valueOf(intphone));

                    btnavail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            techusers = String.valueOf(techuser.getText());
                            locz = location;
                            datez = String.valueOf(tvdates.getText());
                            feez = String.valueOf(techfee.getText());

                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    try {
                                        URL url = new URL("http://192.168.1.113/computech/customer/activity.php");
                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                        httpURLConnection.setRequestMethod("POST");
                                        httpURLConnection.setDoOutput(true);
                                        OutputStream outputStream = httpURLConnection.getOutputStream();
                                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                                        String data = URLEncoder.encode("customusers", "UTF-8") + "=" + URLEncoder.encode(customusers, "UTF-8") + "&" +
                                                URLEncoder.encode("techusers", "UTF-8") + "=" + URLEncoder.encode(techusers, "UTF-8") + "&" +
                                                URLEncoder.encode("locz", "UTF-8") + "=" + URLEncoder.encode(locz, "UTF-8") + "&" +
                                                URLEncoder.encode("datez", "UTF-8") + "=" + URLEncoder.encode(datez, "UTF-8") + "&" +
                                                URLEncoder.encode("feez", "UTF-8") + "=" + URLEncoder.encode(feez, "UTF-8");
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
                            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("tecname", techname.getText().toString());
                            editor.apply();
                            Intent jummpage = new Intent(DetailTech.this,activity.class);
                            jummpage.putExtra("date",datez);
                            jummpage.putExtra("location",locz);
                            jummpage.putExtra("fee",feez);
                            jummpage.putExtra("name",techname.getText().toString());
                            startActivity(jummpage);
                        }

                    });


                    date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                    // update the Calendar instance with the selected date
                                    calendar.set(year, month, dayOfMonth);

                                    // update the EditText with the selected date
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                                    date.setText(simpleDateFormat.format(calendar.getTime()));
                                    tvdates.setText(String.valueOf(simpleDateFormat.format(calendar.getTime())));
                                    tvdates.setVisibility(View.INVISIBLE);

                                }
                            }, year, month, dayOfMonth);
                            datePickerDialog.show();
                        }
                    });
                    spinner.setOnItemSelectedListener(this);

                }

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String selectedloc = adapterView.getItemAtPosition(i).toString();
                    String[] locations = getResources().getStringArray(R.array.location);
                    String[] fees = getResources().getStringArray(R.array.fee);
                    int fee = 0;
                    for (int j = 0; j < locations.length; j++) {
                        if (selectedloc.equals(locations[j])) {
                            fee = Integer.parseInt(fees[j]);
                            location = adapterView.getItemAtPosition(i).toString();
                            break;
                        }
                    }

                    techfee.setText(String.valueOf(fee));


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }

            }
