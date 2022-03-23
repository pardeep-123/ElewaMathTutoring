package com.elewamathtutoring.Activity.TeacherOrTutor.edit

import java.io.Serializable
import com.google.gson.annotations.SerializedName


//data class EditResponse(
//    val status: Boolean, // true
//    val code: Int, // 200
//    val message: String, // Profile get successfully.
//    val body: Body
//) :Serializable{
//    data class Body(
//        val id: Int, // 321
//        val name: String, // rg
//        val userType: Int, // 2
//        val image: String, // http://202.164.42.227:7552/uploads/users/164562280120220105_150447086_fef2148118f24efae02f5eb75ac3e84e.jpg
//        val email: String, // nihal23@gmail.com
//        val about: String, // fdgfddddddddddddddddddddddddddddddddddddd
//        val teachingHistory: String, // fddfggff
//        val isCertifiedOrtutor: Int, // 0
//        val isBuyPlan: Int, // 0
//        val teachingLevel: String, // 2,5
//        val educationLevel: String, // ree
//        val majors: String, // aaa
//        val hourlyPrice: Int, // 12
//        val specialties: String, // bjfbj,fbb
//        val InPersonRate: Int, // 0
//        val virtualRate: Int, // 0
//        val cancellationPolicy: String, // fdfdfd
//        val availability: String, // 1,2,5
//        val available_slots: String, // 5,8,9
//        val notificationStatus: Int, // 1
//        val latitude: String, // 30.7046
//        val longitude: String, // 76.7179
//        val deviceType: Int, // 0
//        val deviceToken: String,
//        val SocialType: Int, // 0
//        val SocialId: String,
//        val free_slots: String,
//        val status: Int, // 1
//        val subjects: ArrayList<Subject>,
//        val teaching_level: List<TeachingLevel>,
//        val time_slots: List<TimeSlot>,
//        val certificate_images: List<CertificateImage>
//    ) :Serializable{
//
//        data class Subject(
//            val id: Int, // 9
//            val name: String, // punjabi
//            val status: Int // 1
//        ) :Serializable
//
//        data class TeachingLevel(
//            val id: Int, // 2
//            val level: String, // Middle School Level
//            val status: Int, // 1
//            val createdAt: String, // 2021-01-22T05:01:00.000Z
//            val updatedAt: String // 2021-03-30T13:14:08.000Z
//        ):Serializable
//
//        data class TimeSlot(
//            val id: Int, // 5
//            val startTime: String, // 4:00 AM
//            val endTime: String, // 5:00 AM
//            val status: Int, // 1
//            val createdAt: Int, // 1611491609
//            val updatedAt: Int // 0
//        ):Serializable
//
//        data class CertificateImage(
//            val id: Int, // 76
//            val user_id: Int, // 321
//            val images: String, // 1645770982arrow.png
//            val createdAt: String, // 2022-02-25T06:36:22.000Z
//            val updatedAt: String // 2022-02-25T06:36:22.000Z
//        ):Serializable
//    }
//}

data class EditResponse(
    @SerializedName("body")
    val body: Body,
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // Profile get successfully.
    @SerializedName("status")
    val status: Boolean // true
) : Serializable {
    data class Body(
        @SerializedName("about")
        val about: String, // we are provide services
        @SerializedName("availability")
        val availability: String, // 3,1,2
        @SerializedName("available_slots")
        val availableSlots: String, // 7,8,9,10
        @SerializedName("cancellationPolicy")
        val cancellationPolicy: String, // no policy
        @SerializedName("certificate_images")
        val certificateImages: ArrayList<CertificateImage>,
        @SerializedName("deviceToken")
        val deviceToken: String,
        @SerializedName("deviceType")
        val deviceType: Int, // 0
        @SerializedName("educationLevel")
        val educationLevel: String, // High school diploma
        @SerializedName("email")
        val email: String, // prdp@mail.com
        @SerializedName("free_slots")
        val freeSlots: FreeSlots?,
        @SerializedName("hourlyPrice")
        val hourlyPrice: Int, // 20
        @SerializedName("id")
        val id: Int, // 379
        @SerializedName("image")
        val image: String, // http://202.164.42.227:7552/uploads/users/1648015180images (2).jpeg
        @SerializedName("InPersonRate")
        val inPersonRate: Int, // 0
        @SerializedName("isBuyPlan")
        val isBuyPlan: Int, // 0
        @SerializedName("isCertifiedOrtutor")
        val isCertifiedOrtutor: Int, // 0
        @SerializedName("latitude")
        val latitude: String, // 25.0630589
        @SerializedName("longitude")
        val longitude: String, // 121.5159765
        @SerializedName("majors")
        val majors: String, // none
        @SerializedName("name")
        val name: String, // Pardeep
        @SerializedName("notificationStatus")
        val notificationStatus: Int, // 1
        @SerializedName("occupiedStatus")
        val occupiedStatus: Int, // 1
        @SerializedName("SocialId")
        val socialId: String,
        @SerializedName("SocialType")
        val socialType: Int, // 0
        @SerializedName("specialties")
        val specialties: String, // ,8,10
        @SerializedName("status")
        val status: Int, // 1
        @SerializedName("subjects")
        val subjects: List<Subject>,
        @SerializedName("teachingHistory")
        val teachingHistory: String, // no history
        @SerializedName("teachingLevel")
        val teachingLevelString: String, // 2,3
        @SerializedName("teaching_level")
        val teachingLevel: ArrayList<TeachingLevel>,
        @SerializedName("time_slots")
        val timeSlots: ArrayList<TimeSlot>,
        @SerializedName("userType")
        val userType: Int, // 2
        @SerializedName("virtualRate")
        val virtualRate: Int // 0
    ) : Serializable {
        data class CertificateImage(
            @SerializedName("createdAt")
            val createdAt: String, // 2022-03-23T06:08:27.000Z
            @SerializedName("id")
            val id: Int, // 228
            @SerializedName("images")
            val images: String, // 1648015707download (3).jpeg
            @SerializedName("updatedAt")
            val updatedAt: String, // 2022-03-23T06:08:27.000Z
            @SerializedName("user_id")
            val userId: Int // 379
        ) : Serializable

        data class FreeSlots(
            @SerializedName("createdAt")
            val createdAt: Int, // 1610092054
            @SerializedName("endTime")
            val endTime: String, // 11:00 PM
            @SerializedName("id")
            val id: Int, // 23
            @SerializedName("startTime")
            val startTime: String, // 10:00 PM
            @SerializedName("status")
            val status: Int, // 1
            @SerializedName("updatedAt")
            val updatedAt: Int // 0
        ) : Serializable

        data class Subject(
            @SerializedName("id")
            val id: Int, // 8
            @SerializedName("name")
            val name: String, // Science
            @SerializedName("status")
            val status: Int // 1
        ) : Serializable

        data class TeachingLevel(
            @SerializedName("id")
            val id: Int, // 2
            @SerializedName("level")
            val level: String, // Middle School Level
            @SerializedName("status")
            val status: Int // 1
        ) : Serializable

        data class TimeSlot(
            @SerializedName("endTime")
            val endTime: String, // 7:00 AM
            @SerializedName("id")
            val id: Int, // 7
            @SerializedName("startTime")
            val startTime: String, // 6:00 AM
            @SerializedName("status")
            val status: Int // 1
        ) : Serializable
    }
}