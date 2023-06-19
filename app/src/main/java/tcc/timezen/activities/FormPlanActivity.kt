package tcc.timezen.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import tcc.timezen.R
import tcc.timezen.database.DBTimezen
import tcc.timezen.databinding.ActivityFormPlanBinding
import tcc.timezen.model.Plan
import tcc.timezen.utils.Translator

class FormPlanActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityFormPlanBinding
    private lateinit var db: DBTimezen
    private val t = Translator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_form_plan)

        mBinding.buttonBackPlan.setOnClickListener {
            finish()
        }

        db = DBTimezen(this)

        val adapterCat = ArrayAdapter(this, R.layout.item_dropdown, db.getCategoryNames())
        val adapterLvl = ArrayAdapter(this, R.layout.item_dropdown, db.getImportanceLevelNames())

        mBinding.autoCompleteTextViewCategoryPlan.setAdapter(adapterCat)
        mBinding.autoCompleteTextViewImportanceLevelPlan.setAdapter(adapterLvl)

        val extras: Bundle? = intent.extras

        if (extras == null) {
            initializeRegisterButton(false, null)
        } else {
            val originalPlan = db.getPlanById(extras.getInt("id"))
            setTextViewsToExistingPlan(originalPlan)
            initializeRegisterButton(true, originalPlan)
        }
    }

    private fun initializeRegisterButton(isEditingPlan: Boolean, plan: Plan?) {

        if (isEditingPlan) {
            mBinding.buttonSavePlan.text = "finalizar edição"
        }

        mBinding.buttonSavePlan.setOnClickListener {

            var name = mBinding.textEditPlanName.text.toString()
            val inputtedWorkTime = mBinding.textEditPlanWork.text.toString()
            val inputtedBreakTime = mBinding.textEditPlanBreak.text.toString()
            var category = mBinding.autoCompleteTextViewCategoryPlan.text.toString()
            var level = mBinding.autoCompleteTextViewImportanceLevelPlan.text.toString()
            val inputtedRepeatQuantity = mBinding.textEditPlanRepeat.text.toString()

            //use this if you want to insert a plan with seconds worth of duration
            //for development's sake
//            var workTimeInMs = t.getMsFromSecond(2)
//            var breakTimeInMs = t.getMsFromSecond(2)
            var workTimeInMs = t.getMsFromMinute(45)
            var breakTimeInMs = t.getMsFromMinute(10)
            var repeatInt = 3

            if (name.isBlank()) {
                name = "Plano pomodoro"
            }

            if (inputtedWorkTime.isNotBlank()) {
                workTimeInMs = t.getMsFromMinute(inputtedWorkTime.toLong())
            }

            if (inputtedBreakTime.isNotBlank()) {
                breakTimeInMs = t.getMsFromMinute(inputtedBreakTime.toLong())
            }

            if (category.isBlank()) {
                category = "Trabalho"
            }

            if (level.isBlank()) {
                level = "Muito Baixo"
            }

            if (inputtedRepeatQuantity.isNotBlank()) {
                repeatInt = inputtedRepeatQuantity.toInt()
            }

            val idCat = db.getCategoryById(category)
            val idLvl = db.getImportanceLevelById(level)

            if (isEditingPlan) {
                val id = db.getPlanId(plan!!.name())
                db.updatePlan(id, name, workTimeInMs.toInt(), breakTimeInMs.toInt(), repeatInt, idCat, idLvl)
            } else {
                db.insertPlan(name, workTimeInMs.toInt(), breakTimeInMs.toInt(), repeatInt, idCat, idLvl)
            }

            finish()
        }
    }

    private fun setTextViewsToExistingPlan(originalPlan: Plan) {
        val originalWorkTime =
            t.getAbsoluteHumanTime("minuteNumbersOnly", originalPlan.getWorkTime())
        val originalBreakTime =
            t.getAbsoluteHumanTime("minuteNumbersOnly", originalPlan.getBreakTime())
        val originalTaskQuantity = originalPlan.getTaskQuantity().toString()

        mBinding.toolbarFormPlan.title = "Editar Plano"
        mBinding.textEditPlanName.setText(originalPlan.name())
        mBinding.textEditPlanWork.setText(originalWorkTime)
        mBinding.textEditPlanBreak.setText(originalBreakTime)
        mBinding.textEditPlanRepeat.setText(originalTaskQuantity)
        mBinding.autoCompleteTextViewCategoryPlan.setText(originalPlan.category(), false)
        mBinding.autoCompleteTextViewImportanceLevelPlan.setText(
            originalPlan.importanceLevel(),
            false
        )
    }
}