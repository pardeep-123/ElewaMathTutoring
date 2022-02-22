package com.elewamathtutoring.Activity.TeacherOrTutor.edit

import java.io.Serializable

data class EditResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Profile get successfully.
    val body: Body
):Serializable {
    data class Body(
        val id: Int, // 316
        val name: String, // rg
        val userType: Int, // 2
        val image: String, // http://202.164.42.227:7552/uploads/users/1645521975AndroidTest (1).pdf
        val email: String, // nihal22@gmail.com
        val about: String, // fdg
        val teachingHistory: String, // fddfggff
        val isCertifiedOrtutor: Int, // 0
        val isBuyPlan: Int, // 0
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
        val teaching_level: List<TeachingLevel>,
        val time_slots: List<TimeSlot>,
        val certificate_images: List<CertificateImage>
    ):Serializable {
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
            val id: Int, // 56
            val user_id: Int, // 316
            val images: String, // 1645525299button (1).png
            val createdAt: String, // 2022-02-22T10:21:38.000Z
            val updatedAt: String // 2022-02-22T10:21:38.000Z
        ):Serializable
    }
}