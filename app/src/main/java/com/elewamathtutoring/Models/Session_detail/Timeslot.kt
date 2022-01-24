package com.elewamathtutoring.Models.Session_detail

data class Timeslot(
    val createdAt: Int,
    val endTime: String,
    val id: Int,
    val startTime: String,
    val status: Int,
    val updatedAt: Int
)