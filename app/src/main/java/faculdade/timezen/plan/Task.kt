package faculdade.timezen.plan

import android.util.Log
import faculdade.timezen.utils.PomodoroTextViews
import faculdade.timezen.utils.Translator
import java.util.TimerTask

class Task(
    private val pomodoroTimer: PomodoroTimer,
    private val pomodoroTextViews: PomodoroTextViews,
): TimerTask() {

    private val translator = Translator()
    private var timePassed: Long = 0
    private var totalSessions = pomodoroTimer.getTasks()
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
                    return
                }
                decrementSession()
            }

            Log.d("task", "restando: $timeRemaining, " +
                    "work: ${pomodoroTimer.isOnWorkStage}, session: $remainingSessions")

            timePassed += 1000
            updateCounter(timeRemaining)
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
            Log.e("Exception","$e")
        }
    }

    private fun timeHasEnded(timeRemaining: Long): Boolean = timeRemaining <= 0

    private fun resetVariables() {
        pomodoroTimer.isRunning = false
        pomodoroTimer.isOnWorkStage = true
        this.cancel()
        try {
            pomodoroTextViews.planStage.text = "Pomodoro finished!"
            pomodoroTextViews.counter.text = translator.timeStringFromLong(0)
        } catch (e: Exception) {
            Log.e("Exception", "$e")
        }
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