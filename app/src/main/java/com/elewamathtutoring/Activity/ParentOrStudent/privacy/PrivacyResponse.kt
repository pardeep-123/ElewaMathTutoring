package com.elewamathtutoring.Activity.ParentOrStudent.privacy

data class PrivacyResponse(
    val status: Boolean, // true
    val code: Int, // 200
    val message: String, // About Us fetched successfully.
    val body: Body
) {
    data class Body(
        val id: Int, // 1
        val accessor: String, // About
        val content: String, // <h1>About Us</h1><p>Lorem ipsum dolor sit amet, <a href="https://www.google.com">consectetur</a> adipiscing elit&nbsp;Lorem ipsum dolor sit amet, consectetur adipiscing elit</p><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit&nbsp;Lorem ipsum dolor sit amet, consectetur adipiscing elit</p>
        val createdAt: String, // 2020-03-13T16:22:12.000Z
        val updatedAt: String // 2021-03-16T07:24:45.000Z
    )
}