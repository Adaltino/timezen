package tcc.timezen.model

class Plan(
    private var name: String,
    private var pomodoroTimer: PomodoroTimer,
    private var category: String,
    private var importanceLevel: String
) {
    fun name(): String = name
    fun category(): String = category
    fun importanceLevel(): String = importanceLevel
    fun pomodoroTimer(): PomodoroTimer = pomodoroTimer
    fun getWorkTime(): Long = pomodoroTimer.workTime
    fun getBreakTime(): Long = pomodoroTimer.breakTime
    fun getTaskQuantity(): Int = pomodoroTimer.tasks
}