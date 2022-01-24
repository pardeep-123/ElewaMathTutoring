package com.elewamathtutoring.Models.Teacher_details

import java.io.Serializable

data class TeachingLevel(
    val createdAt: String,
    val id: Int,
    val level: String,
    val status: Int,
    val updatedAt: String
): Serializable