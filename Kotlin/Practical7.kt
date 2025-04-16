package com.example.basicui
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class Practical7 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.S_V2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practical7)
        val inputEditText: EditText = findViewById(R.id.inputEditText)
        val outputTextView: TextView = findViewById(R.id.outputTextView)
        val updateButton: Button = findViewById(R.id.updateButton)
        updateButton.setOnClickListener {
            val inputText = inputEditText.text.toString()
            outputTextView.text = inputText
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main6)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets } } }