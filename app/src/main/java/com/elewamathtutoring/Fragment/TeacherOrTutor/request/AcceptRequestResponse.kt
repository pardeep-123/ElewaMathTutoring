package com.elewamathtutoring.Fragment.TeacherOrTutor.request

data class AcceptRequestResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Complete session booking.
    val body: Body
) {
    class Body
}