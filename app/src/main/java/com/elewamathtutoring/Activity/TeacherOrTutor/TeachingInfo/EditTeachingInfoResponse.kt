package com.elewamathtutoring.Activity.TeacherOrTutor.TeachingInfo

data class EditTeachingInfoResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Edit Profile successfully.
    val body: Body
) {
    data class Body(
        val id: Int, // 234
        val name: String, // Hamza Asif
        val userType: Int, // 1
        val image: String, // http://localhost:7552/uploads/users/1637766265swift_file11_24_21_20:04:24.17.jpeg
        val address: String, // fgfg
        val email: String, // nihal@gmail.com
        val about: String, // Cvh
        val teachingHistory: String,
        val isCertifiedOrtutor: Int, // 0
        val isBuyPlan: Int, // 0
        val planId: Int, // 0
        val planExpiryDate: String,
        val teachingLevel: String, // 1,3,4
        val specialties: String, // fsdf
        val InPersonRate: Int, // 0
        val virtualRate: Int, // 0
        val cancellationPolicy: String, // sdfdsf
        val availability: String,
        val available_slots: String,
        val notificationStatus: Int, // 1
        val latitude: String, // 45.56
        val longitude: String, // 44.45
        val deviceType: Int, // 1
        val deviceToken: String, // 123
        val SocialType: Int, // 2
        val SocialId: String, // 104176091516440845425
        val status: Int, // 1
        val hourlyPrice: Int, // 0
        val majors: String,
        val educationLevel: String, // gdsdfs
        val isapproval: Int, // 2
        val free_slots: String, // 1,2
        val isTechingInfo: Int, // 0
        val IsAvailable: Int, // 0
        val teaching_level: List<TeachingLevel>
    ) {
        data class TeachingLevel(
            val id: Int, // 1
            val level: String, // Elementary School Level
            val status: Int, // 1
            val createdAt: String, // 2021-01-22T05:01:00.000Z
            val updatedAt: String // 2021-03-30T13:13:47.000Z
        )
    }
}