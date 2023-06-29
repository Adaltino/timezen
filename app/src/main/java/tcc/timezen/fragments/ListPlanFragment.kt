package tcc.timezen.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import tcc.timezen.R
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

    private var isShowingFilter = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentListPlanBinding.inflate(inflater, container, false)

        mBinding.fabAddNewPlan.setOnClickListener {
            val intent = Intent(activity, FormPlanActivity::class.java)
            startActivity(intent)
        }

        dbTimezen = DBTimezen(requireContext())
        val adapterCat = ArrayAdapter(requireContext(), R.layout.item_dropdown, dbTimezen.getCategoryNames())
        val adapterLvl = ArrayAdapter(requireContext(), R.layout.item_dropdown, dbTimezen.getImportanceLevelNames())

        mBinding.autoCompleteTextViewCategoryPlan.setAdapter(adapterCat)
        mBinding.autoCompleteTextViewImportanceLevelPlan.setAdapter(adapterLvl)

        mBinding.buttonFilter.setOnClickListener {
            isShowingFilter = !isShowingFilter
            if (isShowingFilter) {
                mBinding.textInputLayoutCategoryPlan.visibility = View.VISIBLE
                mBinding.textInputLayoutImportanceLevelPlan.visibility = View.VISIBLE

                mBinding.buttonFilter.text = "OK"
            } else {
                val category = mBinding.autoCompleteTextViewCategoryPlan.text.toString()
                val level = mBinding.autoCompleteTextViewImportanceLevelPlan.text.toString()

                Toast.makeText(requireContext(), "${category}, ${level}", Toast.LENGTH_SHORT).show()

                val newAdapter = PlanRecyclerViewAdapter(dbTimezen.getFilterPlans(category, level), itemViewClickListener)
                mBinding.recyclerViewListPlan.adapter = newAdapter

                mBinding.textInputLayoutCategoryPlan.visibility = View.GONE
                mBinding.textInputLayoutImportanceLevelPlan.visibility = View.GONE
                mBinding.buttonFilter.text = "Filtrar"
            }
        }

        return mBinding.root
    }

    override fun onResume() {
        super.onResume()

        dbTimezen = DBTimezen(requireContext())

        if (dbTimezen.hasPlan()) {
            mBinding.textViewTitleListPlan.visibility = View.GONE
            mBinding.recyclerViewListPlan.visibility = View.VISIBLE
            mBinding.buttonFilter.visibility = View.VISIBLE
            mBinding.ivTzLogo.visibility = View.GONE

            val adapter = PlanRecyclerViewAdapter(dbTimezen.getPlanList(), itemViewClickListener)
            mBinding.recyclerViewListPlan.adapter = adapter

            val layoutManager = LinearLayoutManager(requireContext())
            mBinding.recyclerViewListPlan.layoutManager = layoutManager

        } else {
            mBinding.recyclerViewListPlan.visibility = View.GONE
            mBinding.buttonFilter.visibility = View.GONE
            mBinding.textViewTitleListPlan.visibility = View.VISIBLE
            mBinding.ivTzLogo.visibility = View.VISIBLE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(itemViewClickListener: ItemViewClickListener) =
            ListPlanFragment(itemViewClickListener)
    }
}