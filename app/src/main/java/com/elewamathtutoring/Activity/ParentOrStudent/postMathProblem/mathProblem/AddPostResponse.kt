package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddPostResponse(
    @SerializedName("status")
    val status: Boolean, // true
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // add problem successfully.
    @SerializedName("body")
    val body: Body
):Serializable {
    data class Body(
        @SerializedName("updatedAt")
        val updatedAt: Int, // 1648714395
        @SerializedName("id")
        val id: Int, // 18
        @SerializedName("document")
        val document: String, // CJG7340CJ8J6G368D08F4K9567D7.png
        @SerializedName("description")
        val description: String, // mani hsjkfhjhsgdjhsdghjgdhjhfgjdhjsgdhjk
        @SerializedName("userId")
        val userId: Int, // 398
        @SerializedName("createdAt")
        val createdAt: Int // 1648714395
    ):Serializable
}