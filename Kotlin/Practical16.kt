package com.example.basicui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.*

class Practical16 : AppCompatActivity() {
    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practical16)
        val tvDate = findViewById<TextView>(R.id.tvDate)
        val tvTime = findViewById<TextView>(R.id.tvTime)
        val btnPickDate = findViewById<Button>(R.id.btnPickDate)
        val btnPickTime = findViewById<Button>(R.id.btnPickTime)
        val calendar = Calendar.getInstance()
        btnPickDate.setOnClickListener {
            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                tvDate.text = "Selected Date: $dayOfMonth/${month + 1}/$year"
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        btnPickTime.setOnClickListener {
            TimePickerDialog(this, { _, hourOfDay, minute ->
                tvTime.text = "Selected Time: ${String.format("%02d", hourOfDay)}:${String.format("%02d", minute)}"
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main15)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets }
    }
}
