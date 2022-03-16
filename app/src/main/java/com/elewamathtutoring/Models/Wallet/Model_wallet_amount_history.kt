package com.elewamathtutoring.Models.Wallet

import java.io.Serializable

data class Model_wallet_amount_history(
    val body: Body,
    val code: Int,
    val message: String,
    val status: Boolean
):Serializable