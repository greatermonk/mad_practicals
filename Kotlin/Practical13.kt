package com.example.basicui
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class Practical13 : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.S_V2)
    private var progressVal = 0
    private lateinit var determinateProgressBar : ProgressBar
    private lateinit var indeterminateProgressBar : ProgressBar
    @RequiresApi(Build.VERSION_CODES.S_V2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.practical13)
        determinateProgressBar = findViewById(R.id.determinateProgressBar)
        indeterminateProgressBar = findViewById(R.id.indeterminateProgressBar)
        val updateDeterminateButton : Button = findViewById(R.id.updateDeterminateButton)
        val toggleIndeterminateButton : Button = findViewById(R.id.toggleIndeterminateButton)
        updateDeterminateButton.setOnClickListener {
            updateDeterminateProgress()
        }
        toggleIndeterminateButton.setOnClickListener {
            toggleIndeterminateProgress()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main12)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    @RequiresApi(Build.VERSION_CODES.S_V2)
    private fun updateDeterminateProgress() {
        progressVal += 10
        if (progressVal > 100) {
            progressVal = 0
        }
        determinateProgressBar.progress = progressVal
    }
    private fun toggleIndeterminateProgress() {
        indeterminateProgressBar.apply {
            if (isIndeterminate) {
                isIndeterminate = false
                progress = 0
            } else {
                isIndeterminate = true
            }
        }
    }
}




