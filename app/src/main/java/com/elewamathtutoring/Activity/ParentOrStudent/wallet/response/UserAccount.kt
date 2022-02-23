package com.elewamathtutoring.Activity.ParentOrStudent.wallet.response

import java.io.Serializable

data class UserAccount(
    val id: Int, // 2
    val userId: Int, // 60
    val bankbranch: String, // Nehrian
    val accountNo: String, // 834735276398376
    val accountHolder: String, // Monika
    val ifscCode: String, // gwu6237
    val bankType: Int, // 1
    val isDefault: Int, // 1
    val createdAt: Any, // null
    val updatedAt: String // 2021-03-27T04:58:25.000Z
): Serializable