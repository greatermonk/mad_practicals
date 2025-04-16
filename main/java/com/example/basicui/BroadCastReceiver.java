package com.example.basicui;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;

public class BroadCastReceiver extends BroadcastReceiver {
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent){
        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())){
            boolean isAirplaneModeOnOrOff = intent.getBooleanExtra("state", false);
            if (isAirplaneModeOnOrOff) {
                Toast.makeText(context, "Airplane mode is On:", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(context, "Airplane mode is Off:", Toast.LENGTH_LONG).show();
            }}}}
