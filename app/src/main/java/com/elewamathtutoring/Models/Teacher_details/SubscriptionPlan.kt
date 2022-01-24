package com.elewamathtutoring.Models.Teacher_details

import java.io.Serializable

data class SubscriptionPlan(
    val createdAt: Int,
    val description: String,
    val id: Int,
    val name: String,
    val price: String,
    val updatedAt: Int
): Serializable