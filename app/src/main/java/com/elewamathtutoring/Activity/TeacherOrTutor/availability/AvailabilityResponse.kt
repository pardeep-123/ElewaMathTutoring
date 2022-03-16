package com.elewamathtutoring.Activity.TeacherOrTutor.availability

data class AvailabilityResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // updated data successfully.
    val body: Body
) {
    data class Body(
        val id: Int, // 316
        val name: String, // rg
        val userType: Int, // 2
        val image: String, // http://202.164.42.227:7552/uploads/users/1645521975AndroidTest (1).pdf
        val address: String, // dds
        val email: String, // nihal22@gmail.com
        val about: String, // fdg
        val teachingHistory: String, // fddfggff
        val isCertifiedOrtutor: Int, // 0
        val isBuyPlan: Int, // 0
        val planId: Int, // 0
        val planExpiryDate: String,
        val teachingLevel: String, // 2,5
        val specialties: String, // bjfbj,fbb
        val InPersonRate: Int, // 0
        val virtualRate: Int, // 0
        val cancellationPolicy: String, // fdfdfd
        val availability: String, // 1,2,5
        val available_slots: String, // 5,8,9
        val notificationStatus: Int, // 1
        val latitude: String, // 1000038
        val longitude: String, // 384735
        val deviceType: Int, // 1
        val deviceToken: String, // abcdefghijklmnopqrstuvwxyz
        val SocialType: Int, // 0
        val SocialId: String,
        val status: Int, // 1
        val hourlyPrice: Int, // 3
        val majors: String, // er
        val educationLevel: String, // ree
        val isapproval: Int, // 2
        val free_slots: String,
        val isTechingInfo: Int, // 1
        val IsAvailable: Int, // 1
        val time_slots: List<TimeSlot>
    ) {
        data class TimeSlot(
            val id: Int, // 5
            val startTime: String, // 4:00 AM
            val endTime: String, // 5:00 AM
            val status: Int, // 1
            val createdAt: Int, // 1611491609
            val updatedAt: Int // 0
        )
    }
}