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

    // Name that is saved
    private static String userName;

    // Internal Storage in private mode
    private static String filename = "BitcoinName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = getIntent().getStringExtra("name");
        setContentView(R.layout.content_main);
        setGUI();
    }

    // Inflating the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Handling action bar item clicks here
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.change_name) {

            // Reset name saved in Internal Storage to null
            try {
                FileOutputStream fos = openFileOutput(filename,Context.MODE_PRIVATE);
                fos.write("".getBytes());
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Call Main Activity as a New Task
            Intent startMainActivity = new Intent
                                    (MainContent.this, MainActivity.class);
            startMainActivity.setFlags
                            (Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMainActivity);
        }

        return super.onOptionsItemSelected(item);
    }

    // Sets up GUI for MainContent
    public void setGUI() {

        // Edit the Welcome message
        TextView welcomeMessage = (TextView) findViewById(R.id.displayName);
        welcomeMessage.setText("Welcome "+userName+"!");

        // Populate the Currencies
        final Spinner currencies = (Spinner) findViewById(R.id.currencySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                                            (this,R.array.currency_array,
                                                    android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencies.setAdapter(adapter);

        Button convert = (Button) findViewById(R.id.convertCurrency);

        // On clicking convert, call activity Convert to show value
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
