package com.example.basicui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Practical20 extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practical20);
        Button startServiceButton = findViewById(R.id.startClockService);
        Button stopServiceButton = findViewById(R.id.stopClockService);
        startServiceButton.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(new Intent(Practical20.this, ClockService.class));
            }
        });
        stopServiceButton.setOnClickListener(view -> stopService(new Intent(Practical20.this, ClockService.class)));

    }

}