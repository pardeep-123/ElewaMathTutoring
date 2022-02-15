package com.elewamathtutoring.Activity.ParentOrStudent.resources

import java.io.Serializable

data class ResourcesResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // resources
    val body: List<Body>
):Serializable {
    data class Body(
        val id: Int, // 1
        val text: String, // fd
        val link: String, // https:/jdjs
        val document: String, // H5B7CKG1K487G956927072F18FEB.
        val authname: String,
        val status: Int, // 0
        val category_id: Int, // 3
        val createdAt: String, // 2021-12-24T12:09:34.000Z
        val updatedAt: String, // 2022-01-05T11:16:00.000Z
        val categoryName: String // car
    ):Serializable
}