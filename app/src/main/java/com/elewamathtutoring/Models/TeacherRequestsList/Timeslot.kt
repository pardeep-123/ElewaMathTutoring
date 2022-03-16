package com.elewamathtutoring.Models.TeacherRequestsList


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class Timeslot(
    @SerializedName("createdAt")
    val createdAt: Int = 0,
    @SerializedName("endTime")
    val endTime: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("startTime")
    val startTime: String = "",
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("updatedAt")
    val updatedAt: Int = 0
) : Parcelable