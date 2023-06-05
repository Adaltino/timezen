package tcc.timezen.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import tcc.timezen.fragments.ListPlanFragment
import tcc.timezen.R
import tcc.timezen.fragments.ReportPlanFragment
import tcc.timezen.databinding.ActivityMainBinding
import tcc.timezen.listeners.ItemViewClickListener
import tcc.timezen.model.Plan

class MainActivity : AppCompatActivity(), ItemViewClickListener {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        replaceFragment(ListPlanFragment.newInstance(this))

        mBinding.bottomNavigation.setOnItemSelectedListener(::onSelectedBottomNavigationItem)
        mBinding.toolbar.setOnMenuItemClickListener(::onSelectedToolBarItem)
    }

    override fun onItemClickView(plan: Plan, position: Int) {
        val intent = Intent(this, SelectedPlanActivity::class.java)
        intent.putExtra("id", position)

        startActivity(intent)
    }

    override fun onEditItemClickView(plan: Plan) {
        Toast.makeText(this, "${plan.name()}, (edit)", Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteItemClickView(plan: Plan) {
        Toast.makeText(this, "${plan.name()}, (delete)", Toast.LENGTH_SHORT).show()
    }

    private fun onSelectedToolBarItem(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.item_info -> startActivity(Intent(this, InformationActivity::class.java))
        }
        return true
    }

    private fun onSelectedBottomNavigationItem(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.item_home -> replaceFragment(ListPlanFragment.newInstance(this))
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