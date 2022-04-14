package com.elewamathtutoring.Activity.Chat


import com.google.gson.annotations.SerializedName

data class UploadImageResponse(
    @SerializedName("status")
    val status: Boolean, // true
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // get image name
    @SerializedName("body")
    val body: Body
) {
    data class Body(
        @SerializedName("image")
        val image: String // K718JF7F85C2078DB9513154935J.png
    )
}