package faculdade.timezen

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import faculdade.timezen.Plan.Plan
import faculdade.timezen.Plan.Timer

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    fun startTimeCounter(view: View) {
        val countTimeTextView: TextView = findViewById(R.id.countTime)
        val planNameTextView: TextView = findViewById(R.id.planName)
        val planStageTextView: TextView = findViewById(R.id.planStage)

        val timer = Timer(countTimeTextView, 3000, 2000, 3)
        val plan = Plan("lololol", timer);

        plan.startPlanLoop()
    }

    fun stopTimeCounter(view: View) {
        //TODO
    }
}