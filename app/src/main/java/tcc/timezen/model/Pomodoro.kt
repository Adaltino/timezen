package tcc.timezen.model

import tcc.timezen.utils.InfoManipulator

class Pomodoro(
    private var plan: Plan,
    private val infoManipulator: InfoManipulator
) {
    fun start() {
        plan.pomodoroTimer().start(plan, infoManipulator)
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