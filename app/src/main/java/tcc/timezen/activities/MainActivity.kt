package tcc.timezen.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
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
import tcc.timezen.database.DBTimezen
import tcc.timezen.fragments.ReportPlanFragment
import tcc.timezen.databinding.ActivityMainBinding
import tcc.timezen.listeners.ItemViewClickListener
import tcc.timezen.model.Plan

class MainActivity : AppCompatActivity(), ItemViewClickListener {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var dbTimezen: DBTimezen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dbTimezen = DBTimezen(this)

        replaceFragment(ListPlanFragment.newInstance(this))
        createNotificationChannel()

        mBinding.bottomNavigation.setOnItemSelectedListener(::onSelectedBottomNavigationItem)
        mBinding.toolbar.setOnMenuItemClickListener(::onSelectedToolBarItem)
    }

    override fun onItemClickView(plan: Plan, position: Int) {
        val intent = Intent(this, SelectedPlanActivity::class.java)
        intent.putExtra("id", position)

        startActivity(intent)
    }

    override fun onEditItemClickView(plan: Plan, position: Int) {
        val intent = Intent(this, FormPlanActivity::class.java)
        intent.putExtra("id", position)

        startActivity(intent)
    }

    override fun onDeleteItemClickView(plan: Plan) {
        val id = dbTimezen.getPlanId(plan.name())
        if (dbTimezen.deletePlanById(id)) {
            Toast.makeText(this, "Plano ${plan.name()} Deletado", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show()
        }
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

    private fun createNotificationChannel() {
        val name: CharSequence = "TimeZen"
        val descriptionText = "Put something nice in here"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("TimeZen", name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Register the channel with the system
        notificationManager.createNotificationChannel(channel)
    }
}