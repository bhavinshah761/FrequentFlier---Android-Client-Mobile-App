package com.example.freqflier;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class MainActivity6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);


        Spinner spinner3 = findViewById(R.id.spinner3);
        Intent intent = getIntent();
        String pid = intent.getStringExtra("pid");
        EditText editText3=findViewById(R.id.editText3);


        Button button7 = findViewById(R.id.button7);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url =  "http://10.0.2.2:8080/frequentflier/GetPassengerids.jsp?pid="+pid;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ArrayList<String> MyArrayList2 = new ArrayList<String>();
                String[] result5 = s.trim().split("#");
                for(int i =0; i<result5.length;i++)
                {
                    MyArrayList2.add(result5[i]);

                }
                ArrayAdapter<String> newArrayAdapter2 = new ArrayAdapter<String>(MainActivity6.this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,MyArrayList2);
                newArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner3.setAdapter(newArrayAdapter2);

            }
        },null);
        queue.add(request);


        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String dpid = adapterView.getSelectedItem().toString();


                button7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String points= editText3.getText().toString();
                        System.out.println(points);

                        String url6 = "http://10.0.2.2:8080/frequentflier/TransferPoints.jsp?spid="+pid+"&dpid="+dpid+"&npoints="+points;
                        StringRequest request2 = new StringRequest(Request.Method.GET, url6, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                if (s.trim().equals("No")) {
                                    Toast.makeText(MainActivity6.this, "unsuccesful in transferring", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(MainActivity6.this, "succesful in transferring", Toast.LENGTH_LONG).show();
                                }

                            }
                        },null);
                        queue.add(request2);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}