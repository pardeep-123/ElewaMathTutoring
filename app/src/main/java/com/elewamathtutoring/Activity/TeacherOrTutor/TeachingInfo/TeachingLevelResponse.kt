package com.elewamathtutoring.Activity.TeacherOrTutor.TeachingInfo

data class TeachingLevelResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Get Teaching level successfully.
    val body: List<Body>
) {
    data class Body(
        val id: Int, // 1
        val level: String, // Elementary School Level
        val status: Int, // 1
        val createdAt: String, // 2021-01-22T05:01:00.000Z
        val updatedAt: String // 2021-03-30T13:13:47.000Z
    )
}