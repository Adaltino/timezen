package faculdade.timezen.planModel

import faculdade.timezen.utils.InfoManipulator
import java.util.Timer

class PomodoroTimer(
    val workTime: Long,
    val breakTime: Long,
    val tasks: Int
) {
    private val timer = Timer()
    private lateinit var currentTask: Task

    var isRunning = false
    var isOnWorkStage = true

    fun start(plan: Plan, infoManipulator: InfoManipulator) {
        if (!isRunning) {
            isRunning = true
            infoManipulator.pomodoroTextViews.planName.text = plan.name()
            currentTask = Task(this, infoManipulator)
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
}