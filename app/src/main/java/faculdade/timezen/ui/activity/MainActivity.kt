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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}