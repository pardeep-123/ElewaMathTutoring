package com.elewamathtutoring.Models.Session_detail

data class Body(
    val Card: Card,
    val Student: Student,
    val Teacher: Teacher,
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
    val status: Int,
    val teacherId: Int,
    val time: String,
    val timeslot: List<Timeslot>,
    val total: Int,
    val updated: Int,
    val updatedAt: Int,
    val userId: Int
)