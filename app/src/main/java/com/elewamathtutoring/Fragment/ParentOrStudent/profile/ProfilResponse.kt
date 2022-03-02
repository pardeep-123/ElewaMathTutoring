package com.elewamathtutoring.Fragment.ParentOrStudent.profile

import java.io.Serializable

data class ProfilResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Profile get successfully.
    val body: Body
): Serializable {
    data class Body(
        val id: Int, // 323
        val name: String, // user00
        val userType: Int, // 1
        val image: String, // http://202.164.42.227:7552/uploads/users/164612015220220105_150447086_fef2148118f24efae02f5eb75ac3e84e.jpg
        val email: String, // user0@yopmail.com
        val about: String, // dfuuytttttttttttttttttjffffffffffffffffff
        val teachingHistory: String,
        val isCertifiedOrtutor: Int, // 0
        val isBuyPlan: Int, // 0
        val teachingLevel: String,
        val educationLevel: String,
        val specialties: String,
        val InPersonRate: Int, // 0
        val virtualRate: Int, // 0
        val cancellationPolicy: String,
        val availability: String,
        val available_slots: String,
        val notificationStatus: Int, // 1
        val latitude: String,
        val longitude: String,
        val deviceType: Int, // 0
        val deviceToken: String,
        val SocialType: Int, // 0
        val SocialId: String,
        val status: Int, // 1
        val pastTeacher: List<PastTeacher>
    ):Serializable {
        data class PastTeacher(
            val id: Int, // 14
            val userId: Int, // 323
            val teacherId: Int, // 321
            val paidStatus: Int, // 0
            val booking_type: Int, // 0
            val payment_type: Int, // 0
            val about: String, // This is info
            val personVirtual: Int, // 1
            val availability: String, // 3
            val date: String, // 1646048206
            val time: String, // 5
            val hours: Int, // 1
            val perHour: Int, // 20
            val total: Int, // 20
            val adminCommision: String, // 2
            val cardId: Int, // 21
            val status: Int, // 2
            val createdAt: Int, // 1615802021
            val updated: Int, // 0
            val updatedAt: Int, // 0
            val teacher: Teacher
        ):Serializable {
            data class Teacher(
                val id: Int, // 321
                val userType: Int, // 2
                val authorization: String,
                val image: String, // http://202.164.42.227:7552/uploads/users/164611178620220105_145451668_fe17da7b131455d4b17eef4d5a3a15a0.jpg
                val name: String, // Ymnc
                val audioTime: String,
                val videoTime: String,
                val email: String, // nihal23@gmail.com
                val address: String, // dds
                val latitude: String, // 30.7046
                val longitude: String, // 76.7179
                val password: String, // $2y$10$2fB24eyo5ieN4JH9/SkxqO1i2SOouqP40.27z8PMKeffwKRM4E5Vm
                val createdAt: Int, // 1645535629
                val updatedAt: Int, // 1646224769
                val status: Int, // 1
                val isapproval: Int, // 2
                val isTechingInfo: Int, // 1
                val IsAvailable: Int, // 1
                val hourlyPrice: Int, // 3
                val notificationStatus: Int, // 1
                val background_checks: Int, // 0
                val deviceType: Int, // 0
                val deviceToken: String,
                val SocialType: Int, // 0
                val SocialId: String,
                val about: String, // qqqqqqqnjvjnlkvkgfkkgnksd\fdvdsarewbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbrewf
                val teachingHistory: String, // qqqqqqq
                val isBuyPlan: Int, // 0
                val planId: Int, // 0
                val planDuration: String,
                val planExpiryDate: String,
                val location: String,
                val isCertifiedOrtutor: Int, // 0
                val teachingLevel: String, // 2,5
                val specialties: String, // bjfbj,fbb
                val InPersonRate: Int, // 0
                val virtualRate: Int, // 0
                val cancellationPolicy: String, // fdfdfd
                val availability: String, // 2,4,7,1,1
                val available_slots: String, // 1,4,6,9,9
                val free_slots: String,
                val forgotPasswordHash: String,
                val isbankAdd: Int, // 0
                val walletAmount: String, // 0
                val stripe_customID: String,
                val stripe_url: String,
                val isStripe_connected: Int, // 0
                val stripe_account_id: String,
                val educationLevel: String, // ree
                val majors: String // er
            ):Serializable
        }
    }
}