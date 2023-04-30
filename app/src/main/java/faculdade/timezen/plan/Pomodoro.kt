package faculdade.timezen.plan

import android.widget.TextView
import faculdade.timezen.utils.PomodoroTextViews

class Pomodoro(
    private var plan: Plan,
    private val textCountDown: PomodoroTextViews,
) {

    fun startPomodoro() {
        plan.pomodoroTimer.startWorkTime(plan, textCountDown)
    }

    fun pause() {
        //TODO(this one should use DataStore)
    }

    fun stop() {}

}
