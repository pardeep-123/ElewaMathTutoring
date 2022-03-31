package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.details


import com.google.gson.annotations.SerializedName

data class CommentListResponse(
    @SerializedName("status")
    val status: Boolean, // true
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // get comment listing successfully.
    @SerializedName("body")
    val body: Body
) {
    data class Body(
        @SerializedName("problem")
        val problem: Problem,
        @SerializedName("AllComments")
        val allComments: List<AllComment>
    ) {
        data class Problem(
            @SerializedName("id")
            val id: Int, // 7
            @SerializedName("userId")
            val userId: Int, // 383
            @SerializedName("document")
            val document: String, // BKH3F308936DC9KC5D1KJ7FKE0EJ.png
            @SerializedName("description")
            val description: String, // mhk
            @SerializedName("type")
            val type: Int, // 0
            @SerializedName("createdAt")
            val createdAt: Int, // 1648707238
            @SerializedName("updatedAt")
            val updatedAt: Int // 1648707238
        )

        data class AllComment(
            @SerializedName("id")
            val id: Int, // 11
            @SerializedName("userId")
            val userId: Int, // 398
            @SerializedName("problemId")
            val problemId: Int, // 7
            @SerializedName("comment")
            val comment: String, // good
            @SerializedName("createdAt")
            val createdAt: Int, // 1648723199
            @SerializedName("updatedAt")
            val updatedAt: Int, // 1648723199
            @SerializedName("user")
            val user: User
        ) {
            data class User(
                @SerializedName("id")
                val id: Int, // 398
                @SerializedName("name")
                val name: String, // sa
                @SerializedName("image")
                val image: String
            )
        }
    }
}