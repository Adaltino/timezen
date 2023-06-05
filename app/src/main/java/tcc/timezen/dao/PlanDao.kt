package tcc.timezen.dao

import tcc.timezen.model.Plan

class PlanDao {

    fun addPlan(plan: Plan) {
        Plan.add(plan)
    }

    fun listAll() : List<Plan> {
        return Plan.toList()
    }

    companion object {
        private val Plan = mutableListOf<Plan>()
    }
}