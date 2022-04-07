package com.elewamathtutoring.Activity.ParentOrStudent.notification


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NotificationModel(
    @SerializedName("status")
    val status: Boolean, // true
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // Get Notification successfully.
    @SerializedName("body")
    val body: List<Body>
):Serializable {
    data class Body(
        @SerializedName("id")
        val id: Int, // 963
        @SerializedName("type")
        val type: Int, // 6
        @SerializedName("userType")
        val userType: Int, // 1
        @SerializedName("sessionId")
        val sessionId: Int, // 56
        @SerializedName("toUserId")
        val toUserId: Int, // 396
        @SerializedName("byUserId")
        val byUserId: Int, // 67
        @SerializedName("message")
        val message: String, // Justin Complete session booking
        @SerializedName("isRead")
        val isRead: Int, // 1
        @SerializedName("createdAt")
        val createdAt: Int, // 1616936481
        @SerializedName("updatedAt")
        val updatedAt: Int // 0
    ):Serializable
}