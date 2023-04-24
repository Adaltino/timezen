package faculdade.timezen.Plan

import kotlin.system.measureTimeMillis

class Plan(
    private var name: String,
    private var timer: Timer,
) {
    fun startPlanLoop() {
        println("iniciando plano")
        timer.workTimer.start()
    }
}