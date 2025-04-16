package com.example.basicui
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class Practical8 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.S_V2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practical8)
        val cities = arrayOf("Mumbai", "Delhi", "Bangalore", "Chennai", "Kolkata",
            "Hyderabad", "Pune", "Ahmedabad", "Jaipur", "Lucknow",
            "Kanpur", "Nagpur", "Indore", "Thane", "Bhopal")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, cities)
        val autoCompleteTextView: AutoCompleteTextView = findViewById(R.id.cityAutoCompleteTextView)
        autoCompleteTextView.setAdapter(adapter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main7)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets } } }

