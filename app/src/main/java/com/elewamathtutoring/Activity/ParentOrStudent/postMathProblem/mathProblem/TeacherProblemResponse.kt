package com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TeacherProblemResponse(
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
        val id: Int, // 47
        @SerializedName("userId")
        val userId: Int, // 396
        @SerializedName("document")
        val document: String, // 40940J59DH0HBC0K7E93431H1942.jpg
        @SerializedName("description")
        val description: String, // kv singh
        @SerializedName("type")
        val type: Int, // 1
        @SerializedName("createdAt")
        val createdAt: Int, // 1649333362
        @SerializedName("updatedAt")
        val updatedAt: Int, // 1649333362
        @SerializedName("user")
        val user: User,
        @SerializedName("count")
        val count: Int // 0
    ) :Serializable{
        data class User(
            @SerializedName("id")
            val id: Int, // 396
            @SerializedName("name")
            val name: String, // Kiki
            @SerializedName("image")
            val image: String, // 164922229520210922_153633565_2d89da593cd46bde18b1dd2f919b5516.jpg
            @SerializedName("count")
            val count: Int // 0
        ):Serializable
    }
}