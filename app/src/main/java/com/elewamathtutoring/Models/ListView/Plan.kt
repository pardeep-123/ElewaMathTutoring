package com.elewamathtutoring.Models.ListView

data class Plan(
    val SubscriptionPlan: SubscriptionPlan,
    val id: Int,
    val isBuyPlan: Int,
    val planExpiryDate: String,
    val planId: Int
)