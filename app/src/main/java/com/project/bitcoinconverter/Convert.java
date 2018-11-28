package com.project.bitcoinconverter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.project.bitcoinconvert.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Convert extends AppCompatActivity {

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        setGUI();
    }

    public void setGUI() {
        setContentView(R.layout.convert_display);
        final String currency = getIntent().getStringExtra("currency");

        System.out.println("Currency"+currency);
        FloatingActionButton back = (FloatingActionButton) findViewById(R.id.back);
        final TextView converted = (TextView) findViewById(R.id.currencyValue);

        String url = "https://apiv2.bitcoinaverage.com/convert/global?from=BTC&to=" + currency
                    + "&amount=1";

        System.out.println("Link: "+url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("FLAG5");
                        try {
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                            converted.setText(response.getString("price")+" "+currency);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(), "Please try again.", Toast.LENGTH_SHORT)
                                .show();
                        killActivity();
                    }
                });

        queue.add(jsonObjectRequest);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                killActivity();
            }
        });
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    public void killActivity() {
        queue.cancelAll(this);
        finish();
    }

    @Override
    public void onStop() {

        super.onStop();
        killActivity();
    }
}
