package tcc.timezen.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.DataBindingUtil
import tcc.timezen.R
import tcc.timezen.database.DBTimezen
import tcc.timezen.databinding.ActivityEditPlanBinding
import tcc.timezen.model.Pomodoro

class EditPlanActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityEditPlanBinding
    private lateinit var dbTimezen: DBTimezen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_plan)
        dbTimezen = DBTimezen(this)

        val catPlan: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView_category_plan)
        val lvlPlan: AutoCompleteTextView = findViewById(R.id.autoCompleteTextView_importanceLevel_plan)

        val adapterCat = ArrayAdapter(this, R.layout.item_dropdown, dbTimezen.getCategoryNames())
        val adapterLvl = ArrayAdapter(this, R.layout.item_dropdown, dbTimezen.getImportanceLevelNames())

        catPlan.setAdapter(adapterCat)
        lvlPlan.setAdapter(adapterLvl)

        mBinding.buttonSavePlan.setOnClickListener {
            finish()
        }
        mBinding.buttonBackPlan.setOnClickListener {
            finish()
        }
    }
}