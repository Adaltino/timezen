package faculdade.timezen.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import faculdade.timezen.R
import faculdade.timezen.planModel.Plan
import faculdade.timezen.planModel.PomodoroTimer
import faculdade.timezen.utils.Translator

class ListPlansAdapter(
    private val context: Context,
    private val plans: List<Plan>
) : RecyclerView.Adapter<ListPlansAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val translator = Translator()

        fun vincula(plan: Plan) {
            val namePlan = itemView.findViewById<TextView>(R.id.text_name_plan_list)
            namePlan.text = plan.name()
            val timePlan = itemView.findViewById<TextView>(R.id.text_time_plan_list)
            timePlan.text = "${translator.getHumanTime("minute", plan.getWorkTime())} + ${translator.getHumanTime("minute", plan.getBreakTime())}"
            val repeatPlan = itemView.findViewById<TextView>(R.id.text_time_repeat_list)
            repeatPlan.text = "${plan.getTaskQuantity()} repetições"
        }
    }

    /**
     * Responsavel por pegar cada uma das view e fazer o processo de binding
     * ele que vai segurar a view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.list_item_plans, parent, false)
        return ViewHolder(view)
    }

    /**
     * Indicar qual item que a gente está
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plan = plans[position]
        holder.vincula(plan)
    }

    /**
     * Ele vai determinar para o adapter quantos itens a gente quer apresentar
     */
    override fun getItemCount(): Int = plans.size

}
