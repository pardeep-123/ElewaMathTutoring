package com.elewamathtutoring.Activity.Chat.mathChat

import java.io.Serializable

data class MathChatResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // get List successfully
    val body: List<Body>
):Serializable {
    data class Body(
        val id: Int, // 233
        val userType: Int, // 2
        val authorization: String,
        val image: String, // 1637763798swift_file11_24_21_19:23:15.23.jpeg
        val name: String, // Tayyab
        val audioTime: String,
        val videoTime: String,
        val email: String, // aolakh4g@gmail.com
        val address: String, // Nowhere
        val latitude: String, // 40.7317696
        val longitude: String, // -73.9841161
        val password: String,
        val createdAt: Int, // 1637763798
        val updatedAt: Int, // 0
        val status: Int, // 1
        val isapproval: Int, // 1
        val isTechingInfo: Int, // 0
        val IsAvailable: Int, // 0
        val hourlyPrice: Int, // 0
        val notificationStatus: Int, // 1
        val background_checks: Int, // 0
        val deviceType: Int, // 1
        val deviceToken: String,
        val occupiedStatus: Int, // 1// 123
        val SocialType: Int, // 2
        val SocialId: String, // 114454975690526584905
        val about: String, // I am teacher
        val teachingHistory: String, // 10 years
        val isBuyPlan: Int, // 0
        val planId: Int, // 0
        val planDuration: String,
        val planExpiryDate: String,
        val location: String,
        val isCertifiedOrtutor: Int, // 2
        val teachingLevel: String, // 3
        val specialties: String, // Math
        val InPersonRate: Int, // 2
        val virtualRate: Int, // 25
        val cancellationPolicy: String, // Optional
        val availability: String, // 1,2,4
        val available_slots: String, // 1,2,4
        val free_slots: String, // 4
        val forgotPasswordHash: String,
        val isbankAdd: Int, // 0
        val walletAmount: String,
        val stripe_customID: String,
        val stripe_url: String,
        val isStripe_connected: Int, // 0
        val stripe_account_id: String,
        val educationLevel: String, // post graduate
        val majors: String // majorsss
    ):Serializable
}