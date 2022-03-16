package com.elewamathtutoring.Models.TeacherRequestsList


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable


@Parcelize
data class Model_TeacherRequestList(
    @SerializedName("body")
    val body: List<Body> = listOf(),
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Boolean = false
) : Parcelable