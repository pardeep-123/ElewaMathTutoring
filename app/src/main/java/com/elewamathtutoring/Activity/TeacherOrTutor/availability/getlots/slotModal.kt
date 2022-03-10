package com.elewamathtutoring.Activity.TeacherOrTutor.availability.getlots

data class slotModal(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Get Time slots successfully.
    val body: List<Body>
)