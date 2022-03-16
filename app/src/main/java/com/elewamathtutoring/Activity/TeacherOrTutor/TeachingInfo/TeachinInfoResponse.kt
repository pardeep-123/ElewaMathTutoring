package com.elewamathtutoring.Activity.TeacherOrTutor.TeachingInfo

data class TeachinInfoResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // updated data successfully.
    val body: Body
) {
    data class Body(
        val id: Int, // 269
        val name: String, // nihal
        val userType: Int, // 2
        val image: String, // http://202.164.42.227:7552/uploads/users/1644917681AndroidTest (1).pdf
        val address: String, // dds
        val email: String, // nihalrana111@gmail.com
        val about: String, // anout
        val teachingHistory: String, // i have 5 year experiance
        val isCertifiedOrtutor: Int, // 0
        val isBuyPlan: Int, // 0
        val planId: Int, // 0
        val planExpiryDate: String,
        val teachingLevel: String, // ret
        val specialties: String, // bjfbj,fbb
        val InPersonRate: Int, // 0
        val virtualRate: Int, // 0
        val cancellationPolicy: String, // fdfdfd
        val availability: String,
        val available_slots: String,
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
        val IsAvailable: Int, // 0
        val teaching_level: List<Any>,
        val certificate_images: List<CertificateImage>
    ) {
        data class CertificateImage(
            val id: Int, // 18
            val user_id: Int, // 269
            val images: String, // 1644919940button (1).png
            val createdAt: String, // 2022-02-15T10:12:19.000Z
            val updatedAt: String // 2022-02-15T10:12:19.000Z
        )
    }
}