package com.elewamathtutoring.Fragment.TeacherOrTutor.request

data class RequestListResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Session request listed successfully.
    val body: List<Body>
) {
    data class Body(
        val id: Int, // 11
        val userId: Int, // 262
        val teacherId: Int, // 321
        val paidStatus: Int, // 0
        val booking_type: Int, // 0
        val payment_type: Int, // 0
        val about: String, // Ddddddd
        val personVirtual: Int, // 1
        val availability: String, // 7
        val date: String, // 1645698254
        val time: String, // 1
        val hours: Int, // 1
        val perHour: Int, // 4
        val total: Int, // 4
        val adminCommision: String, // 2
        val cardId: Int, // 19
        val status: Int, // 0
        val createdAt: Int, // 1970
        val updated: Int, // 0
        val updatedAt: Int, // 2021
        val timeslot: List<Timeslot>,
        val Teacher: Teacher1,
        val Student: Student1
    ) {
        data class Timeslot(
            val id: Int, // 1
            val startTime: String, // 12:00 AM
            val endTime: String, // 1:00 AM
            val status: Int, // 1
            val createdAt: Int, // 1610092025
            val updatedAt: Int // 0
        )

        data class Teacher1(
            val id: Int, // 321
            val name: String, // rg
            val userType: Int, // 2
            val image: String, // http://202.164.42.227:7552/uploads/users/164562280120220105_150447086_fef2148118f24efae02f5eb75ac3e84e.jpg
            val address: String, // sdsds
            val email: String, // nihal23@gmail.com
            val about: String, // fdgfddddddddddddddddddddddddddddddddddddd
            val teachingHistory: String, // fddfggff
            val isCertifiedOrtutor: Int, // 0
            val isBuyPlan: Int, // 0
            val planId: Int, // 0
            val planExpiryDate: String,
            val teachingLevel: String, // 1,2
            val specialties: String, // eee
            val InPersonRate: Int, // 0
            val virtualRate: Int, // 0
            val cancellationPolicy: String, // rerte
            val availability: String, // 1,2,5
            val available_slots: String, // 5,8,9
            val notificationStatus: Int, // 1
            val latitude: String, // 2123412
            val longitude: String, // 423423
            val deviceType: Int, // 0
            val deviceToken: String,
            val SocialType: Int, // 0
            val SocialId: String,
            val status: Int, // 1
            val hourlyPrice: Int, // 3
            val majors: String, // er
            val educationLevel: String, // wqw
            val isapproval: Int, // 2
            val free_slots: String,
            val isTechingInfo: Int, // 1
            val IsAvailable: Int // 1
        )

        data class Student1(
            val id: Int, // 262
            val name: String, // Hdhd
            val userType: Int, // 1
            val image: String,
            val address: String,
            val email: String, // hhwh@yopmail.com
            val about: String,
            val notificationStatus: Int, // 1
            val deviceType: Int, // 0
            val deviceToken: String,
            val SocialType: Int, // 0
            val SocialId: String,
            val status: Int // 1
        )
    }
}