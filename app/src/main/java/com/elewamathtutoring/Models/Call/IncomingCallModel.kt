package com.elewamathtutoring.Models.Call


import com.google.gson.annotations.SerializedName

data class IncomingCallModel(
    @SerializedName("channelName")
    val channelName: String, // ncxjf06VuK8VA7hV52LV
    @SerializedName("friendId")
    val friendId: Int, // 230
    @SerializedName("friendImage")
    val friendImage: String, // 1637306192swift_file11_19_21_12:46:30.89.jpeg
    @SerializedName("friendName")
    val friendName: String, // deep
    @SerializedName("message")
    val message: String, //  Incoming voice call 
    @SerializedName("push_type")
    val pushType: Int, // 17
    @SerializedName("title")
    val title: String, // Tayyab
    @SerializedName("token")
    val token: String, // 00694d43cc0cd50466190e2cb017b6453ddIADtjSQVvauXK6cMQ8XK1tLuANehoeJQCr6ku4ClbUyPKkSdOuQAAAAAEADfj2mOCtpfYgEAAQAAAAAA
    @SerializedName("userId")
    val userId: Int, // 233
    @SerializedName("userImage")
    val userImage: String, // 1637763798swift_file11_24_21_19:23:15.23.jpeg
    @SerializedName("userName")
    val userName: String // Tayyab
)