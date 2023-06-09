package tcc.timezen.listeners

import tcc.timezen.model.Plan

interface ItemViewClickListener {
    fun onItemClickView(plan: Plan, position: Int)
    fun onEditItemClickView(plan: Plan, position: Int)
    fun onDeleteItemClickView(plan: Plan)
}