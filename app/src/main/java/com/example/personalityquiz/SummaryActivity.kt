package com.example.personalityquiz

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_summary)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val radio = intent.getStringExtra("radio")
        val seekbar = intent.getIntExtra("seekbar", 0)
        val spinner = intent.getStringExtra("spinner")
        val checkboxes = intent.getStringExtra("checkboxes")
        val date = intent.getStringExtra("date")
        val time = intent.getStringExtra("time")

        val textView = findViewById<TextView>(R.id.text_summary)



    }
}