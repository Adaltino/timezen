package faculdade.timezen.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import faculdade.timezen.R
import faculdade.timezen.dao.PlansDao
import faculdade.timezen.planModel.Plan
import faculdade.timezen.planModel.PomodoroTimer
import java.math.BigDecimal

class AddPlanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plan)

        val namePlan = findViewById<EditText>(R.id.editText_name_plan)
        val timePlan = findViewById<EditText>(R.id.editText_time_plan)
        val timeRestPlan = findViewById<EditText>(R.id.editText_time_rest_plan)
        val repeatPlan = findViewById<EditText>(R.id.editText_repeat_plan)
        val btnSavePlan = findViewById<Button>(R.id.button_save_plan)
        val btnBackHome = findViewById<Button>(R.id.button_back_home)

        btnSavePlan.setOnClickListener {
            val name = namePlan.text.toString()
            val time = timePlan.text.toString()
            val rest = timeRestPlan.text.toString()
            val repeat = repeatPlan.text.toString()

            val timeInt = if(time.isBlank()) {
                Long.MIN_VALUE
            } else {
                time.toLong()
            }
            val restInt = if (rest.isBlank()) {
                Long.MIN_VALUE
            } else {
                rest.toLong()
            }
            val repeatInt = if (repeat.isBlank()) {
                Int.MIN_VALUE
            } else {
                repeat.toInt()
            }

            val newPlan = Plan(
                name = name,
                pomodoroTimer = PomodoroTimer(
                    timeInt,
                    restInt,
                    repeatInt
                )
            )
            val dao = PlansDao()
            dao.addPlan(newPlan)
            finish()
        }

        btnBackHome.setOnClickListener {
            finish()
        }
    }
}