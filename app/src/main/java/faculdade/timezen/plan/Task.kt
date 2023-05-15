package faculdade.timezen.plan

import faculdade.timezen.utils.InfoManipulator
import faculdade.timezen.utils.Text
import faculdade.timezen.utils.Translator
import java.util.TimerTask

class Task(
    private val pomodoroTimer: PomodoroTimer,
    private val infoMan: InfoManipulator,
) : TimerTask() {

    private val ONE_SECOND = 1000
    private val translator = Translator()

    private var timePassed: Long = 0
    private var remainingSessions = pomodoroTimer.tasks
    private val initialWorkTime = pomodoroTimer.workTime
    private val initialBreakTime = pomodoroTimer.breakTime
    private var isWorkSession = true

    override fun run() {
        if (pomodoroTimer.isRunning) {
            val timeRemaining: Long = getRemainingTime()

            if (!timeHasEnded(timeRemaining)) {
                timePassed += ONE_SECOND
            } else {
                changeStage()
                if (remainingSessions == 0) {
                    resetVariables()
                    endTimer()
                    return
                }
                changeSession()
            }

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
        infoMan.setText(Text.TEXT_VIEW_PLAN_STAGE.ordinal, s)
    }

    private fun updateCounter(timeRemaining: Long) {
        val time = translator.timeStringFromLong(timeRemaining)
        infoMan.setText(Text.TEXT_VIEW_COUNTER.ordinal, time)
    }

    private fun timeHasEnded(timeRemaining: Long): Boolean = timeRemaining <= 0

    private fun resetVariables() {
        pomodoroTimer.isRunning = false
        pomodoroTimer.isOnWorkStage = true
        val time = translator.timeStringFromLong(0)
        infoMan.setText(Text.TEXT_VIEW_PLAN_STAGE.ordinal, "Pomodoro finished!")
        infoMan.setText(Text.TEXT_VIEW_COUNTER.ordinal, time)
    }

    private fun endTimer() {
        this.cancel()
    }

    private fun changeStage() {
        pomodoroTimer.isOnWorkStage = !pomodoroTimer.isOnWorkStage
        timePassed = 0
    }

    private fun changeSession() {
        decrementSession()
        invertSessionType()
    }

    private fun decrementSession() {
        if (!isWorkSession) {
            remainingSessions--
        }
    }

    private fun invertSessionType() {
        isWorkSession = !isWorkSession
    }
}