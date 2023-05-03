package faculdade.timezen.plan

import faculdade.timezen.utils.PomodoroTextViews

class Pomodoro(
    private var plan: Plan,
    private val textCountDown: PomodoroTextViews,
) {

    fun start() {
        plan.pomodoroTimer().start(plan, textCountDown)
    }

    fun resume() {
        plan.pomodoroTimer().resume()
    }

    fun pause() {
        //TODO(this one should use DataStore...?)
        plan.pomodoroTimer().pause()
    }

    fun stop() {
        plan.pomodoroTimer().stop()
    }

    fun plan(): Plan = plan
}
