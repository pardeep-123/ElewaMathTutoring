package com.elewamathtutoring.Models.My_Schedule

import java.io.Serializable

data class Plan(
    val SubscriptionPlan: SubscriptionPlan,
    val id: Int,
    val isBuyPlan: Int,
    val planExpiryDate: String,
    val planId: Int
): Serializable