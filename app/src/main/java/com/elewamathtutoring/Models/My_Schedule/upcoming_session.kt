package com.elewamathtutoring.Models.My_Schedule

import java.io.Serializable

data class upcoming_session(
    val Student: Student,
    val Teacher: teacher,
    val about: String,
    val adminCommision: String,
    val availability: String,
    val cardId: Int,
    val createdAt: Int,
    val date: String,
    val hours: Int,
    val id: Int,
    val perHour: Int,
    val personVirtual: Int,
    val plan: Plan,
    val status: Int,
    val teacherId: Int,
    val time: String,
    val timeslot: List<Timeslot>,
    val total: Int,
    val updated: Int,
    val updatedAt: Int,
    val userId: Int
): Serializable