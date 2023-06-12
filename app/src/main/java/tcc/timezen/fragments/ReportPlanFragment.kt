package tcc.timezen.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tcc.timezen.database.DBTimezen
import tcc.timezen.databinding.FragmentReportPlanBinding

class ReportPlanFragment : Fragment() {
    private lateinit var mBinding: FragmentReportPlanBinding
    private lateinit var dbTimezen: DBTimezen

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentReportPlanBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.buttonConsultarReportPlan.setOnClickListener {
            mBinding.buttonConsultarReportPlan.visibility = View.GONE
            mBinding.textViewTitleReportPlan.visibility = View.GONE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReportPlanFragment()
    }
}