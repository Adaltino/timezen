package tcc.timezen.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import tcc.timezen.activities.FormPlanActivity
import tcc.timezen.database.DBTimezen
import tcc.timezen.databinding.FragmentListPlanBinding
import tcc.timezen.listeners.ItemViewClickListener
import tcc.timezen.recyclerview.adapter.PlanRecyclerViewAdapter

class ListPlanFragment(
    private val itemViewClickListener: ItemViewClickListener
) : Fragment() {
    private lateinit var mBinding: FragmentListPlanBinding
    private lateinit var dbTimezen: DBTimezen

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

        dbTimezen = DBTimezen(requireContext())

        if (dbTimezen.hasPlan()) {
            mBinding.textViewTitleListPlan.visibility = View.GONE
            mBinding.recyclerViewListPlan.visibility = View.VISIBLE

            val adapter = PlanRecyclerViewAdapter(dbTimezen.getPlanList(), itemViewClickListener)
            mBinding.recyclerViewListPlan.adapter = adapter

            val layoutManager = LinearLayoutManager(requireContext())
            mBinding.recyclerViewListPlan.layoutManager = layoutManager
        } else {
            mBinding.recyclerViewListPlan.visibility = View.GONE
            mBinding.textViewTitleListPlan.visibility = View.VISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(itemViewClickListener: ItemViewClickListener) =
            ListPlanFragment(itemViewClickListener)
    }
}