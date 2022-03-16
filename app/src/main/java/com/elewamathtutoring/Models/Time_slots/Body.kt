package com.elewamathtutoring.Models.Time_slots

data class Body(
    val createdAt: Int,
    val endTime: String,
    val id: Int,
    val startTime: String,
    val status: Int,
    val updatedAt: Int,
    var check: Boolean = false
)