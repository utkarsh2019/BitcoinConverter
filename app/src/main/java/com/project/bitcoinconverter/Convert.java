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

    // Queue for all https requests made
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        setContentView(R.layout.convert_display);
        setGUI();
    }

    // Sets up GUI for Convert
    public void setGUI() {

        // Currency to be converted into
        final String currency = getIntent().getStringExtra("currency");

        FloatingActionButton back = (FloatingActionButton) findViewById(R.id.back);
        final TextView converted = (TextView) findViewById(R.id.currencyValue);

        // URL for api call to get converted value
        String url = "https://apiv2.bitcoinaverage.com/convert/global?from=BTC&to=" + currency
                    + "&amount=1";

        // Making https request to get back JSON object
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Stop loading
                            findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                            // Set the value on screen
                            converted.setText(response.getString("price")+" "+currency);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Notify of fail call
                        Toast.makeText(getBaseContext(),
                                "Please try again.", Toast.LENGTH_SHORT).show();

                        killActivity();
                    }
                });

        // Add the request to the queue of requests
        queue.add(jsonObjectRequest);

        // On pressing cross button
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

    // Kill activity
    public void killActivity() {
        // Cancel all pending requests
        queue.cancelAll(this);

        // Finish activity
        finish();
    }

    @Override
    public void onStop() {

        super.onStop();
        killActivity();
    }
}
