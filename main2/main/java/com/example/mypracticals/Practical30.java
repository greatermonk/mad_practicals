package com.example.mypracticals;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Practical30 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practical30);

        // Send Email Button
        Button btnSendEmail = findViewById(R.id.btnSendEmail);
        btnSendEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Practical30.this, SendEmailActivity.class);
            startActivity(intent);
        });

        // Receive Email Button
        Button btnReceiveEmail = findViewById(R.id.btnReceiveEmail);
        btnReceiveEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Practical30.this, ReceiveEmailActivity.class);
            startActivity(intent);
        });
    }
}