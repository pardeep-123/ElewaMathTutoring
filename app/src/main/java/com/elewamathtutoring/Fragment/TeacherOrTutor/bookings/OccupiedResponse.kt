package com.elewamathtutoring.Fragment.TeacherOrTutor.bookings

data class OccupiedResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Status Updated Successfully.
    val body: Body
) {
    data class Body(
        val id: Int, // 349
        val occupiedStatus: Int // 1
    )
}