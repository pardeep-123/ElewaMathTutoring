package com.elewamathtutoring.Models.Login

import java.io.Serializable

data class Model_login(
    var body: Body,
    val code: Int,
    val message: String,
    val status: Boolean
):Serializable