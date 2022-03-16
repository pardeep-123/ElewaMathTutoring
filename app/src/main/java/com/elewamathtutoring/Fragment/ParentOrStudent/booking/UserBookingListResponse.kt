package com.elewamathtutoring.Fragment.ParentOrStudent.booking

data class UserBookingListResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Session listed successfully.
    val body: Body
) {
    data class Body(
        val Pending_sessions: List<Any>,
        val Today_sessions: List<TodaySession>,
        val Upcoming_sessions: List<UpcomingSession>
    ) {
        data class TodaySession(
            val id: Int, // 19
            val userId: Int, // 323
            val teacherId: Int, // 260
            val about: String, // Okkk
            val personVirtual: Int, // 1
            val availability: String, // 5
            val date: String, // 202202281136
            val time: String, // 9
            val perHour: Int, // 20
            val total: Int, // 20
            val cardId: Int, // 22
            val status: Int, // 1
            val createdAt: Int, // 1615896266
            val updated: Int, // 0
            val updatedAt: Int, // 0
            val paidStatus: Int, // 0
            val booking_type: Int, // 0
            val payment_type: Int, // 0
            val hours: Int, // 1
            val adminCommision: String, // 2
            val timeslot: List<Timeslot>,
            val Teacher: Teacher1,
            val Student: Student1
        ) {
            data class Timeslot(
                val id: Int, // 9
                val startTime: String, // 8:00 AM
                val endTime: String, // 9:00 AM
                val status: Int, // 1
                val createdAt: Int, // 1611491609
                val updatedAt: Int // 0
            )

            data class Teacher1(
                val id: Int, // 260
                val name: String, // nihal
                val userType: Int, // 2
                val image: String, // http://202.164.42.227:7552/uploads/users/1644819904button (1).png
                val address: String, // Mohali
                val email: String, // nihalrana@gmail.com
                val about: String, // anout
                val teachingHistory: String, // i have 5 year experiance
                val isCertifiedOrtutor: Int, // 0
                val isBuyPlan: Int, // 0
                val planId: Int, // 0
                val planExpiryDate: String,
                val teachingLevel: String, // 1,2
                val specialties: String, // math,science
                val InPersonRate: Int, // 0
                val virtualRate: Int, // 0
                val cancellationPolicy: String, // cancelPolicy
                val availability: String, // 3,5
                val available_slots: String, // 3,4,5,6,7
                val notificationStatus: Int, // 1
                val latitude: String, // 31.2386136
                val longitude: String, // 76.4899622
                val deviceType: Int, // 1
                val deviceToken: String, // abcdefghijklmnopqrstuvwxyz
                val SocialType: Int, // 0
                val SocialId: String,
                val status: Int, // 1
                val hourlyPrice: Int, // 21
                val majors: String, // majors
                val educationLevel: String, // education level
                val isapproval: Int, // 2
                val free_slots: String,
                val isTechingInfo: Int, // 0
                val IsAvailable: Int // 0
            )

            data class Student1(
                val id: Int, // 323
                val name: String, // sff
                val userType: Int, // 1
                val image: String, // http://202.164.42.227:7552/uploads/users/164602872020220105_145451668_fe17da7b131455d4b17eef4d5a3a15a0.jpg
                val address: String,
                val email: String, // user0@yopmail.com
                val about: String, // dfuuytttttttttttttttttttttttttttt
                val notificationStatus: Int, // 0
                val deviceType: Int, // 0
                val deviceToken: String,
                val SocialType: Int, // 0
                val SocialId: String,
                val status: Int // 1
            )
        }

        data class UpcomingSession(
            val id: Int, // 15
            val userId: Int, // 323
            val teacherId: Int, // 321
            val paidStatus: Int, // 0
            val booking_type: Int, // 0
            val payment_type: Int, // 0
            val about: String, // Okkk
            val personVirtual: Int, // 1
            val availability: String, // 5
            val date: String, // 1646799254
            val time: String, // 1
            val hours: Int, // 1
            val perHour: Int, // 20
            val total: Int, // 20
            val adminCommision: String, // 2
            val cardId: Int, // 22
            val status: Int, // 1
            val createdAt: Int, // 1615896266
            val updated: Int, // 0
            val updatedAt: Int, // 0
            val timeslot: List<Timeslot>,
            val Teacher: TeacherUpcoming,
            val Student: StudentUpcoming
        ) {
            data class Timeslot(
                val id: Int, // 1
                val startTime: String, // 12:00 AM
                val endTime: String, // 1:00 AM
                val status: Int, // 1
                val createdAt: Int, // 1610092025
                val updatedAt: Int // 0
            )

            data class TeacherUpcoming(
                val id: Int, // 321
                val name: String, // Shivali BVVNNNN
                val userType: Int, // 2
                val image: String, // http://202.164.42.227:7552/uploads/users/164578269520220105_150447086_fef2148118f24efae02f5eb75ac3e84e.jpg
                val address: String, // Mohali Bypass, Sector 70, Sahibzada Ajit Singh Nagar, Punjab 160071, India
                val email: String, // nihal23@gmail.com
                val about: String, // JADCBBDCJJ"VHJHJHH
                val teachingHistory: String, // JSDBHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHK
                val isCertifiedOrtutor: Int, // 0
                val isBuyPlan: Int, // 0
                val planId: Int, // 0
                val planExpiryDate: String,
                val teachingLevel: String, // 2,5
                val specialties: String, // fdfdfd
                val InPersonRate: Int, // 0
                val virtualRate: Int, // 0
                val cancellationPolicy: String, // fdfdfd
                val availability: String, // 1,2,5
                val available_slots: String, // 5,8,9
                val notificationStatus: Int, // 1
                val latitude: String, // 30.7046
                val longitude: String, // 76.7179
                val deviceType: Int, // 0
                val deviceToken: String,
                val SocialType: Int, // 0
                val SocialId: String,
                val status: Int, // 1
                val hourlyPrice: Int, // 3
                val majors: String, // er
                val educationLevel: String, // 0
                val isapproval: Int, // 2
                val free_slots: String,
                val isTechingInfo: Int, // 1
                val IsAvailable: Int // 1
            )

            data class StudentUpcoming(
                val id: Int, // 323
                val name: String, // sff
                val userType: Int, // 1
                val image: String, // http://202.164.42.227:7552/uploads/users/164602872020220105_145451668_fe17da7b131455d4b17eef4d5a3a15a0.jpg
                val address: String,
                val email: String, // user0@yopmail.com
                val about: String, // dfuuytttttttttttttttttttttttttttt
                val notificationStatus: Int, // 0
                val deviceType: Int, // 0
                val deviceToken: String,
                val SocialType: Int, // 0
                val SocialId: String,
                val status: Int // 1
            )
        }
    }
}