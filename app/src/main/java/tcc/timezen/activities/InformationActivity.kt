package tcc.timezen.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import tcc.timezen.R
import tcc.timezen.databinding.ActivityInformationBinding

class InformationActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_information)

        mBinding.buttonBackInformation.setOnClickListener {
            finish()
        }
    }
}