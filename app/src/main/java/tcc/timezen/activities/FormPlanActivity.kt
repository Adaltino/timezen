package tcc.timezen.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputEditText
import tcc.timezen.R
import tcc.timezen.dao.PlanDao
import tcc.timezen.database.DBTimezen
import tcc.timezen.databinding.ActivityFormPlanBinding
import tcc.timezen.model.Plan
import tcc.timezen.model.PomodoroTimer
import tcc.timezen.utils.Translator

class FormPlanActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityFormPlanBinding
    private lateinit var dbTimezen: DBTimezen
    val t = Translator()
    val dao = PlanDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_form_plan)
        dbTimezen = DBTimezen(this)

        val autoCompleteCategory: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView_category_plan)
        val autoCompleteLvl: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView_importanceLevel_plan)

        val adapterCat = ArrayAdapter(this, R.layout.item_dropdown, dbTimezen.getCategoryNames())
        val adapterLvl = ArrayAdapter(this, R.layout.item_dropdown, dbTimezen.getImportanceLevelNames())

        autoCompleteCategory.setAdapter(adapterCat)
        autoCompleteLvl.setAdapter(adapterLvl)

        val namePlan = findViewById<TextInputEditText>(R.id.text_edit_plan_name)
        val workPlan = findViewById<TextInputEditText>(R.id.text_edit_plan_time)
        val breakPlan = findViewById<TextInputEditText>(R.id.text_edit_plan_break)
        //val catPlan = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView_category_plan)
        //val lvl = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView_importanceLevel_plan)
        val repeatPlan = findViewById<TextInputEditText>(R.id.text_edit_plan_repeat)

        mBinding.buttonSavePlan.setOnClickListener {
            var name = namePlan.text.toString()
            val workTime = workPlan.text.toString()
            val breakTime = breakPlan.text.toString()
            val repeat = repeatPlan.text.toString()

            var workLong: Long = t.getMsFromMinute(45)
            var breakLong: Long = t.getMsFromMinute(10)
            var repeatInt = 3

            if (name.isBlank()) {
                name = "Plano pomodoro"
            }

            if (workTime.isNotBlank()) {
                workLong = t.getMsFromMinute(workTime.toLong())
            }
            if (breakTime.isNotBlank()) {
                breakLong = t.getMsFromMinute(breakTime.toLong())
            }
            if (repeat.isNotBlank()) {
                repeatInt = repeat.toInt()
            }

            val newPlan = Plan(
                name = name,
                pomodoroTimer = PomodoroTimer(
                    workLong,
                    breakLong,
                    repeatInt
                )
            )
            dao.addPlan(newPlan)
            finish()
        }

        mBinding.buttonBackPlan.setOnClickListener {
            finish()
        }
    }
}