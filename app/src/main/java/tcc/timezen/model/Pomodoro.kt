package tcc.timezen.model

import tcc.timezen.listeners.TimerListener

class Pomodoro(
    private var plan: Plan,
) {
    fun start(timerListener: TimerListener) {
        plan.pomodoroTimer().start(timerListener)
    }

    fun pause() {
        //TODO(this one should use DataStore...?)
        plan.pomodoroTimer().pause()
    }

    fun stop() {
        plan.pomodoroTimer().stop()
    }

    fun plan(): Plan = plan
    fun isRunning() = plan.pomodoroTimer().isRunning
}