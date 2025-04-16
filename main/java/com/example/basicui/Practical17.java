package com.example.basicui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Practical17 extends AppCompatActivity {
    private TextView helloTextView;
    @SuppressLint({"ResourceType", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practical17);
        Button pressButton = findViewById(R.id.pressButton);
        helloTextView = findViewById(R.id.helloTextView);
        pressButton.setOnClickListener(v -> helloTextView.setText(R.string.helloworld));
    }
}