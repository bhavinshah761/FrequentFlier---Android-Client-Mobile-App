package com.example.freqflier;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Spinner spinner = findViewById(R.id.spinner);
        Intent intent = getIntent();
        TextView textView40 = findViewById(R.id.textView40);

        TextView textView32 = findViewById(R.id.textView32);
        TextView textView33 = findViewById(R.id.textView33);
        TextView textView22 = findViewById(R.id.textView22);
        TextView textView20 = findViewById(R.id.textView20);
        TextView textView21 = findViewById(R.id.textView21);

        ArrayList<String> MyArrayList = new ArrayList<String>();
        String pid = intent.getStringExtra("pid");
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://10.0.2.2:8080/frequentflier/Flights.jsp?pid=" + pid;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                // Split the string into an array of flight records
                String[] flightRecords = s.split("#");
                // Create an array to store the flight IDs
                String[] flightIds = new String[flightRecords.length];

                // Process each flight record
                for (int i = 0; i < flightRecords.length; i++) {
                    // Split the flight record into its components
                    String[] flightComponents = flightRecords[i].split(",");
                    // Extract the flight ID, flight number, and destination
                    String flightId = flightComponents[0];
                    // Store the flight ID in the array
                    flightIds[i] = flightId;
                }
                ArrayAdapter<String> newArrayAdapter = new ArrayAdapter<String>(MainActivity4.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, flightIds);
                newArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(newArrayAdapter);

            }
        }, null);
        queue.add(request);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                String flight_id = adapterView.getSelectedItem().toString();
                String url2 = "http://10.0.2.2:8080/frequentflier/FlightDetails.jsp?flightid="+flight_id;

                StringRequest request1 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        String[] tripId = s.trim().split("#");
                        String resp ="";

                        for (int i = 0; i < tripId.length; i++) {
                            String[] parts = tripId[i].trim().split(",");

                            String datetime1 = parts[0].replace("00:00:00","4:15 AM");

                            String datetime2 = parts[1].replace("00:00:00","8:15 PM");;

                            String value = parts[2];

                            textView40.setText(datetime1);
                            textView32.setText(datetime2);
                            textView33.setText(value);
                            String head = "Trip ID\t\t\t\t\t\t\t\tTrip Miles";

                            textView20.setText(head);
                            textView21.setText("--------------------------------------------------------------------------------------------------------");

//                        String resp0 = parts[3].trim().replace(" ","\t\t\t\t\t\t\t\t\t\t").replace('#','\n');
                            String temp = parts[3]+"\t\t\t\t\t\t\t\t\t\t\t"+parts[4];
                            resp =resp+"\n" +temp;
//                        String resp1 = parts[4].trim().replace(" ","\t\t\t\t\t\t\t\t\t\t").replace('#','\n');
//                        String resp =resp0;
                            textView22.setText(resp);
                        }

                    }
                },null);
                queue.add(request1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}