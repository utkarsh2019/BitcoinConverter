package com.project.bitcoinconverter;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.bitcoinconvert.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    public static String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String filename = "BitcoinName";
        File directory;
        if (filename.isEmpty()){
            directory = getFilesDir();
        }
        else {
            directory = getDir(filename,MODE_PRIVATE);
        }
        File [] files = directory.listFiles();
        try {
            FileInputStream fis = openFileInput(filename);
            Scanner sc = new Scanner(fis);
            sc.useDelimiter("\\Z");
            String content = sc.next();
            if(content != null) {
                userName = content;
            }
            sc.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(userName == null) {
            setContentView(R.layout.activity_main);
            Button submitName = (Button) findViewById(R.id.submitName);
            final EditText getName = (EditText) findViewById(R.id.nameText);

            submitName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userName = getName.getText().toString().trim();
                    try {
                        FileOutputStream fos = openFileOutput(filename,Context.MODE_PRIVATE);
                        fos.write(userName.getBytes());
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setGUI();
                }
            });
        }
        else {
            setGUI();
        }

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setGUI() {
        setContentView(R.layout.content_main);
        TextView welcomeMessage = (TextView) findViewById(R.id.displayName);
        welcomeMessage.setText("Welcome "+userName+"!");
    }

    @Override
    public void onStart() {
        super.onStart();
        setContentView(R.layout.content_main);
        TextView welcomeMessage = (TextView) findViewById(R.id.displayName);
        welcomeMessage.setText("Welcome "+userName+"!");
    }

    @Override
    public void onStop() {

        super.onStop();
    }
}
