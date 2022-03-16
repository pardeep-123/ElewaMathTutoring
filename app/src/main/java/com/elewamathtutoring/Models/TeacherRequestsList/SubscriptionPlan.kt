package com.elewamathtutoring.Models.TeacherRequestsList


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class SubscriptionPlan(
    @SerializedName("createdAt")
    val createdAt: Int = 0,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("updatedAt")
    val updatedAt: Int = 0
) : Parcelable