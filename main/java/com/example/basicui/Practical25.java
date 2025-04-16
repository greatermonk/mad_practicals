package com.example.basicui;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Practical25 extends AppCompatActivity {
    private ImageView loadingCircle;
    private Animation rotateAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practical25);
        // Initialize views
        loadingCircle = findViewById(R.id.loadingCircle);
        Button startButton = findViewById(R.id.startButton);
        Button stopButton = findViewById(R.id.stopButton);
        // Load animation
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotation_animation);
        // Set button click listeners
        startButton.setOnClickListener(v -> startAnimation());
        stopButton.setOnClickListener(v -> stopAnimation());
        // Start animation when the app starts
        startAnimation();
    }
    private void startAnimation() {
        loadingCircle.startAnimation(rotateAnimation);
    }
    private void stopAnimation() {
        loadingCircle.clearAnimation();
    }
}