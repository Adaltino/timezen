package tcc.timezen.dao

import tcc.timezen.model.Plan

class PlanDao {

    fun addPlan(plan: Plan) {
        Companion.Plan.add(plan)
    }

    fun listAll(plan: Plan) {
        Companion.Plan.toList()
    }

    companion object {
        private val Plan = mutableListOf<Plan>()
    }
}