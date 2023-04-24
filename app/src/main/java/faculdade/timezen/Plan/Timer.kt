package faculdade.timezen.Plan

import android.os.CountDownTimer
import android.widget.TextView

class Timer(
    private val countTime: TextView, //encontrar um jeito de fazer essa classe nao receber esse objeto
    private val workTimeInMilliseconds: Long,
    private val breakTimeInMilliseconds: Long,
    var tasksQuantity: Int
) {

    val oneSecond: Long = 1000
    private val initialBreakTimeCounter = breakTimeInMilliseconds/1000
    private val initialWorkTimerCounter = workTimeInMilliseconds/1000

    val workTimer: CountDownTimer = object: CountDownTimer(workTimeInMilliseconds, oneSecond) {

        var counter = initialWorkTimerCounter

        override fun onTick(millisUntilFinished: Long) {
            counterRoutine(counter)
            counter--
        }

        override fun onFinish() {
            println("terminado: $counter")
            countTime.text = "fim do trabalho"
            println("iniciando break timer")

            breakTimer.start()
            counter = initialWorkTimerCounter
        }
    }

    val breakTimer: CountDownTimer = object: CountDownTimer(breakTimeInMilliseconds, oneSecond) {

        var counter = initialBreakTimeCounter

        override fun onTick(millisUntilFinished: Long) {
            counterRoutine(counter)
            counter--
        }

        override fun onFinish() {
            println("terminado: $counter")
            countTime.text = "fim do descanço"
            println("iniciando work timer")

            tasksQuantity--

            if (tasksQuantity > 0) {
                workTimer.start()
                counter = initialBreakTimeCounter
            }
        }
    }

    private fun counterRoutine(counter: Long) {
        println("tick atualizado: $counter")
        countTime.text = counter.toString()
    }

}