package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.view.*;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button switchButt = findViewById(R.id.switchbutton); // Replace "switch" with your button's ID

        switchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the NewPageActivity
                Intent intent = new Intent(MainActivity.this, NewPageActivity.class);
                startActivity(intent);
            }
        });
    }

    public void disable(final View v){
        v.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                v.setEnabled(true);
            }
        }, 3000);
        v.setBackgroundColor(Color.RED);
    }

    public void sabotage(View v){
        Button button2 = findViewById(R.id.EnterData);
        if(button2.isEnabled()){
            button2.setEnabled(false);
        }
        else{
            button2.setEnabled(true);
        }
        button2.setBackgroundColor(Color.WHITE);
    }

}