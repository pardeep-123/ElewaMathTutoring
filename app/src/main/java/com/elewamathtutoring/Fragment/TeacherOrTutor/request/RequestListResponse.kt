package com.elewamathtutoring.Fragment.TeacherOrTutor.request

import java.io.Serializable

data class RequestListResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Session request listed successfully.
    val body: List<Body>
):Serializable {
    data class Body(
        val id: Int, // 7
        val userId: Int, // 234
        val teacherId: Int, // 234
        val paidStatus: Int, // 1
        val booking_type: Int, // 0
        val payment_type: Int, // 1
        val about: String, // heyy
        val personVirtual: Int, // 1
        val availability: String, // 2,3
        val date: String, // 2021-02-01
        val time: String, // 1,2
        val hours: Int, // 2
        val perHour: Int, // 25
        val total: Int, // 50
        val adminCommision: String, // 5
        val cardId: Int, // 1
        val status: Int, // 0
        val createdAt: Int, // 1970
        val updated: Int, // 0
        val updatedAt: Int, // 2021
        val timeslot: List<Timeslot>,
        val Teacher: ReqTeacher,
        val Student: ReqStudent
    ) :Serializable{
        data class Timeslot(
            val id: Int, // 1
            val startTime: String, // 12:00 AM
            val endTime: String, // 1:00 AM
            val status: Int, // 1
            val createdAt: Int, // 1610092025
            val updatedAt: Int // 0
        ):Serializable

        data class ReqTeacher(
            val id: Int, // 234
            val name: String, // Hamza Asif
            val userType: Int, // 1
            val image: String, // http://202.164.42.227:7552/uploads/users/1637766265swift_file11_24_21_20:04:24.17.jpeg
            val address: String,
            val email: String, // hayanshyk@gmail.com
            val about: String, // Cvh
            val teachingHistory: String,
            val isCertifiedOrtutor: Int, // 0
            val isBuyPlan: Int, // 0
            val planId: Int, // 0
            val planExpiryDate: String,
            val teachingLevel: String,
            val specialties: String,
            val InPersonRate: Int, // 0
            val virtualRate: Int, // 0
            val cancellationPolicy: String,
            val availability: String,
            val available_slots: String,
            val notificationStatus: Int, // 1
            val latitude: String,
            val longitude: String,
            val deviceType: Int, // 1
            val deviceToken: String, // 123
            val SocialType: Int, // 2
            val SocialId: String, // 104176091516440845425
            val status: Int, // 0
            val hourlyPrice: Int, // 0
            val majors: String,
            val educationLevel: String,
            val isapproval: Int, // 0
            val free_slots: String, // 1,2
            val isTechingInfo: Int, // 0
            val IsAvailable: Int // 0
        ):Serializable

        data class ReqStudent(
            val id: Int, // 234
            val name: String, // Hamza Asif
            val userType: Int, // 1
            val image: String, // http://202.164.42.227:7552/uploads/users/1637766265swift_file11_24_21_20:04:24.17.jpeg
            val address: String,
            val email: String, // hayanshyk@gmail.com
            val about: String, // Cvh
            val notificationStatus: Int, // 1
            val deviceType: Int, // 1
            val deviceToken: String, // 123
            val SocialType: Int, // 2
            val SocialId: String, // 104176091516440845425
            val status: Int // 0
        ):Serializable
    }
}