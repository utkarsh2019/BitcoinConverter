package com.project.bitcoinconverter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

    private static String userName;

    private static int splashTimeout = 1000;

    private static String filename = "BitcoinName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen);

        userName = null;
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
            String content = null;
            Boolean notEmpty = sc.hasNext();
            if (notEmpty) {
                content = sc.next();
            }
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getName();
            }
        },splashTimeout);
    }

    public void getName() {
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
    }

    public void setGUI() {
        Intent startContent = new Intent(MainActivity.this, MainContent.class);
        startContent.putExtra("name",userName);
        startContent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startContent);
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
