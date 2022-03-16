package com.elewamathtutoring.Activity.ParentOrStudent.wallet.response

import java.io.Serializable

data class WalletHistory(
    val id: Int, // 2
    val userId: Int, // 321
    val amount: String, // 0.1
    val bankId: Int, // 2
    val message: String, // Withdrawal Request from teacher
    val status: Int, // 0
    val type: Int, // 2
    val createdAt: Int, // 1617012622
    val updatedAt: String, // 2022-02-23T06:57:59.000Z
    val userAccount: UserAccount
): Serializable