package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.details


import com.google.gson.annotations.SerializedName

data class AddCommentResponse(
    @SerializedName("status")
    val status: Boolean, // true
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // post comment successfully.
    @SerializedName("body")
    val body: Body
) {
    data class Body(
        @SerializedName("updatedAt")
        val updatedAt: Int, // 1648720010
        @SerializedName("id")
        val id: Int, // 3
        @SerializedName("comment")
        val comment: String, // good
        @SerializedName("problemId")
        val problemId: String, // 20
        @SerializedName("userId")
        val userId: Int, // 398
        @SerializedName("createdAt")
        val createdAt: Int // 1648720010
    )
}