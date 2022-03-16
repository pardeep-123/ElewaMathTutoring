package com.elewamathtutoring.Models.Teacher_details

import java.io.Serializable

data class TimeSlot(
    val createdAt: Int,
    val endTime: String,
    val id: Int,
    val startTime: String,
    val status: Int,
    val updatedAt: Int,
    var check: Boolean = false
): Serializable