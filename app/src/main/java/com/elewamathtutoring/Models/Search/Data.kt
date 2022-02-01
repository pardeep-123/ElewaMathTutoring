package com.elewamathtutoring.Models.Search

data class Data(
    val InPersonRate: Int,
    val SocialId: String,
    val SocialType: Int,
    val SubscriptionPlan: SubscriptionPlan,
    val about: String,
    val availability: String,
    val available_slots: String,
    val availableslots: List<Availableslot>,
    val cancellationPolicy: String,
    val deviceToken: String,
    val deviceType: Int,
    val distance: Double,
    val email: String,
    val id: Int,
    val image: String,
    val isBuyPlan: Int,
    val isCertifiedOrtutor: Int,
    val latitude: String,
    val longitude: String,
    val name: String,
    val notificationStatus: Int,
    val planExpiryDate: String,
    val planId: Int,
    val specialties: String,
    val status: Int,
    val teachingHistory: String,
    val teachingLevel: String,
    val teaching_level: List<TeachingLevel>,
    val userType: Int,
    val virtualRate: Int
)