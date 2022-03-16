package com.pipt.restApi

import com.google.gson.annotations.SerializedName

class RestError {
    @SerializedName("success")
    var success: Boolean = true

//     @SerializedName("code")
//    var code: Int = true

    @SerializedName("message")
    var message: String? = null
    // val body: Body,
    //    val code: Int,
    //    val message: String,
    //    val success: Boolean
}