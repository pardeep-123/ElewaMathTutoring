package com.elewamathtutoring.Activity.ParentOrStudent.resources

data class CategoriesResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // get all categories
    val body: List<Body>
) {
    data class Body(
        val id: Int, // 3
        val name: String, // car
        var check:Boolean = false
    )
}