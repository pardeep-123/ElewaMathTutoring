package com.elewamathtutoring.Activity.TeacherOrTutor.editProfile
import com.google.gson.annotations.SerializedName


data class EditTeacherProfileResponse(
    @SerializedName("status")
    val status: Boolean, // true
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // Edit Profile successfully.
    @SerializedName("body")
    val body: Body
) {
    data class Body(
        @SerializedName("id")
        val id: Int, // 379
        @SerializedName("name")
        val name: String, // Pardeep
        @SerializedName("userType")
        val userType: Int, // 2
        @SerializedName("image")
        val image: String, // http://202.164.42.227:7552/uploads/users/1649414397image1649066526648.png.jpg
        @SerializedName("address")
        val address: String, // Minquan W Rd, Datong District, Taipei City, Taiwan
        @SerializedName("email")
        val email: String, // prdp@mail.com
        @SerializedName("about")
        val about: String, // we provide many types services in this pandemic situation
        @SerializedName("teachingHistory")
        val teachingHistory: String, // i have no history   in case she was in the wrong position and phonepe was not a good one test
        @SerializedName("isCertifiedOrtutor")
        val isCertifiedOrtutor: Int, // 0
        @SerializedName("isBuyPlan")
        val isBuyPlan: Int, // 0
        @SerializedName("planId")
        val planId: Int, // 0
        @SerializedName("planExpiryDate")
        val planExpiryDate: String,
        @SerializedName("teachingLevel")
        val teachingLevel: String, // 2,3
        @SerializedName("specialties")
        val specialties: String, // ,8,10
        @SerializedName("cancellationPolicy")
        val cancellationPolicy: String, // no policy
        @SerializedName("availability")
        val availability: String, // 3,1,2
        @SerializedName("available_slots")
        val availableSlots: String, // 7,8,9,10
        @SerializedName("notificationStatus")
        val notificationStatus: Int, // 1
        @SerializedName("latitude")
        val latitude: String, // 25.0630589
        @SerializedName("longitude")
        val longitude: String, // 121.5159765
        @SerializedName("deviceType")
        val deviceType: Int, // 1
        @SerializedName("deviceToken")
        val deviceToken: String, // dns8XFuzRSuE0WJzumOOxS:APA91bFS1GNwZOobxKAPmmPlnL_1avG5w1QVAaE9eqA0cFhKjeXGnGj3I2k0R8E05y8o1UkR-3Cb3071SG5gXuRKZRHwTFMCxfprOYG0oUTO7wDaJ7skTB9sNdrE1EDOR1_AyvXPvoZg
        @SerializedName("SocialType")
        val socialType: Int, // 0
        @SerializedName("SocialId")
        val socialId: String,
        @SerializedName("status")
        val status: Int, // 1
        @SerializedName("hourlyPrice")
        val hourlyPrice: Int, // 20
        @SerializedName("majors")
        val majors: String, // none
        @SerializedName("educationLevel")
        val educationLevel: String, // High school diploma
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
        @SerializedName("teaching_level")
        val teaching_Level: List<TeachingLevel>,
        @SerializedName("time_slots")
        val timeSlots: List<TimeSlot>
    ) {
        data class FreeSlots(
            @SerializedName("id")
            val id: Int, // 22
            @SerializedName("startTime")
            val startTime: String, // 9:00 PM
            @SerializedName("endTime")
            val endTime: String, // 10:00 PM
            @SerializedName("status")
            val status: Int, // 1
            @SerializedName("createdAt")
            val createdAt: Int, // 1610092054
            @SerializedName("updatedAt")
            val updatedAt: Int // 0
        )

        data class TeachingLevel(
            @SerializedName("id")
            val id: Int, // 2
            @SerializedName("level")
            val level: String, // Middle School Level
            @SerializedName("status")
            val status: Int, // 1
            @SerializedName("createdAt")
            val createdAt: String, // 2021-01-22T05:01:00.000Z
            @SerializedName("updatedAt")
            val updatedAt: String // 2021-03-30T13:14:08.000Z
        )

        data class TimeSlot(
            @SerializedName("id")
            val id: Int, // 7
            @SerializedName("startTime")
            val startTime: String, // 6:00 AM
            @SerializedName("endTime")
            val endTime: String, // 7:00 AM
            @SerializedName("status")
            val status: Int, // 1
            @SerializedName("createdAt")
            val createdAt: Int, // 1610092054
            @SerializedName("updatedAt")
            val updatedAt: Int // 0
        )
    }
}