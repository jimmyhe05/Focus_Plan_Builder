package com.jimmyh.focus_plan_builder

data class FocusPlan(
    val subject: String,
    val minutes: Int,
    val category: String,
    val breakMinutes: Int,
) {
    val summary: String
        get() = "Study $subject for $minutes minutes, and then take a $breakMinutes minutes break."
}

fun durationCategory(minutes: Int): String = when {
    minutes < 10 -> "Invalid"
    minutes < 30 -> "Quick Review"
    minutes < 60 -> "Focused Session"
    else -> "Extended Session"
}

fun recommendedBreak(minutes: Int): Int = when {
    minutes < 30 -> 5
    minutes < 60 -> 10
    else -> 15
}