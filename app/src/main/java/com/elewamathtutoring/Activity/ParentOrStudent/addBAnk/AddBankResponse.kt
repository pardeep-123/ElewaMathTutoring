package com.elewamathtutoring.Activity.ParentOrStudent.addBAnk

data class AddBankResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Added Bank Detail successfully.
    val body: Body
) {
    data class Body(
        val bankType: Int, // 0
        val isDefault: String, // 0
        val id: Int, // 43
        val userId: Int, // 314
        val bankbranch: String, // dsffd
        val accountNo: String, // 213412433
        val accountHolder: String, // dssd
        val ifscCode: String, // 1223bsds
        val createdAt: String, // 1970-01-20T01:05:06.697Z
        val updatedAt: String // 2022-02-22T05:11:37.406Z
    )
}