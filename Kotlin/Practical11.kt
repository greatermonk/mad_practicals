package com.example.basicui
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class Practical11 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.S_V2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practical11)
        val myCheckbox:CheckBox = findViewById(R.id.myCheckBox)
        val status : TextView = findViewById(R.id.statusTextView)
        myCheckbox.setOnCheckedChangeListener{_, isChecked ->
            val my_status = if (isChecked) "Selected:" else "Not selected:"
            status.text = "Status: $my_status"
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main10)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets } } }





