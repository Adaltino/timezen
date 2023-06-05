package tcc.timezen.fragments

import android.content.Intent
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import tcc.timezen.activities.FormPlanActivity
import tcc.timezen.dao.PlanDao
import tcc.timezen.databinding.FragmentListPlanBinding
import tcc.timezen.listeners.ItemViewClickListener
import tcc.timezen.recyclerview.adapter.PlanRecyclerViewAdapter

class ListPlanFragment(
    private val itemViewClickListener: ItemViewClickListener
) : Fragment() {
    private lateinit var mBinding: FragmentListPlanBinding

    private val dao = PlanDao()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentListPlanBinding.inflate(inflater, container, false)

        mBinding.fabAddNewPlan.setOnClickListener {
            val intent = Intent(activity, FormPlanActivity::class.java)
            startActivity(intent)
        }

        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        val adapter = PlanRecyclerViewAdapter(dao.listAll(), itemViewClickListener)
        mBinding.recyclerViewListPlan.adapter = adapter

        val layoutManager = LinearLayoutManager(requireContext())
        mBinding.recyclerViewListPlan.layoutManager = layoutManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(itemViewClickListener: ItemViewClickListener) = ListPlanFragment(itemViewClickListener)
    }
}