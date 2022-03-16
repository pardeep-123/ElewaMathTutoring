package com.elewamathtutoring.Model

data class EducationLevel (/*var educationType:Int,*/var educationName:String) {
    override fun toString(): String {
        return educationName
    }
}