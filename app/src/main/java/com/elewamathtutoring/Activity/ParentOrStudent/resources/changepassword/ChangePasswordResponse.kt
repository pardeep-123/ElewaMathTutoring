package com.elewamathtutoring.Activity.ParentOrStudent.resources.changepassword

data class ChangePasswordResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Password updated successfully.
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
        val free_slots: String
    )
}