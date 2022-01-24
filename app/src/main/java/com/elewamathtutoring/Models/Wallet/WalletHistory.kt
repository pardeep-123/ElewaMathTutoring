package com.elewamathtutoring.Models.Wallet

import java.io.Serializable

data class WalletHistory(
    val amount: String,
    val bankId: Int,
    val createdAt: Int,
    val id: Int,
    val message: String,
    val status: Int,
    val type: Int,
    val updatedAt: String,
    val userAccount: Any,
    val userId: Int
): Serializable