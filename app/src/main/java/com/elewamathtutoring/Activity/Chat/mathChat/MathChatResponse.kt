package com.elewamathtutoring.Activity.Chat.mathChat

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MathChatResponse(
    @SerializedName("status")
    val status: Boolean, // true
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // get List successfully
    @SerializedName("body")
    val body: List<Body>
):Serializable {
    data class Body(
        @SerializedName("id")
        val id: Int, // 378
        @SerializedName("userType")
        val userType: Int, // 2
        @SerializedName("authorization")
        val authorization: String,
        @SerializedName("image")
        val image: String, // 1648015104IMG-20220220-WA0059.jpg
        @SerializedName("name")
        val name: String, // Kv
        @SerializedName("audioTime")
        val audioTime: String,
        @SerializedName("videoTime")
        val videoTime: String,
        @SerializedName("email")
        val email: String, // kv@yopmail.com
        @SerializedName("address")
        val address: String, // Minquan W Rd, Datong District, Taipei City, Taiwan
        @SerializedName("latitude")
        val latitude: String, // 25.0630589
        @SerializedName("longitude")
        val longitude: String, // 121.5159765
        @SerializedName("password")
        val password: String, // $2y$10$1f1F1z/YqVReQSGVWTGtLeqFWgYSCNYwaJsfbwcu/ye0FbLw0j5x6
        @SerializedName("createdAt")
        val createdAt: Int, // 1648015104
        @SerializedName("updatedAt")
        val updatedAt: Int, // 1648015333
        @SerializedName("status")
        val status: Int, // 1
        @SerializedName("isapproval")
        val isapproval: Int, // 2
        @SerializedName("isTechingInfo")
        val isTechingInfo: Int, // 1
        @SerializedName("IsAvailable")
        val isAvailable: Int, // 1
        @SerializedName("hourlyPrice")
        val hourlyPrice: Int, // 12
        @SerializedName("notificationStatus")
        val notificationStatus: Int, // 1
        @SerializedName("occupiedStatus")
        val occupiedStatus: Int, // 1
        @SerializedName("background_checks")
        val backgroundChecks: Int, // 0
        @SerializedName("deviceType")
        val deviceType: Int, // 0
        @SerializedName("deviceToken")
        val deviceToken: String,
        @SerializedName("SocialType")
        val socialType: Int, // 0
        @SerializedName("SocialId")
        val socialId: String,
        @SerializedName("about")
        val about: String, // hindi sahitya ke prati apani jimmedari ka nirvah karte hue use jald h hi ni hai h na mei ni hai k liye h to bus
        @SerializedName("teachingHistory")
        val teachingHistory: String, // the winter soldier of fortune to bus stop is the best for the winter soldier of h h e h a h e h e h e
        @SerializedName("isBuyPlan")
        val isBuyPlan: Int, // 0
        @SerializedName("planId")
        val planId: Int, // 0
        @SerializedName("planDuration")
        val planDuration: String,
        @SerializedName("planExpiryDate")
        val planExpiryDate: String,
        @SerializedName("location")
        val location: String,
        @SerializedName("isCertifiedOrtutor")
        val isCertifiedOrtutor: Int, // 0
        @SerializedName("teachingLevel")
        val teachingLevel: String, // [1]
        @SerializedName("specialties")
        val specialties: String, // ,7,8
        @SerializedName("InPersonRate")
        val inPersonRate: Int, // 0
        @SerializedName("virtualRate")
        val virtualRate: Int, // 0
        @SerializedName("cancellationPolicy")
        val cancellationPolicy: String, // canel the policy coz I don't like
        @SerializedName("availability")
        val availability: String, // 1,5,2,6,3
        @SerializedName("available_slots")
        val availableSlots: String, // 1,2,4,6,8,7,9,11,13,15,18,20,21,24,23,19
        @SerializedName("free_slots")
        val freeSlots: String, // 3
        @SerializedName("forgotPasswordHash")
        val forgotPasswordHash: String,
        @SerializedName("isbankAdd")
        val isbankAdd: Int, // 0
        @SerializedName("walletAmount")
        val walletAmount: String,
        @SerializedName("stripe_customID")
        val stripeCustomID: String,
        @SerializedName("stripe_url")
        val stripeUrl: String,
        @SerializedName("isStripe_connected")
        val isStripeConnected: Int, // 0
        @SerializedName("stripe_account_id")
        val stripeAccountId: String,
        @SerializedName("educationLevel")
        val educationLevel: String, // Master's degree
        @SerializedName("majors")
        val majors: String, // there are two different types
        @SerializedName("subjects")
        val subjects: List<Subject>
    ):Serializable {
        data class Subject(
            @SerializedName("id")
            val id: Int, // 7
            @SerializedName("name")
            val name: String, // math
            @SerializedName("status")
            val status: Int, // 1
            @SerializedName("createdAt")
            val createdAt: Int, // 2147483647
            @SerializedName("updatedAt")
            val updatedAt: Int // 2147483647
        ):Serializable
    }
}