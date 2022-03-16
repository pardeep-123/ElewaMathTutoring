package com.elewamathtutoring.Models.Notifications


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable


@Parcelize
data class SessionDetail(
    @SerializedName("about")
    val about: String = "",
    @SerializedName("adminCommision")
    val adminCommision: String = "",
    @SerializedName("availability")
    val availability: String = "",
    @SerializedName("cardId")
    val cardId: Int = 0,
    @SerializedName("createdAt")
    val createdAt: Int = 0,
    @SerializedName("date")
    val date: String = "",
    @SerializedName("hours")
    val hours: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("perHour")
    val perHour: Int = 0,
    @SerializedName("personVirtual")
    val personVirtual: Int = 0,
    @SerializedName("status")
    val status: Int = 0,
    @SerializedName("Student")
    val student: Student = Student(),
    @SerializedName("Teacher")
    val teacher: Teacher = Teacher(),
    @SerializedName("teacherId")
    val teacherId: Int = 0,
    @SerializedName("time")
    val time: String = "",
    @SerializedName("timeslot")
    val timeslot: List<Timeslot> = listOf(),
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("updated")
    val updated: Int = 0,
    @SerializedName("updatedAt")
    val updatedAt: Int = 0,
    @SerializedName("userId")
    val userId: Int = 0
) : Parcelable