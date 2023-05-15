package com.example.freqflier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
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

public class MainActivity3 extends AppCompatActivity {
    String pid = "";
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Intent intent=getIntent();
        pid=intent.getStringExtra("pid");
        progressBar=findViewById(R.id.progressBar);
        RequestQueue queue= Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        String url="http://10.0.2.2:8080/frequentflier/Flights.jsp?pid="+pid;
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                progressBar.setVisibility(View.GONE);
                String[] result=s.trim().split("#");
                initTable(result);
            }
        }, null);
        setRequestPolicy(request);
        queue.add(request);

    }

    public void initTable(String[] result) {
        TableLayout stk = (TableLayout) findViewById(R.id.main_table);
        TableRow tableRow1 = new TableRow(this);
        TextView textView1 = new TextView(this);
        textView1.setText(" Flight id              ");
        tableRow1.addView(textView1);

        TextView textView2 = new TextView(this);
        textView2.setText(" Flight Miles                 ");
        tableRow1.addView(textView2);

        TextView textView3 = new TextView(this);
        textView3.setText(" Destination               ");
        tableRow1.addView(textView3);



        stk.addView(tableRow1);

        View line = new View(this);
        line.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 3));
        line.setBackgroundColor(Color.rgb(51, 51, 51));

        stk.addView(line);
        for (int itr =0; itr< result.length; itr++){
            String[] row = result[itr].split(",");
            String flight_id = row[0];
            String flight_miles = row[1];
            String destination = row[2];
            TableRow tableRow = new TableRow(this);
            TextView trefView = new TextView(this);
            trefView.setText(" " + flight_id);
            tableRow.addView(trefView);

            TextView dateView = new TextView(this);
            dateView.setText(" " + flight_miles);
            tableRow.addView(dateView);

            TextView pointsView = new TextView(this);
            pointsView.setText(" " + destination);
            tableRow.addView(pointsView);

            stk.addView(tableRow);
        }
        View endline = new View(this);
        endline.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, 3));
        endline.setBackgroundColor(Color.rgb(0, 188, 212));
        stk.addView(endline);
    }

    private void setRequestPolicy(StringRequest request) {
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }
}