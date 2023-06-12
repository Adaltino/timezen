package tcc.timezen.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import tcc.timezen.database.DBTimezen
import tcc.timezen.databinding.FragmentReportPlanBinding

class ReportPlanFragment : Fragment() {
    private lateinit var mBinding: FragmentReportPlanBinding
    private lateinit var dbTimezen: DBTimezen
    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentReportPlanBinding.inflate(inflater, container, false)
        pieChart = mBinding.pieChartView
        dbTimezen = DBTimezen(requireContext())
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.buttonConsultarReportPlan.setOnClickListener {
            mBinding.buttonConsultarReportPlan.visibility = View.GONE
            mBinding.textViewTitleReportPlan.visibility = View.GONE
            mBinding.pieChartView.visibility = View.VISIBLE
            setupPieChart()
            loadPieChartData()
        }
    }

    private fun setupPieChart() {
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = true
        pieChart.isRotationEnabled = true
        pieChart.isHighlightPerTapEnabled = true
    }

    private fun loadPieChartData() {
        val entries = ArrayList<PieEntry>()
        val dataCursor = dbTimezen.readableDatabase.query(
            "Plan",
            arrayOf("pla_name", "pla_work"),
            null,
            null,
            null,
            null,
            null
        )
        if (dataCursor.moveToFirst()) {
            do {
                val name = dataCursor.getColumnIndex("pla_name")
                val work = dataCursor.getColumnIndex("pla_work")

                val namePlan = dataCursor.getString(name)
                val workPlan = dataCursor.getInt(work)
                entries.add(PieEntry(workPlan.toFloat(), namePlan))
            } while (dataCursor.moveToNext())
        }
        dataCursor.close()

        val dataSet = PieDataSet(entries, "Plan")
        dataSet.colors = listOf(Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED)
        dataSet.valueTextSize = 12f

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.invalidate()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReportPlanFragment()
    }
}