package com.elewamathtutoring.Activity.Auth.login

data class LoginResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // User logged in successfully.
    val body: Body
) {
    data class Body(
        val id: Int, // 241
        val name: String, // aaa
        val userType: Int, // 1
        val image: String,
        val address: String,
        val email: String, // a@a.a
        val about: String,
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
        val deviceType: Int, // 0
        val deviceToken: String,
        val SocialType: Int, // 0
        val SocialId: String,
        val status: Int, // 1
        val hourlyPrice: Int, // 0
        val majors: String,
        val educationLevel: String,
        val isapproval: Int, // 1
        val free_slots: String,
        val teaching_level: List<Any>,
        val time_slots: List<Any>,
        val token: String // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImlkIjoyNDF9LCJpYXQiOjE2NDQ0NzE5OTJ9.tMmmUt_CcXc2Z6LXRT1RTRJYNpbehjjUUOg8G3Tx-w0
    )
}