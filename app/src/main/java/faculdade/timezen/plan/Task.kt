package faculdade.timezen.plan

import android.util.Log
import faculdade.timezen.utils.PomodoroTextViews
import faculdade.timezen.utils.Translator
import java.util.TimerTask

class Task(
    private val pomodoroTimer: PomodoroTimer,
    private val pomodoroTextViews: PomodoroTextViews,
) : TimerTask() {

    private val translator = Translator()
    private var timePassed: Long = 0
    private val totalSessions = pomodoroTimer.getTasks()
    private var remainingSessions = pomodoroTimer.getTasks()
    private val initialWorkTime = pomodoroTimer.getWorkTime()
    private val initialBreakTime = pomodoroTimer.getBreakTime()
    private var isNewSession = true

    override fun run() {
        if (pomodoroTimer.isRunning) {
            val timeRemaining: Long = getRemainingTime()

            if (timeHasEnded(timeRemaining)) {
                changePomodoroStage()
                if (remainingSessions == 0) {
                    resetVariables()
                    endTimer()
                    return
                }
                decrementSession()
                updateCounter(timeRemaining)
            } else {
                timePassed += 1000
                updateCounter(timeRemaining)
            }
        }
    }

    private fun getRemainingTime(): Long {
        return if (pomodoroTimer.isOnWorkStage) {
            updateStage("Work time!")
            initialWorkTime - timePassed
        } else {
            updateStage("Break time!")
            initialBreakTime - timePassed
        }
    }

    private fun updateStage(s: String) {
        try {
            pomodoroTextViews.planStage.text = s
        } catch (e: Exception) {
            Log.e("Exception", "$e")
        }
    }

    private fun updateCounter(timeRemaining: Long) {
        try {
            pomodoroTextViews.counter.text = translator.timeStringFromLong(timeRemaining)
        } catch (e: Exception) {
            Log.e("Exception", "$e")
        }
    }

    private fun timeHasEnded(timeRemaining: Long): Boolean = timeRemaining <= 0

    private fun resetVariables() {
        pomodoroTimer.isRunning = false
        pomodoroTimer.isOnWorkStage = true
        try {
            pomodoroTextViews.planStage.text = "Pomodoro finished!"
            pomodoroTextViews.counter.text = translator.timeStringFromLong(0)
        } catch (e: Exception) {
            Log.e("Exception", "$e")
        }
    }

    private fun endTimer() {
        this.cancel()
    }

    private fun changePomodoroStage() {
        pomodoroTimer.isOnWorkStage = !pomodoroTimer.isOnWorkStage
        timePassed = 0
    }

    private fun decrementSession() {
        if (!isNewSession) {
            remainingSessions--
        }
        isNewSession = !isNewSession
    }

}