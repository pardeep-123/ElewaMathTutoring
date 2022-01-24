package com.elewamathtutoring.Models.BankAccountsModel


import java.io.Serializable


data class Body(

    val accountHolder: String = "",
    val accountNo: String = "",
    val bankType: Int = 0,
    val bankbranch: String = "",
    val createdAt: Any? = Any(),
    val id: Int = 0,
    val ifscCode: String = "",
    val isDefault: Int = 0,
    val updatedAt: Any? = Any(),
    val userId: Int = 0
):Serializable