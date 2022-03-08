package com.elewamathtutoring.Activity.TeacherOrTutor.request

import java.io.Serializable

data class RequestDetailResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Get session detail successfully.
    val body: Body
):Serializable {
    data class Body(
        val id: Int, // 13
        val userId: Int, // 264
        val teacherId: Int, // 321
        val paidStatus: Int, // 0
        val booking_type: Int, // 0
        val payment_type: Int, // 0
        val about: String, // This is info
        val personVirtual: Int, // 1
        val availability: String, // 3
        val date: String, // 2021-03-16
        val time: String, // 1,2
        val hours: Int, // 2
        val perHour: Int, // 20
        val total: Int, // 40
        val adminCommision: String, // 2
        val cardId: Int, // 21
        val status: Int, // 2
        val createdAt: Int, // 1615802021
        val updated: Int, // 0
        val updatedAt: Int, // 0
        val timeslot: ArrayList<Timeslot>,
        val Teacher: Teacher1,
        val Student: Student1
    ):Serializable {
        data class Timeslot(
            val id: Int, // 1
            val startTime: String, // 12:00 AM
            val endTime: String, // 1:00 AM
            val status: Int, // 1
            val createdAt: Int, // 1610092025
            val updatedAt: Int, // 0
            var check: Boolean = false
        ):Serializable
        data class Teacher1(
            val id: Int, // 321
            val name: String, // Shivali BVVNNNN
            val userType: Int, // 2
            val image: String, // http://202.164.42.227:7552/uploads/users/164578269520220105_150447086_fef2148118f24efae02f5eb75ac3e84e.jpg
            val address: String, // Mohali Bypass, Sector 70, Sahibzada Ajit Singh Nagar, Punjab 160071, India
            val email: String, // nihal23@gmail.com
            val about: String, // JADCBBDCJJ"VHJHJHH
            val teachingHistory: String, // JSDBHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHK
            val isCertifiedOrtutor: Int, // 0
            val isBuyPlan: Int, // 0
            val planId: Int, // 0
            val planExpiryDate: String,
            val teachingLevel: String, // 2,5
            val specialties: String, // fdfdfd
            val InPersonRate: Int, // 0
            val virtualRate: Int, // 0
            val cancellationPolicy: String, // fdfdfd
            val availability: String, // 1,2,5
            val available_slots: String, // 5,8,9
            val notificationStatus: Int, // 1
            val latitude: String, // 30.7046
            val longitude: String, // 76.7179
            val deviceType: Int, // 0
            val deviceToken: String,
            val SocialType: Int, // 0
            val SocialId: String,
            val status: Int, // 1
            val hourlyPrice: Int, // 3
            val majors: String, // er
            val educationLevel: String, // 0
            val isapproval: Int, // 2
            val free_slots: String,
            val isTechingInfo: Int, // 1
            val IsAvailable: Int // 1
        ):Serializable
        data class Student1(
            val id: Int, // 264
            val name: String, // Tutor
            val userType: Int, // 1
            val image: String, // http://202.164.42.227:7552/uploads/users/164483668820220203_115147572_72172cad46fc20e4105e81b1e333e0dd.jpg
            val address: String,
            val email: String, // tutoring@yopmail.com
            val about: String, // cyguggufuu
            val notificationStatus: Int, // 1
            val deviceType: Int, // 0
            val deviceToken: String,
            val SocialType: Int, // 0
            val SocialId: String,
            val status: Int // 1
        ):Serializable
    }
}