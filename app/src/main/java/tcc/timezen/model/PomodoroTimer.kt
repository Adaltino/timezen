package tcc.timezen.model

import tcc.timezen.listeners.TimerListener
import java.util.Timer

class PomodoroTimer(
    val workTime: Long,
    val breakTime: Long,
    val tasks: Int
) {
    private val timer = Timer()
    private lateinit var currentTask: Task

    var hasStarted = false
    var isRunning = false
    var isOnWorkStage = true

    fun start(timerListener: TimerListener) {
        if (!hasStarted) {
            hasStarted = true
            isRunning = true
            currentTask = Task(this, timerListener)
            timer.scheduleAtFixedRate(currentTask, 0, 1000)
        } else {
            resume()
        }
    }

    private fun resume() {
        isRunning = true
    }

    fun pause() {
        isRunning = false
    }

    fun stop() {
        isRunning = false
        hasStarted = false
        currentTask.cancel()
    }
}