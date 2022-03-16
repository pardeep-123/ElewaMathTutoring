package com.elewamathtutoring.Models.BankAccountsModel


import java.io.Serializable


data class Model_BankAccount(

    val body: List<Body> = listOf(),
    val code: Int = 0,
    val message: String = "",
    val status: Boolean = false
):Serializable