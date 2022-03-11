package com.elewamathtutoring.Activity.TeacherOrTutor.availability

import java.io.Serializable

data class TimeSlotsResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Get Time slots successfully.
    val body: List<Body>
):Serializable {
    data class Body(
        val id: Int, // 1
        val startTime: String, // 12:00 AM
        val endTime: String, // 1:00 AM
        val status: Int, // 1
        val createdAt: Int, // 1610092025
        val updatedAt: Int, // 0
        var check: Boolean = false
    ):Serializable {
        override fun toString(): String {
            return startTime + "-" + endTime
        }
    }
}