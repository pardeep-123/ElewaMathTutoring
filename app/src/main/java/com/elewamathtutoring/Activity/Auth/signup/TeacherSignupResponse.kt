package com.elewamathtutoring.Activity.Auth.signup

import java.io.Serializable

data class TeacherSignupResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Signup successfully.
    val body: Body
):Serializable {
    data class Body(
        val id: Int, // 260
        val name: String, // nihal
        val userType: Int, // 2
        val image: String, // http://202.164.42.227:7552/uploads/users/1644819904button (1).png
        val address: String, // Mohali
        val email: String, // nihalrana@gmail.com
        val about: String, // anout
        val teachingHistory: String, // i have 5 year experiance
        val isCertifiedOrtutor: Int, // 0
        val isBuyPlan: Int, // 0
        val planId: Int, // 0
        val planExpiryDate: String,
        val teachingLevel: String, // 1,2
        val specialties: String, // math,science
        val InPersonRate: Int, // 0
        val virtualRate: Int, // 0
        val cancellationPolicy: String, // cancelPolicy
        val availability: String, // 3,5
        val available_slots: String, // 3,4,5,6,7
        val notificationStatus: Int, // 1
        val latitude: String, // 31.2386136
        val longitude: String, // 76.4899622
        val deviceType: Int, // 1
        val deviceToken: String, // abcdefghijklmnopqrstuvwxyz
        val SocialType: Int, // 0
        val SocialId: String,
        val status: Int, // 1
        val hourlyPrice: Int, // 21
        val majors: String, // majors
        val educationLevel: String, // education level
        val isapproval: Int, // 2
        val free_slots: String,
        val time_slots: List<TimeSlot>,
        val teaching_level: List<TeachingLevel>,
        val token: String, // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImlkIjoyNjB9LCJpYXQiOjE2NDQ4MTk5MDR9.YIs2xOAu7vvV_AKnglQypICCB2-wM3fAKA03tZswqGs
        val certificate_images: List<CertificateImage>
    ) :Serializable {
        data class TimeSlot(
            val id: Int, // 3
            val startTime: String, // 2:00 AM
            val endTime: String, // 3:00 AM
            val status: Int, // 1
            val createdAt: Int, // 1611491517
            val updatedAt: Int // 0
        ):Serializable

        data class TeachingLevel(
            val id: Int, // 1
            val level: String, // Elementary School Level
            val status: Int, // 1
            val createdAt: String, // 2021-01-22T05:01:00.000Z
            val updatedAt: String // 2021-03-30T13:13:47.000Z
        ):Serializable

        data class CertificateImage(
            val id: Int, // 14
            val user_id: Int, // 260
            val images: String, // 1644819904arrow.png
            val createdAt: String, // 2022-02-14T06:25:04.000Z
            val updatedAt: String // 2022-02-14T06:25:04.000Z
        ):Serializable
    }
}