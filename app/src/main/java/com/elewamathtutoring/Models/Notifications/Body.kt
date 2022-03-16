package com.elewamathtutoring.Models.Notifications


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class Body(
    @SerializedName("byUserId")
    val byUserId: Int = 0,
    @SerializedName("createdAt")
    val createdAt: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("isRead")
    val isRead: Int = 0,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("sessionDetail")
    val sessionDetail: List<SessionDetail> = listOf(),
    @SerializedName("sessionId")
    val sessionId: Int = 0,
    @SerializedName("toUserId")
    val toUserId: Int = 0,
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("updatedAt")
    val updatedAt: Int = 0,
    @SerializedName("userType")
    val userType: Int = 0
) : Parcelable