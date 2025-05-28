package com.example.personalityquiz

import android.os.Bundle
import android.widget.ImageView
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

        val points = listOf(
            intent.getIntExtra("odkrywca", 0) to "Ekstrawertyczny Odkrywca",
            intent.getIntExtra("refleksyjny", 0) to "Introwertyczny Refleksyjny",
            intent.getIntExtra("strateg", 0) to "Ambitny Strateg",
            intent.getIntExtra("idealista", 0) to "Empatyczny Idealista"
        )

        val personalityType = points.maxByOrNull { it.first }?.second ?: "Nieznany"

        val img = when(personalityType) {
            "Ekstrawertyczny Odkrywca" -> R.drawable.odkrywca
            "Introwertyczny Refleksyjny" -> R.drawable.introwertyk
            "Ambitny Strateg" -> R.drawable.strateg
            "Empatyczny Idealista" -> R.drawable.idealista
            else -> R.drawable.nimagoscia
        }

        val textView = findViewById<TextView>(R.id.text_summary)
        val image = findViewById<ImageView>(R.id.image_summary)

        textView.setText("Twoj typ osobowości: $personalityType")
        image.setImageResource(img)

    }
}