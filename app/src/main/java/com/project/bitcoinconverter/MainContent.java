package com.project.bitcoinconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.bitcoinconvert.R;

public class MainContent extends AppCompatActivity {

    public static String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getStringExtra("name");
        setContentView(R.layout.content_main);
        setGUI();
    }

    public void setGUI() {
        TextView welcomeMessage = (TextView) findViewById(R.id.displayName);
        welcomeMessage.setText("Welcome "+userName+"!");

        final Spinner currencies = (Spinner) findViewById(R.id.currencySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.currency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencies.setAdapter(adapter);

        Button convert = (Button) findViewById(R.id.convertCurrency);

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = currencies.getSelectedItem().toString();
                String currency = temp.substring(temp.indexOf('(')+1,temp.indexOf(')'));
                Intent startConvert = new Intent(MainContent.this, Convert.class);
                startConvert.putExtra("currency",currency);
                startActivity(startConvert);
            }
        });
    }

    @Override
    public void onStart() {

        super.onStart();
    }

    @Override
    public void onStop() {

        super.onStop();
    }
}
