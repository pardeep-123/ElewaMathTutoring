package com.elewamathtutoring.Models.TeacherRequestsList


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class Student(
    @SerializedName("about")
    val about: String = "",
    @SerializedName("address")
    val address: String = "",
    @SerializedName("deviceToken")
    val deviceToken: String = "",
    @SerializedName("deviceType")
    val deviceType: Int = 0,
    @SerializedName("email")
    val email: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("notificationStatus")
    val notificationStatus: Int = 0,
    @SerializedName("SocialId")
    val socialId: String = "",
    @SerializedName("SocialType")
    val socialType: Int = 0,
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("userType")
    val userType: Int = 0
) : Parcelable