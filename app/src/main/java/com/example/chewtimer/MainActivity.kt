package com.example.chewtimer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var timerButton: Button
    private var timer: CountDownTimer? = null
    private var isRunning = false
    private val initialTime: Long = 20000 // in milliseconds
    private var timeLeft: Long = initialTime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerButton = findViewById(R.id.timerButton)

        val chime = MediaPlayer.create(this, R.raw.bing_bong)

        timerButton.setOnClickListener {
            if (isRunning) {
                stopTimer()
            } else {
                startTimer(chime)
            }
        }
    }

    private fun startTimer(chime: MediaPlayer) {
        timer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
                val secondsLeft = (timeLeft / 1000).toString()
                timerButton.text = secondsLeft
            }

            override fun onFinish() {
                chime.start()
                timeLeft = initialTime
                startTimer(chime) // Restart the timer automatically
            }
        }.start()
        isRunning = true
    }

    private fun stopTimer() {
        timer?.cancel()
        isRunning = false
        timerButton.text = (timeLeft / 1000).toString()
    }
}