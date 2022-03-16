package com.elewamathtutoring.Models.Teacher_details

import java.io.Serializable

data class Model_teacherdetails(
    val body: Body,
    val code: Int,
    val message: String,
    val status: Boolean
): Serializable