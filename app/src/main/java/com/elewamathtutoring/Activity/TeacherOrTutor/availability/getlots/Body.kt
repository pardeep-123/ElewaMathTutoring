package com.elewamathtutoring.Activity.TeacherOrTutor.availability.getlots
import java.io.Serializable
data class Body(
    val id: Int, // 1
    val startTime: String, // 12:00 AM
    val endTime: String, // 1:00 AM
    val status: Int, // 1
    val createdAt: Int, // 1610092025
    val updatedAt: Int // 0

):Serializable{
    override fun toString(): String {
        return startTime
    }
}
