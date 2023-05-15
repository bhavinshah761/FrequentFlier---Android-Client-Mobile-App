package com.example.freqflier;

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
import java.util.List;

public class MainActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        TextView textView30 = findViewById(R.id.textView30);
        TextView textView31 = findViewById(R.id.textView31);
        TextView textView32 = findViewById(R.id.textView32);
        TextView textView33 = findViewById(R.id.textView33);
        TextView textView16 = findViewById(R.id.textView16);

        Spinner spinner2 = findViewById(R.id.spinner2);

        Intent intent5 = getIntent();

        String pid = intent5.getStringExtra("pid");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url5 = "http://10.0.2.2:8080/frequentflier/AwardIds.jsp?pid="+pid;

        StringRequest request5 = new StringRequest(Request.Method.GET, url5, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ArrayList<String> MyArrayList2 = new ArrayList<String>();
                String[] result5 = s.trim().split("#");
                for(int i =0; i<result5.length;i++)
                {
                    MyArrayList2.add(result5[i]);
                }
                ArrayAdapter<String> newArrayAdapter2 = new ArrayAdapter<String>(MainActivity5.this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,MyArrayList2);
                newArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(newArrayAdapter2);
            }
        },null);
        requestQueue.add(request5);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String award_id = adapterView.getSelectedItem().toString();
                String url2 = "http://10.0.2.2:8080/frequentflier/RedemptionDetails.jsp?awardid="+award_id+"&pid="+pid;
                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        String[] parts = s.trim().split(",");


                        String prize = parts[0].trim();
                        String points = parts[1];



                        System.out.println(prize);
                        System.out.println(points);
                        System.out.println(parts[2]);


                        textView30.setText(prize);
                        textView31.setText(points);
                        String head = "Redemption Date\t\t\t\t\t\tExchange Center";
                        textView16.setText(head);
                        textView32.setText("----------------------------------------------------------------------------------------------------------------");





                        String resp = parts[2].trim().replace("$","\t\t\t\t").replace('#','\n').replace("00:00:00","\t\t\t\t\t\t\t\t\t")+"\t\t\t\t"+parts[3].replace('#','\n');

                        textView33.setText(resp);

                    }
                },null);
                requestQueue.add(stringRequest2);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}