package tcc.timezen.model

import tcc.timezen.listeners.TimerListener
import tcc.timezen.utils.Translator
import java.util.TimerTask

class Task(
    private val pomodoroTimer: PomodoroTimer,
    private val listener: TimerListener
) : TimerTask() {

    companion object {
        private const val ONE_SECOND = 1000
    }

    private val t = Translator()

    private var timePassed: Long = 0
    private var remainingSessions = pomodoroTimer.tasks
    private val initialWorkTime = pomodoroTimer.workTime
    private val initialBreakTime = pomodoroTimer.breakTime
    private var isWorkStage = true

    /*

        mental note for self:

            1 session is made up of two timers: work and break. ( plan.getTasksQuantity() )
            both work and break are called stages

            "in the break stage, you rest a lot"
            "there are 2 sessions left, that is 2 stages of work time and 2 stages of break time"

     */
    override fun run() {
        if (pomodoroTimer.isRunning) {
            val timeRemaining = getRemainingTime()

            updateCounter(timeRemaining)

            if (!timeHasEnded(timeRemaining)) {
                timePassed += ONE_SECOND
            } else {
                changeStage()
                if (remainingSessions == 0) {
                    resetVariables()
                    this.cancel()
                    return
                }
                changeSession()
            }
        }
    }

    private fun timeHasEnded(timeRemaining: Long): Boolean = timeRemaining <= 0

    private fun getRemainingTime(): Long {
        return if (pomodoroTimer.isOnWorkStage) {
            initialWorkTime - timePassed
        } else {
            initialBreakTime - timePassed
        }
    }

    private fun updateCounter(timeRemaining: Long) {
        listener.onTick(t.timeStringFromLong(timeRemaining))
    }

    private fun resetVariables() {
        pomodoroTimer.isRunning = false
        pomodoroTimer.isOnWorkStage = true
        pomodoroTimer.hasStarted = false
        listener.onFinish()
    }

    private fun changeStage() {
        pomodoroTimer.isOnWorkStage = !pomodoroTimer.isOnWorkStage
        timePassed = 0
        listener.onStageChange(pomodoroTimer.isOnWorkStage)
    }

    private fun changeSession() {
        decrementSession()
        invertStageType()
        listener.onSessionChange(remainingSessions, isWorkStage)
    }

    private fun decrementSession() {
        if (!isWorkStage) {
            remainingSessions--
        }
    }

    private fun invertStageType() {
        isWorkStage = !isWorkStage
    }
}
