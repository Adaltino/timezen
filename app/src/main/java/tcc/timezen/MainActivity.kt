package tcc.timezen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import tcc.timezen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    private lateinit var mListPlanFragment: ListPlanFragment
    private lateinit var mReportPlanFragment: ReportPlanFragment
    private lateinit var mFormPlanFragment: FormPlanFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        replaceFragment(ListPlanFragment.newInstance())

        mBinding.bottomNavigation.setOnItemSelectedListener(::onSelectedBottomNavigationItem)

        mBinding.fabAddNewPlan.setOnClickListener {
            mBinding.bottomNavigation.visibility = View.GONE
            replaceFragment(FormPlanFragment.newInstance())
        }
    }

    private fun onSelectedBottomNavigationItem(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.item_home -> replaceFragment(ListPlanFragment.newInstance())
            R.id.item_report -> replaceFragment(ReportPlanFragment.newInstance())
        }
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE)
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}