package com.elewamathtutoring.Activity.ParentOrStudent.filter

import java.io.Serializable

data class SubjectsResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // get all subjects
    val body: List<Body>
):Serializable {
    data class Body(
        val id: Int, // 7
        val name: String, // math
        var check: Boolean = false
    ):Serializable
}