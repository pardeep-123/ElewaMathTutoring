package com.elewamathtutoring.Fragment.TeacherOrTutor.bookings

data class TeacherRequestListResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Session request listed successfully.
    val body: List<Body>
) {
    data class Body(
        val id: Int, // 7
        val userId: Int, // 234
        val teacherId: Int, // 234
        val paidStatus: Int, // 1
        val booking_type: Int, // 0
        val payment_type: Int, // 1
        val about: String, // heyy
        val personVirtual: Int, // 1
        val availability: String, // 2,3
        val date: String, // 2021-02-01
        val time: String, // 1,2
        val hours: Int, // 2
        val perHour: Int, // 25
        val total: Int, // 50
        val adminCommision: String, // 5
        val cardId: Int, // 1
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
            val teachingLevel: String, // 1,2
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
            val free_slots: String, // 1,2
            val isTechingInfo: Int, // 0
            val IsAvailable: Int, // 0
            val occupiedStatus: Int // 1
        )

        data class Student1(
            val id: Int, // 234
            val name: String, // rg
            val userType: Int, // 1
            val image: String, // http://202.164.42.227:7552/uploads/users/1637766265swift_file11_24_21_20:04:24.17.jpeg
            val address: String, // sdsds
            val email: String, // hayanshyk@gmail.com
            val about: String, // fdg
            val notificationStatus: Int, // 1
            val deviceType: Int, // 1
            val deviceToken: String, // 123
            val SocialType: Int, // 2
            val SocialId: String, // 104176091516440845425
            val status: Int // 0
        )
    }
}