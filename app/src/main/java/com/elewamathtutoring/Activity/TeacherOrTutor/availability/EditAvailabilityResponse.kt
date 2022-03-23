package com.elewamathtutoring.Activity.TeacherOrTutor.availability

import com.elewamathtutoring.Activity.TeacherOrTutor.edit.EditResponse
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EditAvailabilityResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Edit Profile successfully.
    val body: Body
) {
    data class Body(
        val id: Int, // 234
        val name: String, // rg
        val userType: Int, // 1
        val image: String, // http://202.164.42.227:7552/uploads/users/1637766265swift_file11_24_21_20:04:24.17.jpeg
        val address: String, // sdsds
        val email: String, // hayanshyk@gmail.com
        val about: String, // fdg
        val teachingHistory: String, // fddfggff
        val isCertifiedOrtutor: Int, // 0
        val isBuyPlan: Int, // 0
        val planId: Int, // 0
        val planExpiryDate: String,
        val teachingLevel: String, // ww
        val specialties: String, // eee
        val InPersonRate: Int, // 0
        val virtualRate: Int, // 0
        val cancellationPolicy: String, // rerte
        val availability: String, // 2,4,7
        val available_slots: String, // 1,4,6
        val notificationStatus: Int, // 1
        val latitude: String, // 2123412
        val longitude: String, // 423423
        val deviceType: Int, // 1
        val deviceToken: String, // 123
        val SocialType: Int, // 2
        val SocialId: String, // 104176091516440845425
        val status: Int, // 0
        val hourlyPrice: Int, // 0
        val majors: String,
        val educationLevel: String, // wqw
        val isapproval: Int, // 0
        val free_slots: FreeSlots?, // 1,2
        val isTechingInfo: Int, // 0
        val IsAvailable: Int, // 0
        val time_slots: List<TimeSlot>
    ) {
        data class TimeSlot(
            val id: Int, // 1
            val startTime: String, // 12:00 AM
            val endTime: String, // 1:00 AM
            val status: Int, // 1
            val createdAt: Int, // 1610092025
            val updatedAt: Int // 0
        )
    }
    data class FreeSlots(
        @SerializedName("createdAt")
        val createdAt: Int, // 1610092054
        @SerializedName("endTime")
        val endTime: String, // 11:00 PM
        @SerializedName("id")
        val id: Int, // 23
        @SerializedName("startTime")
        val startTime: String, // 10:00 PM
        @SerializedName("status")
        val status: Int, // 1
        @SerializedName("updatedAt")
        val updatedAt: Int // 0
    ) : Serializable
}