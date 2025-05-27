package com.example.personalityquiz

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var chronometer: Chronometer
    private lateinit var button_start : Button
    private var running = false
    private lateinit var text1 : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button_date = findViewById<Button>(R.id.button_date)
        val button_time = findViewById<Button>(R.id.button_time)

        button_date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, {_, selectedYear, selectedMonth, selectedDay ->
                button_date.setText("$selectedYear/${selectedMonth+1}/$selectedDay")
            }, year, month, day)
            datePickerDialog.show()
        }

        button_time.setOnClickListener {
            var calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, {_ : TimePicker, selectedHour: Int, selectedMinute: Int ->
                button_time.setText("$selectedHour:$selectedMinute")
            }, hour, minute, true)
            timePickerDialog.show()
        }

        chronometer = findViewById(R.id.chronometer)
        button_start = findViewById(R.id.button_start)
        button_start.setOnClickListener {
            if(!running) {
                chronometer.base = SystemClock.elapsedRealtime()
                chronometer.start()
                running = true
            }
        }

    }
}