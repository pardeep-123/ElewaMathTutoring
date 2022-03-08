package com.elewamathtutoring.Activity.ParentOrStudent.session

data class ScheduleSessionResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Session booked successfully.
    val body: Body
) {
    data class Body(
        val paidStatus: Int, // 0
        val booking_type: Int, // 0
        val payment_type: Int, // 0
        val personVirtual: Int, // 0
        val availability: String,
        val cardId: Int, // 0
        val id: Int, // 194
        val userId: Int, // 323
        val teacherId: String, // 239
        val about: String, // hhhhhhhhhhh
        val time: String, // 1,4
        val hours: String, // 2
        val perHour: Int, // 0
        val createdAt: Int, // 1646717647
        val updated: Int, // 1646717647
        val adminCommision: Int, // 0
        val total: Int, // 0
        val date: String, // 12
        val status: Int, // 0
        val updatedAt: Int // 1646717647
    )
}