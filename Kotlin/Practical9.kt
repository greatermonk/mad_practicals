package com.example.basicui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class Practical9 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n", "CutPasteId")
    @RequiresApi(Build.VERSION_CODES.S_V2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practical9)
        val normalButton: Button = findViewById(R.id.normalButton)
        val imageButton: ImageButton = findViewById(R.id.imagebutton)
        val toggleButton: ToggleButton = findViewById(R.id.toggleButton)
        val statusTextView: TextView = findViewById(R.id.statusTextView)
        normalButton.setOnClickListener{
            Toast.makeText(this, "Normal Button Clicked", Toast.LENGTH_SHORT).show()
        }
        imageButton.setOnClickListener{
            Toast.makeText(this, "Image Button Clicked", Toast.LENGTH_SHORT).show()
        }
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                statusTextView.text = "Status: ON"
            } else {
                statusTextView.text = "Status: OFF"
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main8)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets } } }