package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MathProblemListResponse(
    @SerializedName("status")
    val status: Boolean, // true
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // get listing successfully.
    @SerializedName("body")
    val body: List<Body>
):Serializable {
    data class Body(
        @SerializedName("id")
        val id: Int, // 31
        @SerializedName("userId")
        val userId: Int, // 396
        @SerializedName("document")
        val document: String, // 21B5GBD8GJ5DEGF84E87C6E36HK8.jpg
        @SerializedName("description")
        val description: String, // you
        @SerializedName("type")
        val type: Int, // 0
        @SerializedName("createdAt")
        val createdAt: String, // 1649141649
        @SerializedName("count")
        val count: Int // 0
    ):Serializable

}