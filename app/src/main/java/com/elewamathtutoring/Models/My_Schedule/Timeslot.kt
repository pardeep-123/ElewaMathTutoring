package com.elewamathtutoring.Models.My_Schedule

import java.io.Serializable

data class Timeslot(
    val createdAt: Int,
    val endTime: String,
    val id: Int,
    val startTime: String,
    val status: Int,
    val updatedAt: Int
): Serializable