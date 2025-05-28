package com.example.personalityquiz

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
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
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
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

        val datePicker = findViewById<DatePicker>(R.id.date)
        val timePicker = findViewById<TimePicker>(R.id.time)

        timePicker.setIs24HourView(true)

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

        fun SummaryActivity() {

            val selectedRadioId = findViewById<RadioGroup>(R.id.radiogroup).checkedRadioButtonId
            val selectedRadioText = findViewById<RadioButton>(selectedRadioId)?.text?.toString() ?: "Brak wyboru"

            val seekbarValue = findViewById<SeekBar>(R.id.seekbar).progress
            val spinnerValue = findViewById<Spinner>(R.id.spinner_pozdro).selectedItem.toString()

            val selectedCheckboxes = mutableListOf<String>()
            if (findViewById<CheckBox>(R.id.checkbox_1).isChecked) selectedCheckboxes.add("Cel i sens")
            if (findViewById<CheckBox>(R.id.checkbox_2).isChecked) selectedCheckboxes.add("Uznanie")
            if (findViewById<CheckBox>(R.id.checkbox_3).isChecked) selectedCheckboxes.add("Rozwój")
            if (findViewById<CheckBox>(R.id.checkbox_4).isChecked) selectedCheckboxes.add("Stabilność")
            if (findViewById<CheckBox>(R.id.checkbox_5).isChecked) selectedCheckboxes.add("Rywalizacja")
            if (findViewById<CheckBox>(R.id.checkbox_6).isChecked) selectedCheckboxes.add("Swoboda")

            val checkboxSummary = selectedCheckboxes.joinToString(", ")

            val selectedDate = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"
            val selectedTime = "${timePicker.hour}:${timePicker.minute}"

            val intent = Intent(this, SummaryActivity::class.java)
            intent.putExtra("radio", selectedRadioText)
            intent.putExtra("seekbar", seekbarValue)
            intent.putExtra("spinner", spinnerValue)
            intent.putExtra("checkboxes", checkboxSummary)
            intent.putExtra("date", selectedDate)
            intent.putExtra("time", selectedTime)

            startActivity(intent)

        }

    }
}