package tcc.timezen.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import tcc.timezen.R
import tcc.timezen.database.DBTimezen
import tcc.timezen.databinding.FragmentReportPlanBinding
import tcc.timezen.recyclerview.adapter.ReportRecyclerViewAdapter

class ReportPlanFragment : Fragment() {
    private lateinit var mBinding: FragmentReportPlanBinding
    private lateinit var dbTimezen: DBTimezen
    private lateinit var pieChart: PieChart

    private var isShowingReportList = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentReportPlanBinding.inflate(inflater, container, false)
        pieChart = mBinding.pieChartView
        dbTimezen = DBTimezen(requireContext())
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.buttonConsultarReportPlan.setOnClickListener {
            isShowingReportList = !isShowingReportList
            if (isShowingReportList) {
                mBinding.recyclerViewReportPlan.visibility = View.VISIBLE
                mBinding.pieChartView.visibility = View.GONE
                mBinding.buttonConsultarReportPlan.text = getString(R.string.relatorio_em_grafico)
            } else {
                mBinding.recyclerViewReportPlan.visibility = View.GONE
                mBinding.pieChartView.visibility = View.VISIBLE

                mBinding.buttonConsultarReportPlan.text = getString(R.string.relatorio_em_lista)
                setupPieChart()
                loadPieChartData()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val adapter = ReportRecyclerViewAdapter(dbTimezen.getReportList())
        mBinding.recyclerViewReportPlan.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext())
        mBinding.recyclerViewReportPlan.layoutManager = layoutManager
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
            "Report",
            arrayOf("rpt_pla_name", "rpt_pla_work"),
            null,
            null,
            null,
            null,
            null
        )
        if (dataCursor.moveToFirst()) {
            do {
                val name = dataCursor.getColumnIndex("rpt_pla_name")
                val work = dataCursor.getColumnIndex("rpt_pla_work")

                val namePlan = dataCursor.getString(name)
                val workPlan = dataCursor.getInt(work)
                entries.add(PieEntry(workPlan.toFloat(), namePlan))
            } while (dataCursor.moveToNext())
        }
        dataCursor.close()

        val dataSet = PieDataSet(entries, "Relatório")
        dataSet.colors = listOf(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.YELLOW, Color.MAGENTA)
        dataSet.valueTextSize = 18f

        val data = PieData(dataSet)
        pieChart.animateXY(3000, 3000)
        pieChart.data = data
        pieChart.invalidate()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReportPlanFragment()
    }
}