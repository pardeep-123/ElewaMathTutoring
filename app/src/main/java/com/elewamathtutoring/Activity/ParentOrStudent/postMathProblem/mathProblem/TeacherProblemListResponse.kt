package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem


import com.google.gson.annotations.SerializedName

data class TeacherProblemListResponse(
    @SerializedName("status")
    val status: Boolean, // true
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // get listing successfully.
    @SerializedName("body")
    val body: List<Body>
) {
    data class Body(
        @SerializedName("id")
        val id: Int, // 40
        @SerializedName("userId")
        val userId: Int, // 396
        @SerializedName("document")
        val document: String, // BEFBG39GHBGFKDJ21HCJG2JGEFKD.jpg
        @SerializedName("description")
        val description: String, // New1j,zjggzgxjxg
        @SerializedName("type")
        val type: Int, // 2
        @SerializedName("createdAt")
        val createdAt: Int, // 1649236810
        @SerializedName("updatedAt")
        val updatedAt: Int, // 1649236874
        @SerializedName("user.id")
        val userid: Int, // 396
        @SerializedName("user.name")
        val userName: String, // Kiki
        @SerializedName("user.image")
        val userImage: String, // 164922229520210922_153633565_2d89da593cd46bde18b1dd2f919b5516.jpg
        @SerializedName("user.CommentCount")
        val userCommentCount: Int // 1
    )
}