package com.elewamathtutoring.Activity.ParentOrStudent.wallet

import java.io.Serializable

data class BankListingResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Get Bank listing successfully.
    val body: List<Body>
):Serializable {
    data class Body(
        val id: Int, // 42
        val userId: Int, // 314
        val bankbranch: String, // dsffd
        val accountNo: String, // 213412433
        val accountHolder: String, // dssd
        val ifscCode: String, // 1223bsds
        val bankType: Int, // 0
        val isDefault: Int, // 0
        val createdAt: String, // 1970-01-20T01:04:10.000Z
        val updatedAt: String // 2022-02-21T13:30:11.000Z
    ):Serializable
}