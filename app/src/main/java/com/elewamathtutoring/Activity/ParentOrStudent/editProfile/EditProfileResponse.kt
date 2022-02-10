package com.elewamathtutoring.Activity.ParentOrStudent.editProfile

data class EditProfileResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Edit Profile successfully.
    val body: Body
) {
    data class Body(
        val id: Int, // 241
        val name: String, // aawew
        val userType: Int, // 1
        val image: String, // 1644491519arrow.png
        val address: String,
        val email: String, // a@a.a
        val about: String, // dsfgsghb
        val notificationStatus: Int, // 0
        val deviceType: Int, // 0
        val deviceToken: String,
        val SocialType: Int, // 0
        val SocialId: String,
        val status: Int // 1
    )
}