package com.elewamathtutoring.Models.Login

import java.io.Serializable

data class TimeSlot(
    val createdAt: Int,
    val endTime: String,
    val id: Int,
    val startTime: String,
    val status: Int,
    val updatedAt: Int
):Serializable