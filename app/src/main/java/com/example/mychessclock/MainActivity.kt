package com.example.mychessclock

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.widget.AppCompatImageButton
import com.example.mychessclock.databinding.ActivityMainBinding
import com.example.mychessclock.databinding.GameEndPopupBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var gameEndPopupBinding: GameEndPopupBinding
    private lateinit var gameEndPopupView: View

    private var p1TimeLeftInMillis: Long = 61010
    private var p2TimeLeftInMillis: Long = 61010

    private var incrementInMillis: Long = 0

    private var gameStarted = false

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        supportActionBar?.title = "Tost"

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.settingsId -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                return true

            }
            else -> { super.onOptionsItemSelected(item) }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        gameEndPopupBinding = GameEndPopupBinding.inflate(layoutInflater)

        gameEndPopupView = gameEndPopupBinding.root

        setInitialTimers()

        val countDownInterval: Long = 1

        var timerPlayer1: CountDownTimer = object: CountDownTimer(p1TimeLeftInMillis, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {

            }
        }
        var timerPlayer2: CountDownTimer = object: CountDownTimer(p2TimeLeftInMillis, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {

            }
        }

        mainBinding.player1TimerBtn.setOnClickListener {
            timerPlayer1.cancel()
            if (gameStarted) {
                p1TimeLeftInMillis += incrementInMillis
            } else {
                gameStarted = true
            }
            mainBinding.player1TimerBtn.text = millisToMinsSecsMillis(p1TimeLeftInMillis)

             timerPlayer2 = object: CountDownTimer(p2TimeLeftInMillis, countDownInterval) {
                override fun onTick(millisUntilFinished: Long) {
                    mainBinding.player2TimerBtn.text = millisToMinsSecsMillis(millisUntilFinished)

                    p2TimeLeftInMillis = millisUntilFinished
                }

                override fun onFinish() {
                    gameEndPopupBinding.winnerTextView.text = "Black Wins!"
                    beepAndPopup()
                }
            }
            timerPlayer2.start()

            mainBinding.player1TimerBtn.isEnabled = false
            mainBinding.player2TimerBtn.isEnabled = true
        }

        mainBinding.player2TimerBtn.setOnClickListener {
            timerPlayer2.cancel()
            if (gameStarted) {
                p2TimeLeftInMillis += incrementInMillis
            } else {
                gameStarted = true
            }
            mainBinding.player2TimerBtn.text = millisToMinsSecsMillis(p2TimeLeftInMillis)

            timerPlayer1 = object: CountDownTimer(p1TimeLeftInMillis, countDownInterval) {
                override fun onTick(millisUntilFinished: Long) {
                    mainBinding.player1TimerBtn.text = millisToMinsSecsMillis(millisUntilFinished)
                    p1TimeLeftInMillis = millisUntilFinished
                }

                override fun onFinish() {
                    gameEndPopupBinding.winnerTextView.text = "White Wins!"
                    beepAndPopup()
                }
            }
            timerPlayer1.start()

            mainBinding.player1TimerBtn.isEnabled = true
            mainBinding.player2TimerBtn.isEnabled = false

        }

        mainBinding.refreshTimerBtn.setOnClickListener {
            timerPlayer1.cancel()
            timerPlayer2.cancel()
            setInitialTimers()
        }

        mainBinding.pauseTimerButton.setOnClickListener {
            mainBinding.player1TimerBtn.isEnabled = true
            mainBinding.player2TimerBtn.isEnabled = true
            gameStarted = false
            timerPlayer1.cancel()
            timerPlayer2.cancel()
        }
    }

    fun millisToMinsSecsMillis(millis: Long): String {
        val minutesLeft = TimeUnit.MILLISECONDS.toMinutes(millis)
        val secondsLeft = TimeUnit.MILLISECONDS.toSeconds(millis) - minutesLeft * 60
        val millisLeft = millis - minutesLeft * 60000 - secondsLeft * 1000
        val listofthis = listOf(minutesLeft, secondsLeft, millisLeft).joinToString(":") { "%02d".format(it) }
        Log.wtf("listofsthi", listofthis)
        return listofthis
    }

    private fun setInitialTimers() {
        gameStarted = false
        mainBinding.player1TimerBtn.isEnabled = true
        mainBinding.player2TimerBtn.isEnabled = true

        val sharedPref = this@MainActivity.getSharedPreferences("PREFPREF", Context.MODE_PRIVATE)
        p1TimeLeftInMillis = (sharedPref.getInt("HOURS", 0) * 3600000).toLong()
        p1TimeLeftInMillis += (sharedPref.getInt("MINUTES", 0) * 60000).toLong()
        p1TimeLeftInMillis += (sharedPref.getInt("SECONDS", 0) * 1000).toLong()
        p2TimeLeftInMillis = p1TimeLeftInMillis

        mainBinding.player1TimerBtn.text = millisToMinsSecsMillis(p1TimeLeftInMillis)
        mainBinding.player2TimerBtn.text = millisToMinsSecsMillis(p2TimeLeftInMillis)
        incrementInMillis = (sharedPref.getInt("MINUTES_INC", 0) * 60000).toLong()
        incrementInMillis += (sharedPref.getInt("SECONDS_INC", 0) * 1000).toLong()

    }

    private fun beepAndPopup() {

        val mediaPlayer = MediaPlayer.create(this@MainActivity, R.raw.fail)
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.reset()
            mediaPlayer.release()
        }
        mediaPlayer.start()
        val popupWindow = PopupWindow(gameEndPopupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        popupWindow.isFocusable = true
        popupWindow.elevation = 10.0f
        popupWindow.showAtLocation(gameEndPopupView, Gravity.CENTER, 0, 0)
    }

    override fun onResume() {
        super.onResume()
        setInitialTimers()
    }
}