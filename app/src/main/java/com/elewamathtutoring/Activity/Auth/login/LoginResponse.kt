package com.elewamathtutoring.Activity.Auth.login

data class LoginResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // User logged in successfully.
    val body: Body
) {

    data class Body(
        val id: Int, // 361
        val name: String, // Gfg
        val userType: Int, // 2
        val image: String, // http://202.164.42.227:7552/uploads/users/164749471020210922_153633565_2d89da593cd46bde18b1dd2f919b5516.jpg
        val address: String, // Emei St, Wanhua District, Taipei City, Taiwan 108
        val email: String, // hh@yopmail.com
        val about: String, // eee
        val teachingHistory: String, // eee
        val isCertifiedOrtutor: Int, // 0
        val isBuyPlan: Int, // 0
        val planId: Int, // 0
        val planExpiryDate: String,
        val teachingLevel: String, // [1, 2]
        val specialties: String, // ,7,8
        val InPersonRate: Int, // 0
        val virtualRate: Int, // 0
        val cancellationPolicy: String, // nwebvvve
        val availability: String, // 1
        val available_slots: String, // 3
        val notificationStatus: Int, // 1
        val latitude: String, // 25.0439749
        val longitude: String, // 121.5048818
        val deviceType: Int, // 0
        val deviceToken: String,
        val SocialType: Int, // 0
        val SocialId: String,
        val status: Int, // 1
        val hourlyPrice: Int, // 25
        val majors: String, // ee
        val educationLevel: String, // Bachlor's degree
        val isapproval: Int, // 1
        val free_slots: FreeSlots,
        val isTechingInfo: Int, // 1
        val IsAvailable: Int, // 1
        val occupiedStatus: Int, // 1
        val teaching_level: List<TeachingLevel>,
        val time_slots: List<TimeSlot>,
        val token: String // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImlkIjozNjF9LCJpYXQiOjE2NDc1MTQ0MDF9.Z2eNYtdH0bvcUBonygvs7TsMecXMhmKcevF7y5-Kh8A
    ) {
        data class FreeSlots(
            val id: Int, // 5
            val startTime: String, // 4:00 AM
            val endTime: String, // 5:00 AM
            val status: Int, // 1
            val createdAt: Int, // 1611491609
            val updatedAt: Int // 0
        )

        data class TeachingLevel(
            val id: Int, // 2
            val level: String, // Middle School Level
            val status: Int, // 1
            val createdAt: String, // 2021-01-22T05:01:00.000Z
            val updatedAt: String // 2021-03-30T13:14:08.000Z
        )

        data class TimeSlot(
            val id: Int, // 3
            val startTime: String, // 2:00 AM
            val endTime: String, // 3:00 AM
            val status: Int, // 1
            val createdAt: Int, // 1611491517
            val updatedAt: Int // 0
        )
    }
}