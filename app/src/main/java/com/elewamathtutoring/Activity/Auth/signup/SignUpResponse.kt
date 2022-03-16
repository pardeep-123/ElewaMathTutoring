package com.elewamathtutoring.Activity.Auth.signup


import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("status")
    val status: Boolean, // true
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // Signup successfully.
    @SerializedName("body")
    val body: Body
) {
    data class Body(
        @SerializedName("id")
        val id: Int, // 241
        @SerializedName("name")
        val name: String, // aaa
        @SerializedName("userType")
        val userType: Int, // 1
        @SerializedName("image")
        val image: String,
        @SerializedName("address")
        val address: String,
        @SerializedName("email")
        val email: String, // a@a.a
        @SerializedName("about")
        val about: String,
        @SerializedName("notificationStatus")
        val notificationStatus: Int, // 1
        @SerializedName("deviceType")
        val deviceType: Int, // 0
        @SerializedName("deviceToken")
        val deviceToken: String,
        @SerializedName("SocialType")
        val socialType: Int, // 0
        @SerializedName("SocialId")
        val socialId: String,
        @SerializedName("status")
        val status: Int, // 1
        @SerializedName("token")
        val token: String // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImlkIjoyNDF9LCJpYXQiOjE2NDQ0NzE5MjR9.AVjfR6qgLIZR57BRwaCKX6wRq0rIkF9lRZatYxc3UsY
    )
}