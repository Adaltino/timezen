package tcc.timezen.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import tcc.timezen.fragments.ListPlanFragment
import tcc.timezen.R
import tcc.timezen.fragments.ReportPlanFragment
import tcc.timezen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        replaceFragment(ListPlanFragment.newInstance())

        mBinding.bottomNavigation.setOnItemSelectedListener(::onSelectedBottomNavigationItem)
        mBinding.toolbar.setOnMenuItemClickListener(::onSelectedToolBarItem)
    }

    private fun onSelectedToolBarItem(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.item_info -> startActivity(Intent(this, InformationActivity::class.java))
        }
        return true
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