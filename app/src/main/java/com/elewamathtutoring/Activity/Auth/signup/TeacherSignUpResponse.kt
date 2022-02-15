package com.elewamathtutoring.Activity.Auth.signup

data class TeacherSignUpResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Signup successfully.
    val body: Body
) {
    data class Body(
        val id: Int, // 268
        val name: String, // nihal
        val userType: Int, // 2
        val image: String, // http://202.164.42.227:7552/uploads/users/1644905620AndroidTest (1).pdf
        val address: String,
        val email: String, // nihalrana11@gmail.com
        val about: String, // anout
        val teachingHistory: String, // i have 5 year experiance
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
        val deviceToken: String, // abcdefghijklmnopqrstuvwxyz
        val SocialType: Int, // 0
        val SocialId: String,
        val status: Int, // 1
        val hourlyPrice: Int, // 0
        val majors: String,
        val educationLevel: String,
        val isapproval: Int, // 2
        val free_slots: String,
        val isTechingInfo: Int, // 0
        val IsAvailable: Int, // 0
        val token: String // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImlkIjoyNjh9LCJpYXQiOjE2NDQ5MDU2MTl9.d3XcPiUG9eUDPJMXawrE85iaSSh3CIwFI9Q3lo1BkC8
    )
}