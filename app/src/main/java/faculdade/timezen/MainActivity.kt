package faculdade.timezen

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import faculdade.timezen.plan.DataHelper
import java.util.Date
import java.util.Timer
import java.util.TimerTask

class MainActivity : Activity() {

    lateinit var dataHelper: DataHelper

    private val timer = Timer()

    lateinit var countTimeTextView: TextView
    lateinit var startButtonTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countTimeTextView = findViewById(R.id.countTime)
        startButtonTextView = findViewById(R.id.start_button)
        dataHelper = DataHelper(applicationContext)

        if (dataHelper.timerCounting()) {
            startTimer()
        } else {
            stopTimer()
            if (dataHelper.startTime() != null && dataHelper.stopTime() != null) {
                val time = Date().time - calcRestartTime()!!.time
                countTimeTextView.text = timeStringFromLong(time)
            }
        }

        timer.scheduleAtFixedRate(TimeTasklol(), 0, 500)
    }

    private inner class TimeTasklol: TimerTask() {
        override fun run() {
            if (dataHelper.timerCounting()) {
                val time = Date().time - dataHelper.startTime()!!.time
                countTimeTextView.text = timeStringFromLong(time)
            }
        }
    }

    fun startTimer() {
        dataHelper.setTimerCounting(true)
        startButtonTextView.text = "stawp"
    }

    fun stopTimer() {
        dataHelper.setTimerCounting(false)
        startButtonTextView.text = "start"
    }

    fun startStopAction(view: View) {
        if (dataHelper.timerCounting()) {
            dataHelper.setStopTime(Date())
            stopTimer()
        } else {
            if (dataHelper.stopTime() != null) {
                dataHelper.setStartTime(calcRestartTime())
                dataHelper.setStopTime(null)
            } else {
                dataHelper.setStartTime(Date())
            }
            startTimer()
        }
    }

    fun resetTimer(view: View) {
        dataHelper.setStopTime(null)
        dataHelper.setStartTime(null)
        stopTimer()
        countTimeTextView.text = timeStringFromLong(0)
    }

    private fun calcRestartTime(): Date? {
        return Date(System.currentTimeMillis() + dataHelper.startTime()!!.time - dataHelper.stopTime()!!.time)
    }
    private fun timeStringFromLong(ms: Long): String {
        val seconds = (ms / 1000) % 60
        val minutes =  ms / (1000 * 60) % 60
        val hours =    ms / (1000 * (60*2)) % 24

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hours: Long, minutes: Long, seconds: Long): String {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}