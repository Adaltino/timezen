package tcc.timezen.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
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
            mBinding.buttonSavePlan.text = getString(R.string.finalizar_edicao)
        }

        mBinding.buttonSavePlan.setOnClickListener {

            val name = mBinding.textEditPlanName.text.toString()
            val inputtedWorkTime = mBinding.textEditPlanWork.text.toString()
            val inputtedBreakTime = mBinding.textEditPlanBreak.text.toString()
            val category = mBinding.autoCompleteTextViewCategoryPlan.text.toString()
            val level = mBinding.autoCompleteTextViewImportanceLevelPlan.text.toString()
            val inputtedRepeatQuantity = mBinding.textEditPlanRepeat.text.toString()

            if (name.isEmpty() || inputtedWorkTime.isEmpty() || inputtedBreakTime.isEmpty() || category.isEmpty() || level.isEmpty() || inputtedRepeatQuantity.isEmpty() ) {
                Toast.makeText(this, "Por favor preencher todos os campos", Toast.LENGTH_LONG).show()
            } else {
                if (db.hasNameInPlan(name) && !isEditingPlan) {
                    Toast.makeText(this, "Esse nome já existe, tente novamente", Toast.LENGTH_LONG).show()
                } else {
                    val workTimeInMs = t.getMsFromMinute(inputtedWorkTime.toLong())
                    val breakTimeInMs = t.getMsFromMinute(inputtedBreakTime.toLong())
                    val repeatInt = inputtedRepeatQuantity.toInt()
                    val idCat = db.getCategoryById(category)
                    val idLvl = db.getImportanceLevelById(level)

                    if (isEditingPlan) {
                        val id = db.getPlanId(plan!!.name())
                        db.updatePlan(id, name, workTimeInMs.toInt(), breakTimeInMs.toInt(), repeatInt, idCat, idLvl)
                        db.updateNameInReport(name, id)
                    } else {
                        db.insertPlan(name, workTimeInMs.toInt(), breakTimeInMs.toInt(), repeatInt, idCat, idLvl)
                        //db.insertPlan("test plan", 2000, 2000, 2, 3, 3)
                    }
                    finish()
                }
            }
        }
    }

    private fun setTextViewsToExistingPlan(originalPlan: Plan) {
        val originalWorkTime =
            t.getAbsoluteHumanTime("minuteNumbersOnly", originalPlan.getWorkTime())
        val originalBreakTime =
            t.getAbsoluteHumanTime("minuteNumbersOnly", originalPlan.getBreakTime())
        val originalTaskQuantity = originalPlan.getTaskQuantity().toString()

        mBinding.toolbarFormPlan.title = getString(R.string.editar_plano)
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