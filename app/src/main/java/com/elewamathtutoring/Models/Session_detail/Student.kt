package com.elewamathtutoring.Models.Session_detail

data class Student(
    val SocialId: String,
    val SocialType: Int,
    val about: String,
    val address: String,
    val deviceToken: String,
    val deviceType: Int,
    val email: String,
    val id: Int,
    val image: String,
    val name: String,
    val notificationStatus: Int,
    val status: Int,
    val userType: Int
)