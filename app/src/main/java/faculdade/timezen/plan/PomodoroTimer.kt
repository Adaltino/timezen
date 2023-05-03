package faculdade.timezen.plan

import faculdade.timezen.utils.PomodoroTextViews
import java.util.Timer

class PomodoroTimer(
    private val workTime: Long,
    private val breakTime: Long,
    private val tasks: Int
) {
    private val timer = Timer()
    private lateinit var currentTask: Task

    var isRunning = false
    var isOnWorkStage = true

    fun start(plan: Plan, pomodoroTextViews: PomodoroTextViews) {
        if (!isRunning) {
            isRunning = true
            pomodoroTextViews.planName.text = plan.name()
            currentTask = Task(this, pomodoroTextViews)
            timer.scheduleAtFixedRate(currentTask, 0, 1000)
        } else {
            resume()
        }
    }

    fun resume() {
        isRunning = true
    }

    fun pause() {
        isRunning = false
    }

    fun stop() {
        isRunning = false
        currentTask.cancel()
    }

    fun getWorkTime(): Long = workTime
    fun getBreakTime(): Long = breakTime
    fun getTasks(): Int = tasks

}