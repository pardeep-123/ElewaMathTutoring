package com.elewamathtutoring.Activity.ParentOrStudent.wallet.response

import java.io.Serializable

data class Body(
    val TotalAmount: TotalAmount,
    val wallet_history: List<WalletHistory>
):Serializable