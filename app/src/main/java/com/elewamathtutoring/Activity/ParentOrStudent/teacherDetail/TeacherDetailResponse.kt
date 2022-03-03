package com.elewamathtutoring.Activity.ParentOrStudent.teacherDetail

import java.io.Serializable

data class TeacherDetailResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // GEt teacher detail successfully
    val body: Body
) :Serializable{
    data class Body(
        val id: Int, // 233
        val name: String, // Tayyab
        val userType: Int, // 2
        val image: String, // http://202.164.42.227:7552/uploads/users/1637763798swift_file11_24_21_19:23:15.23.jpeg
        val email: String, // aolakh4g@gmail.com
        val about: String, // I am teacher
        val teachingHistory: String, // 10 years
        val isCertifiedOrtutor: Int, // 2
        val isBuyPlan: Int, // 0
        val planId: Int, // 0
        val planExpiryDate: String,
        val teachingLevel: String, // 3
        val specialties: String, // Math
        val InPersonRate: Int, // 2
        val virtualRate: Int, // 25
        val cancellationPolicy: String, // Optional
        val availability: String, // 1,2,4
        val notificationStatus: Int, // 1
        val latitude: String, // 40.7317696
        val longitude: String, // -73.9841161
        val deviceType: Int, // 1
        val deviceToken: String, // 123
        val SocialType: Int, // 2
        val SocialId: String, // 114454975690526584905
        val available_slots: String, // 1,2,4
        val status: Int, // 1
        val teaching_level: List<TeachingLevel>
    ):Serializable {
        data class TeachingLevel(
            val id: Int, // 3
            val level: String, // High School Level
            val status: Int, // 1
            val createdAt: String, // 2021-01-22T05:01:00.000Z
            val updatedAt: String // 2021-03-30T13:14:13.000Z
        ):Serializable
    }
}