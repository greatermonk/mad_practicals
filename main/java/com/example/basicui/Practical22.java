package com.example.basicui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class Practical22 extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;
    private TextView tvAccelerometer;
    private TextView tvGyroScope;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practical22);
        tvAccelerometer = findViewById(R.id.tvAccelerometer);
        tvGyroScope = findViewById(R.id.tvGyroScope);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @SuppressLint("DefaultLocale")
    @Override
    public void onSensorChanged(@NonNull SensorEvent event) {
        // Check which sensor has triggered the event
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // Retrieve accelerometer values
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            // Update the accelerometer TextView
            tvAccelerometer.setText(String.format("Accelerometer:\nX: %.2f\nY: %.2f\nZ: %.2f", x, y, z));
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            // Retrieve gyroscope values (angular speed around the x, y, and z axes)
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            // Update the gyroscope TextView
            tvGyroScope.setText(String.format("Gyroscope:\nX: %.2f\nY: %.2f\nZ: %.2f", x, y, z));
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

