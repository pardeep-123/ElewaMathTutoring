package com.elewamathtutoring.Models.My_Schedule

import java.io.Serializable

data class Model_schedule(
    val body: Body,
    val code: Int,
    val message: String,
    val status: Boolean
): Serializable