package com.elewamathtutoring.Activity.TeacherOrTutor.editProfile

data class EditTeacherProfileResponse(
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
        val address: String,
        val email: String, // hayanshyk@gmail.com
        val about: String, // fdg
        val teachingHistory: String, // fddfggff
        val isCertifiedOrtutor: Int, // 0
        val isBuyPlan: Int, // 0
        val planId: Int, // 0
        val planExpiryDate: String,
        val teachingLevel: String,
        val specialties: String,
        val InPersonRate: Int, // 0
        val virtualRate: Int, // 0
        val cancellationPolicy: String,
        val availability: String,
        val available_slots: String,
        val notificationStatus: Int, // 1
        val latitude: String,
        val longitude: String,
        val deviceType: Int, // 1
        val deviceToken: String, // 123
        val SocialType: Int, // 2
        val SocialId: String, // 104176091516440845425
        val status: Int, // 0
        val hourlyPrice: Int, // 0
        val majors: String,
        val educationLevel: String,
        val isapproval: Int, // 0
        val free_slots: String, // 1,2
        val isTechingInfo: Int, // 0
        val IsAvailable: Int, // 0
        val teaching_level: List<Any>,
        val time_slots: List<Any>
    )
}