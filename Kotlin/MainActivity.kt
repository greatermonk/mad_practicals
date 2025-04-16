package com.example.basicui
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.S_V2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets }
        validateCredentials()
    }
    private fun validateCredentials() {
        val username_widget = findViewById<EditText>(R.id.username)
        val password_widget = findViewById<EditText>(R.id.password)
        val submitButton = findViewById<Button>(R.id.submit_button)
        if (username_widget != null && password_widget != null && submitButton != null) {
            submitButton.setOnClickListener {
                val username = username_widget.text.toString()
                val password = password_widget.text.toString()
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    Log.d("Login", "Username: $username\nPassword: $password")
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this,
                        "Username or password is empty please input proper username or password value!",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d(
                        "Login",
                        "Username or password is empty please input proper username or password value!"
                    ) } }
        } else {
            Log.e("com.example.basicui.MainActivity", "One or more views not found in validateCredentials()")
        } } }