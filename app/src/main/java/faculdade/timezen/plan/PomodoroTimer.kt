package faculdade.timezen.plan

import android.widget.TextView
import faculdade.timezen.utils.PomodoroTextViews
import java.util.Timer

class PomodoroTimer(
    private val workTime: Long,
    private val breakTime: Long,
    private val tasks: Int
) {
    private val timer = Timer()

    var isRunning = false
    var isOnWorkStage = true


    fun startWorkTime(plan: Plan, pomodoroTextViews: PomodoroTextViews) {
        if (!isRunning) {
            isRunning = true
            pomodoroTextViews.planName.text = plan.name
            timer.scheduleAtFixedRate(Task(this, pomodoroTextViews), 0, 1000)
        }
    }

    fun startBreakTime(plan: Plan, textView: TextView) {

    }

    fun stopPomodoro() {
        isRunning = false
    }

    fun getWorkTime(): Long = workTime
    fun getBreakTime(): Long = breakTime
    fun getTasks(): Int = tasks

}