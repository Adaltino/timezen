package faculdade.timezen.planModel

class Plan(
    private var name: String,
    private var pomodoroTimer: PomodoroTimer,
) {
    fun create(name: String, pomodoroTimer: PomodoroTimer) {
        //TODO? lol
    }

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
    fun pomodoroTimer(): PomodoroTimer = pomodoroTimer
    fun getWorkTime(): Long = pomodoroTimer.workTime
    fun getBreakTime(): Long = pomodoroTimer.breakTime
    fun getTaskQuantity(): Int = pomodoroTimer.tasks
}