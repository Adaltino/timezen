package tcc.timezen.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import tcc.timezen.R
import tcc.timezen.listeners.ItemViewClickListener
import tcc.timezen.model.Plan
import tcc.timezen.utils.Translator

class PlanRecyclerViewAdapter(
    private val plans: List<Plan>,
    private val itemViewClickListener: ItemViewClickListener
) : RecyclerView.Adapter<PlanRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(
        view: View,
        private val itemViewClickListener: ItemViewClickListener
    ): RecyclerView.ViewHolder(view) {
        private val t = Translator()

        fun vinculateComponents(plan: Plan, position: Int) {
            vinculateTextViews(plan)
            vinculateItemView(plan, position)
            vinculateEditButton(plan, position)
            vinculateDeleteButton(plan, position)
        }

        private fun vinculateTextViews(plan: Plan) {
            val namePlan = itemView.findViewById<TextView>(R.id.text_plan_name)
            val workPlan = itemView.findViewById<TextView>(R.id.text_plan_time)
            val catPlan = itemView.findViewById<TextView>(R.id.text_plan_category)
            val lvlPlan = itemView.findViewById<TextView>(R.id.text_plan_lvl)
            val taskPlan = itemView.findViewById<TextView>(R.id.text_plan_repeat)

            val worktime = t.getAbsoluteHumanTime("minute", plan.getWorkTime())
            val breaktime = t.getAbsoluteHumanTime("minute", plan.getBreakTime())

            namePlan.text = plan.name()
            workPlan.text = "$worktime + $breaktime"
            catPlan.text = plan.category()
            lvlPlan.text = plan.importanceLevel()
            taskPlan.text = "${plan.getTaskQuantity()} repetições"
        }

        private fun vinculateItemView(plan: Plan, position: Int) {
            itemView.findViewById<CardView>(R.id.card_plan).setOnClickListener {
                itemViewClickListener.onItemClickView(plan, position)
            }
        }

        private fun vinculateEditButton(plan: Plan, position: Int) {
            itemView.findViewById<ImageButton>(R.id.button_edit_plan).setOnClickListener {
                itemViewClickListener.onEditItemClickView(plan, position)
            }
        }

        private fun vinculateDeleteButton(plan: Plan, position: Int) {
            itemView.findViewById<ImageButton>(R.id.button_delete_plan).setOnClickListener {
                itemViewClickListener.onDeleteItemClickView(plan)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_plan, parent, false)
        return ViewHolder(view, itemViewClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plan = plans[position]
        holder.vinculateComponents(plan, position)
    }

    override fun getItemCount(): Int = plans.size
}