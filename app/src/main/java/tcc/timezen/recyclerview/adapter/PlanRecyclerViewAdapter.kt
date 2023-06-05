package tcc.timezen.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tcc.timezen.R
import tcc.timezen.model.Plan
import tcc.timezen.utils.Translator

class PlanRecyclerViewAdapter(
    private val plans: List<Plan>
) : RecyclerView.Adapter<PlanRecyclerViewAdapter.ViewHolder>() {
    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val t = Translator()

        fun vinculate(plan: Plan) {
            val namePlan = itemView.findViewById<TextView>(R.id.text_plan_name)
            val workPlan = itemView.findViewById<TextView>(R.id.text_plan_time)
//            val breakPlan = itemView.findViewById<TextView>(R.id.text_plan)
            val catPlan = itemView.findViewById<TextView>(R.id.text_plan_category)
            val lvlPlan = itemView.findViewById<TextView>(R.id.text_plan_lvl)
            val taskPlan = itemView.findViewById<TextView>(R.id.text_plan_repeat)

            namePlan.text = plan.name()
            workPlan.text = "${t.getAbsoluteHumanTime("minute", plan.getWorkTime())} + ${t.getAbsoluteHumanTime("minute", plan.getBreakTime())}"
            catPlan.text = "Trabalho"
            lvlPlan.text = "Médio"
            taskPlan.text = "${plan.getTaskQuantity()} repetições"
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_plan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plan = plans[position]
        holder.vinculate(plan)
    }

    override fun getItemCount(): Int = plans.size
}