package com.elewamathtutoring.Activity.ParentOrStudent.wallet.response

import java.io.Serializable

data class WalletGetResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // Get Wallet successfully.
    val body: Body
): Serializable