package faculdade.timezen

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.material.snackbar.Snackbar
import faculdade.timezen.plan.PomodoroTimer
import faculdade.timezen.plan.Plan
import faculdade.timezen.plan.Pomodoro
import faculdade.timezen.utils.PomodoroTextViews
import java.util.Timer

class MainActivity : Activity() {
    // https://developer.android.com/topic/libraries/architecture/datastore
    private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = "prefs")

    private lateinit var pomodoro: Pomodoro
    private val timer = Timer()
    private val pomodoroTimer = PomodoroTimer(5000, 3000, 3)
    private val plan = Plan("alonso", pomodoroTimer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeFunctions()
    }

    private fun initializeFunctions() {
        val counterTextView: TextView = findViewById(R.id.countTime)
        val planNameTextView: TextView = findViewById(R.id.planName)
        val planStageTextView: TextView = findViewById(R.id.planStage)
        pomodoro = Pomodoro(plan, PomodoroTextViews(counterTextView, planNameTextView, planStageTextView))

        findViewById<Button>(R.id.start_button)
            .setOnClickListener {
                Log.d("Button", "start pomodoro button clicked")
                Snackbar.make(it, "pomodoro iniciado", 2000).show()
                pomodoro.startPomodoro()
            }

        findViewById<Button>(R.id.reset_button)
            .setOnClickListener {

            }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}