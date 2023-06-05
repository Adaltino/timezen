package tcc.timezen.fragments

import android.content.Intent
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tcc.timezen.activities.FormPlanActivity
import tcc.timezen.dao.PlanDao
import tcc.timezen.databinding.FragmentListPlanBinding
import tcc.timezen.recyclerview.adapter.PlanRecyclerViewAdapter

class ListPlanFragment : Fragment() {
    private lateinit var mBinding: FragmentListPlanBinding

    val dao = PlanDao()
    val context: Context = TODO()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentListPlanBinding.inflate(inflater, container, false)

        mBinding.recyclerViewListPlan.adapter = PlanRecyclerViewAdapter(context, dao.listAll())

        mBinding.fabAddNewPlan.setOnClickListener {
            val intent = Intent(activity, FormPlanActivity::class.java)
            startActivity(intent)
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListPlanFragment()
    }
}