package com.elewamathtutoring.Models.Notifications


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable


@Parcelize
data class Teacher(
    @SerializedName("about")
    val about: String = "",
    @SerializedName("address")
    val address: String = "",
    @SerializedName("availability")
    val availability: String = "",
    @SerializedName("available_slots")
    val availableSlots: String = "",
    @SerializedName("cancellationPolicy")
    val cancellationPolicy: String = "",
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
    @SerializedName("InPersonRate")
    val inPersonRate: Int = 0,
    @SerializedName("isBuyPlan")
    val isBuyPlan: Int = 0,
    @SerializedName("isCertifiedOrtutor")
    val isCertifiedOrtutor: Int = 0,
    @SerializedName("latitude")
    val latitude: String = "",
    @SerializedName("longitude")
    val longitude: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("notificationStatus")
    val notificationStatus: Int = 0,
    @SerializedName("planExpiryDate")
    val planExpiryDate: String = "",
    @SerializedName("planId")
    val planId: Int = 0,
    @SerializedName("SocialId")
    val socialId: String = "",
    @SerializedName("SocialType")
    val socialType: Int = 0,
    @SerializedName("specialties")
    val specialties: String = "",
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("teachingHistory")
    val teachingHistory: String = "",
    @SerializedName("teachingLevel")
    val teachingLevel: String = "",
    @SerializedName("userType")
    val userType: Int = 0,
    @SerializedName("virtualRate")
    val virtualRate: Int = 0
) : Parcelable