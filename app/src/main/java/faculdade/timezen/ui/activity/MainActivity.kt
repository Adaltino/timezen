package faculdade.timezen.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import faculdade.timezen.R
import faculdade.timezen.planModel.Plan
import faculdade.timezen.planModel.PomodoroTimer
import faculdade.timezen.ui.recyclerview.adapter.ListPlansAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = ListPlansAdapter(this, listOf(
            Plan(name = "Estudar", PomodoroTimer(5000, 3000, 3)),
            Plan(name = "Meditar", PomodoroTimer(5000, 3000, 3)),
            Plan(name = "Programar", PomodoroTimer(5000, 3000, 3))
        ))
    }
}