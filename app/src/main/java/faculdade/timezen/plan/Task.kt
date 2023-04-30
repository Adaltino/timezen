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
    private var remainingSessions = pomodoroTimer.getTasks()

    override fun run() {
        if (pomodoroTimer.isRunning) {
            val timeRemaining: Long
            if (pomodoroTimer.isOnWorkStage) {
                timeRemaining = pomodoroTimer.getWorkTime() - timePassed
                try {
                    pomodoroTextViews.planStage.text = "Work time!"
                } catch (e: Exception) {
                    Log.e("Exception", "$e")
                }
            } else {
                timeRemaining = pomodoroTimer.getBreakTime() - timePassed
                try {
                    pomodoroTextViews.planStage.text = "Break time!"
                } catch (e: Exception) {
                    Log.e("Exception", "$e")
                }
            }

            if (timeHasEnded(timeRemaining)) {
                pomodoroTimer.isOnWorkStage = !pomodoroTimer.isOnWorkStage
                timePassed = 0

                if (remainingSessions == 0) {
                    pomodoroTimer.isRunning = false
                    this.cancel()
                    try {
                        pomodoroTextViews.planStage.text = "Pomodoro finished!"
                        pomodoroTextViews.counter.text = translator.timeStringFromLong(0)
                    } catch (e: Exception) {
                        Log.e("Exception", "$e")
                    }
                    return
                }

                remainingSessions--
            }

            Log.d("task", "restando: $timeRemaining, work: ${pomodoroTimer.isOnWorkStage}, session: $remainingSessions")

            timePassed += 1000
            try {
                pomodoroTextViews.counter.text = translator.timeStringFromLong(timeRemaining)
            } catch (e: Exception) {
                Log.e("ERROR","$e")
            }
        }
    }

    private fun timeHasEnded(timeRemaining: Long): Boolean = timeRemaining <= 0

}