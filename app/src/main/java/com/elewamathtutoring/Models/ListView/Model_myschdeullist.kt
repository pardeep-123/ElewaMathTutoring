package com.elewamathtutoring.Models.ListView
import com.google.gson.annotations.SerializedName


//data class Model_myschdeullist(
//    val body: List<Body>,
//    val code: Int,
//    val message: String,
//    val status: Boolean
//)

data class Model_myschdeullist(
    @SerializedName("body")
    val body: ArrayList<Body>,
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // Get All sessions successfully.
    @SerializedName("status")
    val status: Boolean // true
) {
    data class Body(
        @SerializedName("about")
        val about: String, // ssssssssssssss
        @SerializedName("adminCommision")
        val adminCommision: String, // 0
        @SerializedName("availability")
        val availability: String,
        @SerializedName("booking_type")
        val bookingType: Int, // 0
        @SerializedName("cardId")
        val cardId: Int, // 97
        @SerializedName("createdAt")
        val createdAt: String, // 1647349200
        @SerializedName("date")
        val date: String, // 18-03-2022
        @SerializedName("hours")
        val hours: Int, // 0
        @SerializedName("id")
        val id: Int, // 233
        @SerializedName("paidStatus")
        val paidStatus: Int, // 0
        @SerializedName("payment_type")
        val paymentType: Int, // 0
        @SerializedName("perHour")
        val perHour: Int, // 25
        @SerializedName("personVirtual")
        val personVirtual: Int, // 0
        @SerializedName("status")
        val status: Int, // 1
        @SerializedName("Student")
        val student: Student,
        @SerializedName("Teacher")
        val teacher: Teacher,
        @SerializedName("teacherId")
        val teacherId: Int, // 356
        @SerializedName("time")
        val time: String, // 97
        @SerializedName("timeslot")
        val timeslot: List<Timeslot>,
        @SerializedName("total")
        val total: Int, // 0
        @SerializedName("updated")
        val updated: String, // 1647349199
        @SerializedName("updatedAt")
        val updatedAt: String, // 1647349257
        @SerializedName("userId")
        val userId: Int // 352
    ) {
        data class Student(
            @SerializedName("about")
            val about: String,
            @SerializedName("address")
            val address: String,
            @SerializedName("deviceToken")
            val deviceToken: String,
            @SerializedName("deviceType")
            val deviceType: Int, // 0
            @SerializedName("email")
            val email: String, // k@yopmail.com
            @SerializedName("id")
            val id: Int, // 352
            @SerializedName("image")
            val image: String,
            @SerializedName("name")
            val name: String, // KUMAR
            @SerializedName("notificationStatus")
            val notificationStatus: Int, // 0
            @SerializedName("SocialId")
            val socialId: String,
            @SerializedName("SocialType")
            val socialType: Int, // 0
            @SerializedName("status")
            val status: Int, // 1
            @SerializedName("userType")
            val userType: Int // 1
        )

        data class Teacher(
            @SerializedName("about")
            val about: String, // all good
            @SerializedName("address")
            val address: String, // Mofan St, West District, Taichung City, Taiwan 403
            @SerializedName("availability")
            val availability: String, // 1,2,4
            @SerializedName("available_slots")
            val availableSlots: String, // 6,8
            @SerializedName("cancellationPolicy")
            val cancellationPolicy: String, // okk
            @SerializedName("deviceToken")
            val deviceToken: String,
            @SerializedName("deviceType")
            val deviceType: Int, // 0
            @SerializedName("educationLevel")
            val educationLevel: String, // Bachlor's degree
            @SerializedName("email")
            val email: String, // jack@yopmail.com
            @SerializedName("free_slots")
            val freeSlots: String, // 9
            @SerializedName("hourlyPrice")
            val hourlyPrice: Int, // 25
            @SerializedName("id")
            val id: Int, // 356
            @SerializedName("image")
            val image: String, // http://202.164.42.227:7552/uploads/users/1647321739images (3).jpeg
            @SerializedName("InPersonRate")
            val inPersonRate: Int, // 0
            @SerializedName("IsAvailable")
            val isAvailable: Int, // 1
            @SerializedName("isBuyPlan")
            val isBuyPlan: Int, // 0
            @SerializedName("isCertifiedOrtutor")
            val isCertifiedOrtutor: Int, // 0
            @SerializedName("isTechingInfo")
            val isTechingInfo: Int, // 1
            @SerializedName("isapproval")
            val isapproval: Int, // 2
            @SerializedName("latitude")
            val latitude: String, // 24.1486475
            @SerializedName("longitude")
            val longitude: String, // 120.6666266
            @SerializedName("majors")
            val majors: String, // too
            @SerializedName("name")
            val name: String, // Jack
            @SerializedName("notificationStatus")
            val notificationStatus: Int, // 1
            @SerializedName("occupiedStatus")
            val occupiedStatus: Int, // 1
            @SerializedName("planExpiryDate")
            val planExpiryDate: String,
            @SerializedName("planId")
            val planId: Int, // 0
            @SerializedName("SocialId")
            val socialId: String,
            @SerializedName("SocialType")
            val socialType: Int, // 0
            @SerializedName("specialties")
            val specialties: String, // ,10,7
            @SerializedName("status")
            val status: Int, // 1
            @SerializedName("teachingHistory")
            val teachingHistory: String, // no history
            @SerializedName("teachingLevel")
            val teachingLevel: String, // [2, 4]
            @SerializedName("userType")
            val userType: Int, // 2
            @SerializedName("virtualRate")
            val virtualRate: Int // 0
        )
    }
}