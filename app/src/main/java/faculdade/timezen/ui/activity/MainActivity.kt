package faculdade.timezen.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import faculdade.timezen.R
import faculdade.timezen.dao.PlansDao
import faculdade.timezen.planModel.Plan
import faculdade.timezen.planModel.PomodoroTimer
import faculdade.timezen.ui.recyclerview.adapter.ListPlansAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val dao = PlansDao()

        recyclerView.adapter = ListPlansAdapter(this, plans = dao.buscarTodos())

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            val intent = Intent(this, AddPlanActivity::class.java)
            startActivity(intent)
        }
    }
}