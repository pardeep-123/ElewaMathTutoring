package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.details


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CommentListResponse(
    @SerializedName("status")
    val status: Boolean, // true
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // get comment listing successfully.
    @SerializedName("body")
    val body: Body
):Serializable {
    data class Body(
        @SerializedName("problem")
        val problem: Problem,
        @SerializedName("AllComments")
        val allComments: List<AllComment>
    ) :Serializable{
        data class Problem(
            @SerializedName("id")
            val id: Int, // 20
            @SerializedName("userId")
            val userId: Int, // 398
            @SerializedName("document")
            val document: String, // C0K874F30K3E71243BGJH71K4B20.png
            @SerializedName("description")
            val description: String, // mani hsjkfhjhsgdjhsdghjgdhjhfgjdhjsgdhjk
            @SerializedName("type")
            val type: Int, // 0
            @SerializedName("createdAt")
            val createdAt: Int, // 1648719723
            @SerializedName("updatedAt")
            val updatedAt: Int // 1648719723
        ):Serializable

        data class AllComment(
            @SerializedName("id")
            val id: Int, // 18
            @SerializedName("userId")
            val userId: Int, // 398
            @SerializedName("problemId")
            val problemId: Int, // 20
            @SerializedName("comment")
            val comment: String, // WEQEQWQ
            @SerializedName("createdAt")
            val createdAt: Int, // 1648796557
            @SerializedName("updatedAt")
            val updatedAt: Int, // 1648796557
            @SerializedName("user")
            val user: User
        ) :Serializable{
            data class User(
                @SerializedName("id")
                val id: Int, // 398
                @SerializedName("name")
                val name: String, // sa
                @SerializedName("image")
                val image: String
            ):Serializable
        }
    }
}