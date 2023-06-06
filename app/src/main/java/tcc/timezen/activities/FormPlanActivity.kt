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

        val catPlan: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView_category_plan)
        val lvlPlan: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView_importanceLevel_plan)

        val adapterCat = ArrayAdapter(this, R.layout.item_dropdown, dbTimezen.getCategoryNames())
        val adapterLvl = ArrayAdapter(this, R.layout.item_dropdown, dbTimezen.getImportanceLevelNames())

        catPlan.setAdapter(adapterCat)
        lvlPlan.setAdapter(adapterLvl)

        val namePlan = findViewById<TextInputEditText>(R.id.text_edit_plan_name)
        val workPlan = findViewById<TextInputEditText>(R.id.text_edit_plan_time)
        val breakPlan = findViewById<TextInputEditText>(R.id.text_edit_plan_break)
        val repeatPlan = findViewById<TextInputEditText>(R.id.text_edit_plan_repeat)

        catPlan.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            val idCategory = dbTimezen.getCategoryById(selectedItem)
            Toast.makeText(this, "ID - $idCategory | Nome: $selectedItem", Toast.LENGTH_LONG).show()
        }

        lvlPlan.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            val idLvl = dbTimezen.getImportanceLevelById(selectedItem)
            Toast.makeText(this, "ID - $idLvl | Nome: $selectedItem", Toast.LENGTH_LONG).show()
        }

        mBinding.buttonSavePlan.setOnClickListener {
            var name = namePlan.text.toString()
            val workTime = workPlan.text.toString()
            val breakTime = breakPlan.text.toString()
            var category = catPlan.text.toString()
            var level = lvlPlan.text.toString()
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

            if (category.isBlank()) {
                category = "Trabalho"
            }

            if (level.isBlank()) {
                level = "Muito Baixo"
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
                ),
                category,
                level
            )
            dao.addPlan(newPlan)
            finish()
        }

        mBinding.buttonBackPlan.setOnClickListener {
            finish()
        }
    }
}