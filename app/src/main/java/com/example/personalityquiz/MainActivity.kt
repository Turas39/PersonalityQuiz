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
                goToSummaryActivity()
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
            goToSummaryActivity()
        }

        button_zakoncz.setBackgroundColor(Color.parseColor("#FF0000"))


    }

    private fun goToSummaryActivity() {

        val radiobutton1 = findViewById<RadioButton>(R.id.radiobutton_1).isChecked
        val radiobutton2 = findViewById<RadioButton>(R.id.radiobutton_2).isChecked
        val radiobutton3 = findViewById<RadioButton>(R.id.radiobutton_3).isChecked
        val radiobutton4 = findViewById<RadioButton>(R.id.radiobutton_4).isChecked

        val checkbox1 = findViewById<CheckBox>(R.id.checkbox_1).isChecked
        val checkbox2 = findViewById<CheckBox>(R.id.checkbox_2).isChecked
        val checkbox3 = findViewById<CheckBox>(R.id.checkbox_3).isChecked
        val checkbox4 = findViewById<CheckBox>(R.id.checkbox_4).isChecked
        val checkbox5 = findViewById<CheckBox>(R.id.checkbox_5).isChecked
        val checkbox6 = findViewById<CheckBox>(R.id.checkbox_6).isChecked

        val seekbar = findViewById<SeekBar>(R.id.seekbar).progress
        val spinner = findViewById<Spinner>(R.id.spinner_pozdro).selectedItem.toString()

        var ekstrawertycznyOdkrywca = 0
        var introwertycznyRefleksyjny = 0
        var ambitnyStrateg = 0
        var empatycznyIdealista = 0

        if (radiobutton1) {
            ekstrawertycznyOdkrywca += 1
            empatycznyIdealista += 1
        } else if (radiobutton2) {
            introwertycznyRefleksyjny += 1
            empatycznyIdealista += 1
        } else if (radiobutton3) {
            ekstrawertycznyOdkrywca += 2
        } else if (radiobutton4) {
            ambitnyStrateg += 2
        }

        if (checkbox1) {
            empatycznyIdealista += 1
            introwertycznyRefleksyjny += 1
        }
        if (checkbox2) {
            ekstrawertycznyOdkrywca += 1
            ambitnyStrateg += 1
        }
        if (checkbox3) {
            ekstrawertycznyOdkrywca += 1
            ambitnyStrateg += 1
        }
        if (checkbox4) {
            introwertycznyRefleksyjny += 1
            ambitnyStrateg += 1
        }
        if (checkbox5) {
            ekstrawertycznyOdkrywca += 1
            ambitnyStrateg += 2
        }
        if (checkbox6) {
            empatycznyIdealista += 2
        }

        when (seekbar) {
            in 1..4 -> ekstrawertycznyOdkrywca += 2
            in 5..6 -> empatycznyIdealista += 2
            in 7..8 -> introwertycznyRefleksyjny += 2
            in 9..10 -> ambitnyStrateg += 2
        }

        when (spinner) {
            "Kieruję się intuicją – czuję, że tak będzie dobrze" -> {
                ekstrawertycznyOdkrywca += 1
                empatycznyIdealista += 1
            }
            "Analizuję fakty, dane, plusy i minusy" -> {
                introwertycznyRefleksyjny += 1
                ambitnyStrateg += 1
            }
            "Konsultuję się z innymi, zanim zdecyduję" -> {
                empatycznyIdealista += 1
            }
            "Zazwyczaj odkładam decyzje, dopóki nie muszę ich podjąć" -> {
                introwertycznyRefleksyjny += 1
            }
            "Działam spontanicznie, bez dłuższego zastanowienia" -> {
                ekstrawertycznyOdkrywca += 1
            }
        }

        val intent = Intent(this, SummaryActivity::class.java)
        intent.putExtra("odkrywca", ekstrawertycznyOdkrywca)
        intent.putExtra("refleksyjny", introwertycznyRefleksyjny)
        intent.putExtra("strateg", ambitnyStrateg)
        intent.putExtra("idealista", empatycznyIdealista)


        startActivity(intent)

    }
}