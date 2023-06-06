package tcc.timezen.model

class Plan(
    private var name: String,
    private var pomodoroTimer: PomodoroTimer,
    private var category: String,
    private var importanceLevel: String
) {
    fun edit(name: String, pomodoroTimer: PomodoroTimer) {
        this.pomodoroTimer = pomodoroTimer
        this.name = name
    }

    fun edit(name: String, workTime: Long, breakTime: Long, tasks: Int) {
        this.pomodoroTimer = PomodoroTimer(workTime, breakTime, tasks)
        this.name = name
    }

    fun delete() {}
    fun select() {}

    fun name(): String = name
    fun category(): String = category
    fun importanceLevel(): String = importanceLevel
    fun pomodoroTimer(): PomodoroTimer = pomodoroTimer
    fun getWorkTime(): Long = pomodoroTimer.workTime
    fun getBreakTime(): Long = pomodoroTimer.breakTime
    fun getTaskQuantity(): Int = pomodoroTimer.tasks
}