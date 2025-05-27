package com.example.personalityquiz

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Chronometer
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var chronometer: Chronometer
    private lateinit var button_start : Button
    private var running = false
    private lateinit var text_timer : TextView


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

        button_date.setBackgroundColor(Color.parseColor("#0000CD"))
        button_time.setBackgroundColor(Color.parseColor("#0000CD"))

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

        button_start.setBackgroundColor(Color.parseColor("#78D317"))

        text_timer = findViewById(R.id.text_timer)

        val countDownTimer: CountDownTimer = object : CountDownTimer(600000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000 + 1
                text_timer.text = "Zostało ci $secondsLeft sekund"
            }

            override fun onFinish() {
                SummaryActivity()
            }
        }
        countDownTimer.start()

        val spinner = findViewById<Spinner>(R.id.spinner_pozdro)
        val decyzje = arrayOf("Kieruję się intuicją – czuję, że tak będzie dobrze",
                                "Analizuję fakty, dane, plusy i minusy",
                                "Konsultuję się z innymi, zanim zdecyduję",
                                "Zazwyczaj odkładam decyzje, dopóki nie muszę ich podjąć",
                                "Działam spontanicznie, bez dłuższego zastanowienia")

        val adapter = ArrayAdapter( this,
                                    android.R.layout.simple_list_item_1,
                                    decyzje
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedDecyzje = decyzje[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        val button_zakoncz = findViewById<Button>(R.id.button_zakoncz)
        button_zakoncz.setOnClickListener {
            SummaryActivity()
        }

        button_zakoncz.setBackgroundColor(Color.parseColor("#FF0000"))

    }
}