package com.elewamathtutoring.Models.Card_listing

data class Body(
    val card_number: String,
    val createdAt: String,
    val cvv: Int,
    val expiry_month: String,
    val expiry_year: String,
    val holder_name: String,
    val id: Int,
    val isSave: Int,
    val updatedAt: String,
    val user_id: Int
)