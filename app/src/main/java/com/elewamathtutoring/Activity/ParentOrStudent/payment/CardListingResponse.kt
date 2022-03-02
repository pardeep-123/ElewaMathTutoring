package com.elewamathtutoring.Activity.ParentOrStudent.payment

data class CardListingResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // User cards listed successfully.
    val body: List<Body>
) {
    data class Body(
        val id: Int, // 82
        val user_id: Int, // 267
        val holder_name: String, // sfdas
        val card_number: String, // 3453453442
        val expiry_year: String, // 2222
        val expiry_month: String, // 2
        val cvv: Int, // 111
        val isSave: Int, // 1
        val createdAt: String, // 2022-03-02T05:49:46.000Z
        val updatedAt: String // 2022-03-02T05:49:46.000Z
    )
}