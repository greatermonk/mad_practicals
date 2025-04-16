package com.example.basicui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Practical21 extends AppCompatActivity {
    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practical21);
        ((TextView)findViewById(R.id.airplane_mode_status)).setText("Toggle Airplane mode in notification panel to see broadcast receiver in action!");}}
