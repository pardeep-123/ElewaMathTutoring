package com.elewamathtutoring.Fragment.ParentOrStudent.profile

import java.io.Serializable

data class ProfileResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Profile get successfully.
    val body: Body
):Serializable {
    data class Body(
        val id: Int, // 241
        val name: String, // aaa
        val userType: Int, // 1
        val image: String,
        val email: String, // a@a.a
        val about: String,
        val teachingHistory: String,
        val isCertifiedOrtutor: Int, // 0
        val isBuyPlan: Int, // 0
        val teachingLevel: String,
        val specialties: String,
        val InPersonRate: Int, // 0
        val virtualRate: Int, // 0
        val cancellationPolicy: String,
        val availability: String,
        val available_slots: String,
        val notificationStatus: Int, // 0
        val latitude: String,
        val longitude: String,
        val deviceType: Int, // 0
        val deviceToken: String,
        val SocialType: Int, // 0
        val SocialId: String,
        val status: Int // 1
    ):Serializable
}