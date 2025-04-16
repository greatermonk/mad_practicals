package com.example.basicui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Practical18 extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practical18);
        TextView textView = findViewById(R.id.textView);
        Button explicitIntentButton = findViewById(R.id.explicitIntentButton);
        Button implicitIntentButton = findViewById(R.id.implicitIntentButton);
        Button implicitIntentButton2 = findViewById(R.id.implicitIntentButton2);
        explicitIntentButton.setOnClickListener(v -> {
           Intent openSecondActivityIntent = new Intent(this, Practical18b.class);
           startActivity(openSecondActivityIntent);
        });
        implicitIntentButton.setOnClickListener(v -> openWebpage("https://www.google.com"));
        implicitIntentButton2.setOnClickListener(v -> openWebpage("https://www.youtube.com"));
    }
    public void openWebpage(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
