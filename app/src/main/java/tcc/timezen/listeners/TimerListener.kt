package tcc.timezen.listeners

interface TimerListener {
    fun onTick(timeString: String)
    fun onStageChange(isOnWorkStage: Boolean)
    fun onSessionChange(sessionsLeft: Int, isOnWorkStage: Boolean)
    fun onFinish()
}