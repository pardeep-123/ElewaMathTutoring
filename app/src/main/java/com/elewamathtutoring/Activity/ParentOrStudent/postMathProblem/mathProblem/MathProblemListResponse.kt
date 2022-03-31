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
        val id: Int, // 8
        @SerializedName("userId")
        val userId: Int, // 383
        @SerializedName("document")
        val document: String, // CJ5G16H9KFJ3EC3H67CGKJ37C2B7.pdf
        @SerializedName("description")
        val description: String, // mona
        @SerializedName("createdAt")
        val createdAt: Int, // 1648707267
        @SerializedName("updatedAt")
        val updatedAt: Int // 1648707267
    ):Serializable

}