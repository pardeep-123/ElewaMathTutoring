package com.elewamathtutoring.Activity.TeacherOrTutor.edit

import java.io.Serializable

data class EditResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Profile get successfully.
    val body: Body
) :Serializable{
    data class Body(
        val id: Int, // 321
        val name: String, // rg
        val userType: Int, // 2
        val image: String, // http://202.164.42.227:7552/uploads/users/164562280120220105_150447086_fef2148118f24efae02f5eb75ac3e84e.jpg
        val email: String, // nihal23@gmail.com
        val about: String, // fdgfddddddddddddddddddddddddddddddddddddd
        val teachingHistory: String, // fddfggff
        val isCertifiedOrtutor: Int, // 0
        val isBuyPlan: Int, // 0
        val teachingLevel: String, // 2,5
        val educationLevel: String, // ree
        val specialties: String, // bjfbj,fbb
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
        val teaching_level: List<TeachingLevel>,
        val time_slots: List<TimeSlot>,
        val certificate_images: List<CertificateImage>
    ) :Serializable{
        data class TeachingLevel(
            val id: Int, // 2
            val level: String, // Middle School Level
            val status: Int, // 1
            val createdAt: String, // 2021-01-22T05:01:00.000Z
            val updatedAt: String // 2021-03-30T13:14:08.000Z
        ):Serializable

        data class TimeSlot(
            val id: Int, // 5
            val startTime: String, // 4:00 AM
            val endTime: String, // 5:00 AM
            val status: Int, // 1
            val createdAt: Int, // 1611491609
            val updatedAt: Int // 0
        ):Serializable

        data class CertificateImage(
            val id: Int, // 76
            val user_id: Int, // 321
            val images: String, // 1645770982arrow.png
            val createdAt: String, // 2022-02-25T06:36:22.000Z
            val updatedAt: String // 2022-02-25T06:36:22.000Z
        ):Serializable
    }
}