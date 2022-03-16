package com.elewamathtutoring.Models.Login

import java.io.Serializable

data class Body(
    val InPersonRate: Int,
    val SocialId: String,
    val SocialType: Int,
    val about: String,
    val address: String,
    val availability: String,
    val available_slots: String,
    val cancellationPolicy: String,
    val deviceToken: String,
    val deviceType: String,
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
    val time_slots: List<TimeSlot>,
    val token: String,
    val userType: Int,
    val virtualRate: Int
): Serializable