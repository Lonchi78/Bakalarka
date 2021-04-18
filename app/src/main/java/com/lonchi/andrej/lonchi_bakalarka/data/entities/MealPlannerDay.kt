package com.lonchi.andrej.lonchi_bakalarka.data.entities

data class MealPlannerDay(
    val day: Int,
    val month: Int,
    val year: Int,
    val isToday: Boolean = false,
    val isSelected: Boolean = false
) {
    fun isSameDay(otherDay: MealPlannerDay): Boolean {
        return this.day == otherDay.day &&
                this.month == otherDay.month &&
                this.year == otherDay.year
    }
}