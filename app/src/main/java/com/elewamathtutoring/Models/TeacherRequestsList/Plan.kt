package com.elewamathtutoring.Models.TeacherRequestsList


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class Plan(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("isBuyPlan")
    val isBuyPlan: Int = 0,
    @SerializedName("planExpiryDate")
    val planExpiryDate: String = "",
    @SerializedName("planId")
    val planId: Int = 0,
    @SerializedName("SubscriptionPlan")
    val subscriptionPlan: SubscriptionPlan = SubscriptionPlan()
) : Parcelable