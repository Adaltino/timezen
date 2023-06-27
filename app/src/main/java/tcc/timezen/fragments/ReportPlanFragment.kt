package tcc.timezen.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
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

        val daltonismTypes = arrayOf("Normal", "Protanopia", "Deuteranopia", "Tritanopia")
        val adapterColor = ArrayAdapter(requireContext(), R.layout.item_dropdown, daltonismTypes)
        mBinding.autoCompleteTextViewColor.setAdapter(adapterColor)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.buttonConsultarReportPlan.setOnClickListener {
            isShowingReportList = !isShowingReportList
            if (isShowingReportList) {
                mBinding.recyclerViewReportPlan.visibility = View.VISIBLE
                mBinding.pieChartView.visibility = View.GONE
                mBinding.textInputColor.visibility = View.GONE
                mBinding.buttonConsultarReportPlan.text = getString(R.string.relatorio_em_grafico)
            } else {
                mBinding.recyclerViewReportPlan.visibility = View.GONE
                mBinding.pieChartView.visibility = View.VISIBLE
                mBinding.textInputColor.visibility = View.VISIBLE

                mBinding.buttonConsultarReportPlan.text = getString(R.string.relatorio_em_lista)
                setupPieChart()
                loadPieChartData()
            }
        }

        mBinding.autoCompleteTextViewColor.setOnItemClickListener { parent, _, position, _ ->
            val selectedType = parent.getItemAtPosition(position) as String
            changeChartColorsByType(pieChart, selectedType)
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
        pieChart.legend.isEnabled = false
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
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.asList()
        dataSet.valueFormatter = PercentFormatter(pieChart)
        dataSet.valueTextSize = 18f

        val data = PieData(dataSet)
        pieChart.animateXY(3000, 3000)
        pieChart.data = data
        pieChart.invalidate()
    }

    fun changeChartColorsByType(chart: PieChart, daltonismType: String) {
        val colors = when (daltonismType) {
            "Normal" -> ColorTemplate.COLORFUL_COLORS.asList()
            "Protanopia" -> ColorTemplate.MATERIAL_COLORS.asList()
            "Deuteranopia" -> ColorTemplate.JOYFUL_COLORS.asList()
            "Tritanopia" -> ColorTemplate.LIBERTY_COLORS.asList()
            else -> ColorTemplate.COLORFUL_COLORS.asList()
        }

        val dataSet = chart.data.dataSets[0] as PieDataSet
        dataSet.colors = colors
        chart.invalidate()
    }

    fun resetChartColor(chart: PieChart) {
        val dataSet = chart.data.dataSets[0] as PieDataSet
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.asList()
        chart.invalidate()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReportPlanFragment()
    }
}