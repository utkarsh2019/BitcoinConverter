package com.project.bitcoinconverter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.bitcoinconvert.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainContent extends AppCompatActivity {

    public static String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getStringExtra("name");
        setContentView(R.layout.content_main);
        setGUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.change_name) {
            String filename = "BitcoinName";
            try {
                FileOutputStream fos = openFileOutput(filename,Context.MODE_PRIVATE);
                fos.write("".getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent startMainActivity = new Intent(MainContent.this, MainActivity.class);
            startMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMainActivity);
        }

        return super.onOptionsItemSelected(item);
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
