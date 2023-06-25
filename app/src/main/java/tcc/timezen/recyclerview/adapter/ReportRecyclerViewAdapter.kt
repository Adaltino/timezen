package tcc.timezen.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tcc.timezen.R
import tcc.timezen.listeners.ItemViewClickListener
import tcc.timezen.model.Plan
import tcc.timezen.model.Report
import tcc.timezen.utils.Translator

class ReportRecyclerViewAdapter(
    private val report: List<Report>
) : RecyclerView.Adapter<ReportRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReportRecyclerViewAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_report, parent, false)
        return ReportRecyclerViewAdapter.ViewHolder(view)
    }

    class ViewHolder(
        view: View,
    ): RecyclerView.ViewHolder(view) {
        val t = Translator()

        fun vinculate(report: Report) {
            val namePlan = itemView.findViewById<TextView>(R.id.text_rpt_plan_name)
            val workPlan = itemView.findViewById<TextView>(R.id.text_rpt_plan_time)

            val time = t.makeTimeStringForReport(report.work())

            namePlan.text = report.name()
            workPlan.text = "Tempo de Atividade: $time"
        }
    }


    override fun onBindViewHolder(holder: ReportRecyclerViewAdapter.ViewHolder, position: Int) {
        val reports = report[position]
        holder.vinculate(reports)
    }

    override fun getItemCount(): Int = report.size
}