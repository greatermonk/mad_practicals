package com.example.basicui;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.util.Set;

public class Practical24 extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter bluetoothAdapter;
    private ListView listViewPairedDevices;
    private final ActivityResultLauncher<Intent> bluetoothActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Bluetooth enabled
                    listPairedDevices();
                } else {
                    // Bluetooth not enabled or error occurred
                    Toast.makeText(Practical24.this,
                            "Bluetooth must be enabled to use this app", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practical24);
        listViewPairedDevices = findViewById(R.id.listviewPairedDevices);
        // Get the Bluetooth adapter
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not supported on this device", Toast.LENGTH_LONG).show();
            finish();
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                // Request to enable Bluetooth using the new API
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                bluetoothActivityResultLauncher.launch(enableBtIntent);
            } else {
                listPairedDevices();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                // Bluetooth has been enabled
                listPairedDevices();
            } else {
                // User did not enable Bluetooth or an error occurred
                Toast.makeText(this, "Bluetooth must be enabled to use this app", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
    private void listPairedDevices() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        ArrayAdapter<String> adapter;

        if (!pairedDevices.isEmpty()) {
            // There are paired devices. Add each one to a list.
            String[] deviceNames = new String[pairedDevices.size()];
            int index = 0;
            for (BluetoothDevice device : pairedDevices) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                deviceNames[index++] = device.getName() + "\n" + device.getAddress();
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceNames);
        } else {
            // No paired devices found
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"No paired devices found"});
        }
        listViewPairedDevices.setAdapter(adapter);
    }
}