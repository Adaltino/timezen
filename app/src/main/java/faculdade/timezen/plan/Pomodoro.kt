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

}
