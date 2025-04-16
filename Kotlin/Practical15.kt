package com.example.basicui
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Practical15 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.S_V2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practical15)
        val btnShowToast = findViewById<Button>(R.id.btnShowToast)
        btnShowToast.setOnClickListener {
            showCustomToast("This is a Custom Toast Alert!")
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main14)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets } }

    private fun showCustomToast(message: String){
        val layout : View = ViewGroup.inflate(this, R.layout.toastbutton, null)
        layout.findViewById<TextView>(R.id.tvToastMessage).text = message

        with(Toast(this)){
            setGravity(Gravity.CENTER_HORIZONTAL, 0, 100)
            duration = Toast.LENGTH_LONG
            show()

        }

    }
}