package faculdade.timezen.ui.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.material.snackbar.Snackbar
import faculdade.timezen.R
import faculdade.timezen.planModel.Plan
import faculdade.timezen.planModel.Pomodoro
import faculdade.timezen.planModel.PomodoroTimer
import faculdade.timezen.utils.InfoManipulator
import faculdade.timezen.utils.PomodoroTextViews
import java.util.Timer
import java.util.prefs.Preferences

class MainActivity : AppCompatActivity() {

    companion object {
        // https://developer.android.com/topic/libraries/architecture/datastore
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "prefs")
    }

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
        val pomodoroTextViews =
            PomodoroTextViews(counterTextView, planNameTextView, planStageTextView)
        pomodoro =
            Pomodoro(plan, InfoManipulator(pomodoroTextViews, dataStore))


        findViewById<Button>(R.id.start_button)
            .setOnClickListener {
                Log.d("Button", "start pomodoro button clicked")
                Snackbar.make(it, "pomodoro iniciado", 2000).show()
                pomodoro.start()
            }

        findViewById<Button>(R.id.reset_button)
            .setOnClickListener {
                //TODO()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}