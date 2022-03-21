package com.elewamathtutoring.Models.Card_listing

data class AllSessionListResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Get All sessions successfully.
    val body: ArrayList<Body>
) {
    data class Body(
        val id: Int, // 239
        val userId: Int, // 352
        val teacherId: Int, // 356
        val paidStatus: Int, // 0
        val booking_type: Int, // 0
        val payment_type: Int, // 0
        val about: String, // hello
        val personVirtual: Int, // 0
        val availability: String,
        val date: String, // 1647561600
        val time: String, // 97
        val hours: Int, // 0
        val perHour: Int, // 25
        val total: Int, // 0
        val adminCommision: String, // 0
        val cardId: Int, // 97
        val status: Int, // 1
        val createdAt: Int, // 1647426402
        val updated: Int, // 1647426401
        val updatedAt: Int, // 1647426486
        val timeslot: List<Any>,
        val Teacher: TeacherX,
        val Student: StudentX
    ) {
        data class TeacherX(
            val id: Int, // 356
            val name: String, // Jack
            val userType: Int, // 2
            val image: String, // http://202.164.42.227:7552/uploads/users/1647321739images (3).jpeg
            val address: String, // Mofan St, West District, Taichung City, Taiwan 403
            val email: String, // jack@yopmail.com
            val about: String, // all good
            val teachingHistory: String, // no history
            val isCertifiedOrtutor: Int, // 0
            val isBuyPlan: Int, // 0
            val planId: Int, // 0
            val planExpiryDate: String,
            val teachingLevel: String, // [2, 4]
            val specialties: String, // ,10,7
            val InPersonRate: Int, // 0
            val virtualRate: Int, // 0
            val cancellationPolicy: String, // okk
            val availability: String, // 1,2,4
            val available_slots: String, // 6,8
            val notificationStatus: Int, // 1
            val latitude: String, // 24.1486475
            val longitude: String, // 120.6666266
            val deviceType: Int, // 0
            val deviceToken: String,
            val SocialType: Int, // 0
            val SocialId: String,
            val status: Int, // 1
            val hourlyPrice: Int, // 25
            val majors: String, // too
            val educationLevel: String, // Bachlor's degree
            val isapproval: Int, // 2
            val free_slots: String, // 9
            val isTechingInfo: Int, // 1
            val IsAvailable: Int, // 1
            val occupiedStatus: Int // 1
        )

        data class StudentX(
            val id: Int, // 352
            val name: String, // KUMAR
            val userType: Int, // 1
            val image: String,
            val address: String,
            val email: String, // k@yopmail.com
            val about: String,
            val notificationStatus: Int, // 0
            val deviceType: Int, // 0
            val deviceToken: String,
            val SocialType: Int, // 0
            val SocialId: String,
            val status: Int // 1
        )
    }
}