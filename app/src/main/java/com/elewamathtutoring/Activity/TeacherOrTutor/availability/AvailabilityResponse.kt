package com.elewamathtutoring.Activity.TeacherOrTutor.availability
import com.google.gson.annotations.SerializedName


data class AvailabilityResponse(
    @SerializedName("status")
    val status: Boolean, // true
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // updated data successfully.
    @SerializedName("body")
    val body: Body
) {
    data class Body(
        @SerializedName("id")
        val id: Int, // 392
        @SerializedName("name")
        val name: String, // B
        @SerializedName("userType")
        val userType: Int, // 2
        @SerializedName("image")
        val image: String, // http://202.164.42.227:7552/uploads/users/1648471711image1647414630018.png.jpg
        @SerializedName("address")
        val address: String, // Huacheng Rd, Xinzhuang District, New Taipei City, Taiwan 242
        @SerializedName("email")
        val email: String, // bb@yopmail.com
        @SerializedName("about")
        val about: String, // bjkk
        @SerializedName("teachingHistory")
        val teachingHistory: String, // bbjj
        @SerializedName("isCertifiedOrtutor")
        val isCertifiedOrtutor: Int, // 0
        @SerializedName("isBuyPlan")
        val isBuyPlan: Int, // 0
        @SerializedName("planId")
        val planId: Int, // 0
        @SerializedName("planExpiryDate")
        val planExpiryDate: String,
        @SerializedName("teachingLevel")
        val teachingLevel: String, // 1
        @SerializedName("specialties")
        val specialties: String, // ,7
        @SerializedName("cancellationPolicy")
        val cancellationPolicy: String, // gghjn
        @SerializedName("availability")
        val availability: String, // 1,3,6,4,2
        @SerializedName("available_slots")
        val availableSlots: String, // 9,1,2,5,6,8,14,12,15,16,17,18,19,11,13,10
        @SerializedName("notificationStatus")
        val notificationStatus: Int, // 1
        @SerializedName("latitude")
        val latitude: String, // 25.0544732
        @SerializedName("longitude")
        val longitude: String, // 121.4661641
        @SerializedName("deviceType")
        val deviceType: Int, // 1
        @SerializedName("deviceToken")
        val deviceToken: String, // f6bbhYkqTg-YOV11_B_j-f:APA91bFyfihTSlg4NoMll2Fk0btmGVWRtwxNTABKUHvVQ56lvCyKV51fCdbtr0Av_gMRd9Z2m9F6Qr3D2b6UC7yrEhlf3rSPpatp7ufGwTu3Br5_NUlYMXVAkcglVFJ011x6wI5h1e8N
        @SerializedName("SocialType")
        val socialType: Int, // 0
        @SerializedName("SocialId")
        val socialId: String,
        @SerializedName("status")
        val status: Int, // 1
        @SerializedName("hourlyPrice")
        val hourlyPrice: Int, // 25
        @SerializedName("majors")
        val majors: String, // hh
        @SerializedName("educationLevel")
        val educationLevel: String, // Bachlor's degree
        @SerializedName("isapproval")
        val isapproval: Int, // 2
        @SerializedName("free_slots")
        val freeSlots: FreeSlots,
        @SerializedName("isTechingInfo")
        val isTechingInfo: Int, // 1
        @SerializedName("IsAvailable")
        val isAvailable: Int, // 1
        @SerializedName("occupiedStatus")
        val occupiedStatus: Int, // 1
        @SerializedName("time_slots")
        val timeSlots: List<TimeSlot>
    ) {
        data class FreeSlots(
            @SerializedName("id")
            val id: Int, // 24
            @SerializedName("startTime")
            val startTime: String, // 11:00 PM
            @SerializedName("endTime")
            val endTime: String, // 12:00 AM
            @SerializedName("status")
            val status: Int, // 1
            @SerializedName("createdAt")
            val createdAt: Int, // 1610092054
            @SerializedName("updatedAt")
            val updatedAt: Int // 0
        )

        data class TimeSlot(
            @SerializedName("id")
            val id: Int, // 1
            @SerializedName("startTime")
            val startTime: String, // 12:00 AM
            @SerializedName("endTime")
            val endTime: String, // 1:00 AM
            @SerializedName("status")
            val status: Int, // 1
            @SerializedName("createdAt")
            val createdAt: Int, // 1610092025
            @SerializedName("updatedAt")
            val updatedAt: Int // 0
        )
    }
}